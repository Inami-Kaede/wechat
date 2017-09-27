package com.hayate.wechat.common.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

public class TestRequest {
	
	public TestRequest() {
	}

	@ApiModelProperty(example="yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date birthday;
	
	@JsonIgnore
	private Long age;
	
	@ApiModelProperty(hidden=true)
	private boolean sex = true;
	
	@ApiModelProperty(hidden=true)
	@JsonIgnore
	private CommonResponse res;
	
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Long getAge() {
		return age;
	}
	public void setAge(Long age) {
		this.age = age;
	}
	public CommonResponse getRes() {
		return res;
	}
	public void setRes(CommonResponse res) {
		this.res = res;
	}

	public boolean isSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	@Override
	public String toString() {
		return "TestRequest [birthday=" + birthday + ", age=" + age + ", sex=" + sex + ", res=" + res + "]";
	}
}
