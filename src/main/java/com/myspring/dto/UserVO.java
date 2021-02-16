package com.myspring.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class UserVO {

//	id pw name email joindate
	private String id;
	private String pwd;
	private String name;
	private String email;
	
	private String fileName;
	private MultipartFile uploadFile;
	
	private Date joindate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public MultipartFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}

	public Date getJoindate() {
		return joindate;
	}

	public void setJoindate(Date joindate) {
		this.joindate = joindate;
	}

	@Override
	public String toString() {
		return "UserVO [id=" + id + ", pwd=" + pwd + ", name=" + name + ", email=" + email + ", fileName=" + fileName
				+ ", uploadFile=" + uploadFile + ", joindate=" + joindate + "]";
	}

	
}
