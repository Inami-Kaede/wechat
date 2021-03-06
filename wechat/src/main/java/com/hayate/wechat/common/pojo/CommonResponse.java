package com.hayate.wechat.common.pojo;

public class CommonResponse {
	
	private boolean isSuccess;

	private String errCode;
	
	private String errMsg;
	
	private Object data;
	
	public CommonResponse(boolean isSuccess, String errCode, String errMsg,
			Object data) {
		super();
		this.isSuccess = isSuccess;
		this.errCode = errCode;
		this.errMsg = errMsg;
		this.data = data;
	}
	
	public CommonResponse(boolean isSuccess) {
		this.isSuccess = isSuccess;
		this.errCode = null;
		this.errMsg = null;
		this.data = null;
	}

	public CommonResponse(Object data) {
		this.isSuccess = true;
		this.errCode = null;
		this.errMsg = null;
		this.data = data;
	}
	
	public CommonResponse(String errCode,String errMsg) {
		this.isSuccess = false;
		this.errCode = errCode;
		this.errMsg = errMsg;
		this.data = null;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
