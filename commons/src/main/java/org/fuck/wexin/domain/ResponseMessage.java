package org.fuck.wexin.domain;

public abstract class ResponseMessage {
	
	private int status;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
