package org.fuck.wexin.domain;

public abstract class ResponseMessage {
	
//响应的状态值，1表示成功，其他表示失败
	private int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
