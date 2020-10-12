package com.study.question.dao;

import java.util.HashMap;
import java.util.Map;

public class SearchParam {

	private String searchQuery;

	private Map<String, Object> searchMap = new HashMap(0);

	public String getSearchQuery() {
		return searchQuery;
	}

	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}

	public Map<String, Object> getSearchMap() {
		return searchMap;
	}

	public void setSearchMap(Map<String, Object> searchMap) {
		this.searchMap = searchMap;
	}

}
