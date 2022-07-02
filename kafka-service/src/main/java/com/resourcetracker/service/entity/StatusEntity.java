package com.resourcetracker.service.entity;

public class StatusEntity {
	public enum StatusType {
		FAILURE, SUCCESS;
	}

	StatusType statusType;

	public StatusType getStatusType() {
		return this.statusType;
	}

	public void setStatusType(StatusType statusType) {
		this.statusType = statusType;
	}
}
