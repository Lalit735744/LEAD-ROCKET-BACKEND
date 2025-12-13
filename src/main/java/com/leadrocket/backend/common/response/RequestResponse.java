package com.leadrocket.backend.common.response;

public class RequestResponse {

	private boolean status;
	private String message;
	private Object data;
	private int code;

	public static RequestResponse success(Object data) {
		RequestResponse r = new RequestResponse();
		r.status = true;
		r.data = data;
		r.code = 200;
		return r;
	}

	public static RequestResponse error(String message, int code) {
		RequestResponse r = new RequestResponse();
		r.status = false;
		r.message = message;
		r.code = code;
		return r;
	}

	public boolean isStatus() { return status; }
	public String getMessage() { return message; }
	public Object getData() { return data; }
	public int getCode() { return code; }
}
