package com.myspring.dto;

import java.util.Arrays;
import java.util.Date;

public class BoardDto {
	
	private int bno;
	private String title;
	private String content;
	private String writer;	 	// 게시글 작성자
	private String userName;	// 게시글 작성자의 이름(회원이름)
	private Date regdate;		// util.Date!!
	private String viewcnt;
	
	private int recnt;			// **게시글 댓글(Reply)의 수 추가
	
	private String[] files;		// 첨부파일
	
	public int getBno() {
		return bno;
	}
	public String[] getFiles() {
		return files;
	}
	public void setFiles(String[] files) {
		this.files = files;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public int getRecnt() {
		return recnt;
	}
	public void setRecnt(int recnt) {
		this.recnt = recnt;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
	public String getViewcnt() {
		return viewcnt;
	}
	public void setViewcnt(String viewcnt) {
		this.viewcnt = viewcnt;
	}
	
	@Override
	public String toString() {
		return "BoardDto [bno=" + bno + ", title=" + title + ", content=" + content + ", writer=" + writer
				+ ", userName=" + userName + ", regdate=" + regdate + ", viewcnt=" + viewcnt + ", recnt=" + recnt
				+ ", files=" + Arrays.toString(files) + "]";
	}
	
	
	
}

