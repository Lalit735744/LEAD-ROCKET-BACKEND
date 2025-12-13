package com.leadrocket.backend.common.response;

import java.util.List;

public class PaginationResponse<T> {

	private List<T> records;
	private long total;

	public PaginationResponse(List<T> records, long total) {
		this.records = records;
		this.total = total;
	}

	public List<T> getRecords() { return records; }
	public long getTotal() { return total; }
}
