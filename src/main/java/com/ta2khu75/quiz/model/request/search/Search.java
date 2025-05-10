package com.ta2khu75.quiz.model.request.search;

public class Search {
	private String keyword;
	private int page=0;
	private int size=10;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if (page > 0) {
			this.page = page-1;
		} 
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		if(size>0 && size<51) {
		this.size = size;
		}
	}
}
