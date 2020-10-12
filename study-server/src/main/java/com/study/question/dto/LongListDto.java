package com.study.question.dto;

import java.util.List;

public class LongListDto {

	List<Long> list;

	boolean approvalStatus = false;

	public List<Long> getList() {
		return list;
	}

	public void setList(List<Long> list) {
		this.list = list;
	}

	public boolean isApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(boolean approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

}
