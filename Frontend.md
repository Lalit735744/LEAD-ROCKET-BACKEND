Frontend integration guide — LeadRocket Backend

Overview

This document describes all backend features implemented in the repository and every API path, request/response shape, authentication expectations, SSE for in-app push, and important UI/workflow notes so a frontend team can implement the app A→Z.

Important concepts / conventions
- Multi-tenant approach (tenant-per-company collections): every company has its own collections for domain data. Collection helper names use this pattern (example): company_{companyId}_users, company_{companyId}_leads, company_{companyId}_notifications, company_{companyId}_roles.
- Authentication: JWT access tokens are used. Tokens embed the user id as the JWT subject and the tenant/company id in the claim `cid`. Example claim: { sub: "<userId>", cid: "<companyId>", exp: ... }.
- Request attributes: the backend servlet JWT filter sets request attributes `authenticatedUserId` and `authenticatedCompanyId` for server handlers; the frontend must send Authorization header: `Authorization: Bearer <accessToken>` for secured endpoints.
- Roles & RBAC: roles are stored per-company and contain permission keys (strings). Roles can include the special permission `ALL` which grants all actions. The CEO role is created by the signup flow and has `ALL` by default.
- In-app push: backend supports Server-Sent Events (SSE) for real-time notifications. Frontend should open an SSE connection to receive notifications for a user.
- Environment: use `application-dev.yml` for development. Backend expects a running MongoDB (default mongodb://localhost:27017/leadrocket_dev). Frontend developers only need to call backend APIs.

Authentication & Signup

1) Signup (create company + CEO admin): public endpoint
- Path: POST /api/signup
- Request payload (JSON):
  {
    "companyName": "Acme Builders",
    "domain": "acme.local",
    "adminName": "Alice CEO",
    "adminEmail": "alice@acme.local",
    "adminPassword": "securePassword"
  }
- Response (JSON):
  {
    "companyId": "<companyId>",
    "adminUserId": "<userId>",
    "accessToken": "<jwtAccessToken>"
  }
- Notes: the returned accessToken contains `cid` claim with the created `companyId`. After signup the CEO token has rights to create roles, users, etc.

2) Login (if your frontend needs it)
- If a conventional login endpoint exists in your project /api/auth/login or similar, supply email+password and receive an access token. If not present, add a simple login flow: POST /api/auth/login { email, password } -> returns token containing company context. (If you need me to implement login endpoint I can add it.)

Headers you must send for protected endpoints
- Authorization: Bearer <token>
- Content-Type: application/json (for JSON body requests)
- For SSE connections only, use no Authorization header in the EventSource constructor: instead pass token as a query parameter or custom header depending on the client. (See SSE section below.)

User management

Create user (tenant-scoped)
- Path: POST /api/users
- Request payload (JSON):
  {
    "name": "Bob Manager",
    "email": "bob@acme.local",
    "mobile": "9998887777",
    "password": "password123",
    "companyId": "<companyId>",         // required for tenant user creation
    "roleIds": ["<roleId>"]             // optional role ids
  }
- Important: supply the companyId (the CEO should be issuing these calls and token must have cid matching companyId).
- Response: created user DTO (id, name, email, mobile, active).
- Error: 401 if token missing/invalid; 403 if token company mismatch; 409/400 if email conflict.

List tenant users
- Path: GET /api/{companyId}/users  (note: implementation may use tenant repository direct APIs; adapt UI to backend paths present in codebase)
- Query params: optional pagination (not currently implemented by default — page/limit could be added)

Roles & RBAC

Create role (CEO-only)
- Path: POST /api/{companyId}/roles
- Body: JSON
  {
    "name": "Manager",
    "permissions": ["LEADS_MANAGE", "REPORTS_VIEW"]
  }
- Response: created role (id,name,permissions)
- Authorization: token must be from company's CEO (backend checks userHasRole(companyId, userId, "CEO")) and token company must match path companyId.

List roles
- Path: GET /api/{companyId}/roles
- Response: [ {id,name,permissions}, ... ]

Assign/Remove role to/from user (CEO-only)
- Assign: POST /api/{companyId}/users/{userId}/roles
  - Body: JSON array of roleIds to add
  - Response: updated user document
- Remove: DELETE /api/{companyId}/users/{userId}/roles/{roleId}

Notes on RBAC in UI
- In the company settings UI, show a roles management page where CEO can create roles and assign permissions (permission keys are arbitrary strings, choose a convention like MODULE_ACTION).
- When creating manager/employee accounts, the CEO can assign role ids during creation via `roleIds` property.
- Frontend should disable role-management UI for non-CEO users; final enforcement is server-side.

Leads

Tenant lead creation (and assignment)
- Path: POST /api/{companyId}/leads
- Body:
  {
    "name": "John Prospect",
    "phone": "9991234567",
    "email": "john@example.com",
    "source": "Website",
    "status": "NEW",
    "assignedTo": "<userId>"
  }
- Response: the created lead object with id, timestamps
- Side effects: if `assignedTo` is provided, the backend creates a Notification for that user and pushes an SSE event to any active SSE subscriber.

Lead operations (existing code provides classic endpoints under /leads controller):
- GET /leads -> list leads (global implementation pre-existing). For tenant-specific listing prefer tenant endpoints or use companyId path prefer /api/{companyId}/leads (tenant API implemented as TenantLeadApi).
- GET /leads/{id}, PUT /leads/{id}, /leads/{id}/assign/{userId}, etc — these endpoints are part of the existing lead controller. Frontend can reuse those but prefer tenant endpoints when available.

Notifications (In-app push)

SSE subscription (recommended approach for frontend real-time push):
- Open an SSE connection and subscribe to notifications for a user. Example URL:
  /api/{companyId}/notifications/subscribe?userId={userId}
- Authorization: The backend expects JWT in Authorization header; some browsers restrict custom headers with EventSource. Two options:
  1. Create an authenticated endpoint that accepts token as a query param: /subscribe?userId=...&token=... (not recommended for production since query tokens appear in logs), OR
  2. Use a proxied SSE endpoint that adds the header server-side (best), OR use the library that supports fetch-based SSE with headers.
- On event: SSE sends events named `notification` with the Notification object as data.

Notification object shape (example):
  {
    "id": "notif-1",
    "fromUserId": "u123",
    "toUserId": "u456",
    "type": "LEAD_ASSIGNED",
    "title": "New lead assigned",
    "body": "You have been assigned a new lead: John Doe",
    "read": false,
    "createdAt": "2025-12-21T..."
  }

REST notification endpoints
- POST /api/{companyId}/notifications?fromUserId=...&toUserId=...&type=...&title=...&body=...  -> creates notification and triggers SSE push
- GET /api/{companyId}/notifications?userId=...&limit=50  -> list last notifications for user
- PUT /api/{companyId}/notifications/{id}/read  -> mark as read

UI behavior
- When a lead is assigned, show a toast and push it to the in-app notifications pane. Notification store should be persisted in DB by backend.
- Provide an unread badge count via a small endpoint; or compute from GET /notifications results easily on frontend.

Reports

1) On-demand aggregated reports (sync)
- Path: GET /api/{companyId}/reports/employee-leads?start=YYYY-MM-DD&end=YYYY-MM-DD
- Response: list of aggregation docs: each document contains the group key (assignedTo) as `_id`, `leadCount`, and `convertedCount`.

2) CSV download (sync)
- Path: GET /api/{companyId}/reports/employee-leads/csv?start=YYYY-MM-DD&end=YYYY-MM-DD
- Response: text/csv attachment

3) Async export skeleton (background)
- Schedule export: POST /api/{companyId}/employee-leads/export?start=YYYY-MM-DD&end=YYYY-MM-DD
  - Response includes a `ReportRecord` id and status PENDING
- Download: GET /api/{companyId}/export/{reportId}
  - If status == DONE -> returns CSV content as attachment
  - If status == PENDING -> returns 409 with status message
  - If status == FAILED -> returns 409 and error details

ReportRecord shape (saved in `reports` collection):
  {
    "id": "report-1",
    "companyId": "company-1",
    "type": "employee-leads",
    "status": "PENDING|DONE|FAILED",
    "createdAt": "...",
    "completedAt": "...",
    "fileName": "employee_leads_20251221...csv",
    "content": "...csv text...",
    "errorMessage": null
  }

Error handling / common error codes
- 401 Unauthorized: missing/invalid token or token cannot be parsed
- 403 Forbidden: token companyId mismatch, or role check failure (only CEO allowed for role-management endpoints)
- 404 Not Found: requested resource not found
- 409 Conflict: resource conflict (email already exists) or report not ready
- 400 Bad Request: validation errors (missing required fields or invalid dates)

Frontend UX suggestions
- Use the signup flow to create a company and CEO; store returned token and company id in local storage/session securely.
- On login/store token, decode token to get companyId (claim `cid`) if needed client-side for constructing tenant paths.
- Always include Authorization header for protected endpoints.
- SSE: open SSE on app load when a user signs in. Reconnect strategy: exponential backoff, re-authenticate if server rejects.
- Provide roles & permissions UI only for CEO: role create/edit page, assign roleIds during user creation.
- For reports, provide date pickers and option to run sync preview (GET /employee-leads) and also scheduled CSV export (POST .../export) that the user can download later.

Testing & local setup notes for frontend devs
- Backend dev profile: ensure `application-dev.yml` is used. By default the application typically loads `dev` profile during tests; verify in your environment.
- Mongo: backend expects a local mongo on port 27017 for dev; start one before running backend.
- For quick integrated testing of SSE locally: create signup -> get token -> open SSE connection (pass token either via query param during local dev) -> create a lead assigned to the user -> verify SSE event arrives.

API Examples (curl)
- Signup
  curl -X POST http://localhost:8080/api/signup \
    -H "Content-Type: application/json" \
    -d '{"companyName":"Acme","domain":"acme.local","adminName":"Alice","adminEmail":"alice@acme.local","adminPassword":"pass"}'

- Subscribe SSE (quick local dev example where token placed in query param — not recommended for production):
  eventSource = new EventSource("http://localhost:8080/api/<companyId>/notifications/subscribe?userId=<userId>&token=<token>");
  eventSource.onmessage = e => console.log(JSON.parse(e.data));

- Create Lead (tenant)
  curl -X POST http://localhost:8080/api/<companyId>/leads \
    -H "Authorization: Bearer <token>" \
    -H "Content-Type: application/json" \
    -d '{"name":"Jane Prospect","phone":"9990001111","assignedTo":"<employeeId>"}'

- Schedule Report Export
  curl -X POST "http://localhost:8080/api/<companyId>/employee-leads/export?start=2025-01-01&end=2025-12-31" \
    -H "Authorization: Bearer <token>"

- Download Report
  curl -X GET "http://localhost:8080/api/<companyId>/export/<reportId>" -H "Authorization: Bearer <token>"

Final notes
- If you want me to add a web-openapi (Swagger) description I can auto-generate an OpenAPI document describing the endpoints defined above.
- If you want login endpoint added, or changes to send token via header for SSE, I can implement that quickly.

If you'd like, next I will:
- Add a short README.md file with the same content in the repo root for the frontend team; and
- Generate a minimal OpenAPI spec (YAML) for these APIs so the frontend can auto-generate client stubs.

Tell me which of the two (README or OpenAPI) you want next, or I will create both.
