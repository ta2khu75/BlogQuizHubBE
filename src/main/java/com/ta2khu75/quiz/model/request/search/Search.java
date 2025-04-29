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
			this.page = page - 1;
		}
	}

	public int getSize() {
		if (size < 1 || size > 50) {
			return defalutSize;
		}
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
