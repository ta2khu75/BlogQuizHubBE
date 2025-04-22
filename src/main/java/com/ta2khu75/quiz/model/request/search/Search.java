package com.ta2khu75.quiz.model.request.search;

public class Search {
	private String keyword;
	private int page;
	private int size;
	private final int defalutPage = 0;
	private final int defalutSize = 10;

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
		if (page < 1) {
			this.page = defalutPage;
		} else {
			this.page = page-1;
		}
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		if (size > 100) {
			this.size = defalutSize;
		} else {
			this.size = size;
		}
	}
}
