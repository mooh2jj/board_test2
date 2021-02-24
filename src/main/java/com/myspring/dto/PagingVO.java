package com.myspring.dto;

public class PagingVO {
	
	// nowPage:현재페이지, startPage: 시작페이지, endPage:끝페이지, total:게시글 총 갯수, 
	// cntPerPage:페이지당 글 갯수, lastPage:마지막페이지
	private int nowPage, startPage, endPage, total, cntPerPage, lastPage;
	// SQL쿼리에 쓸 start, end
	private int start, end;
	// 페이지 목록에 나타낼 페이지 번호의 수
	private int cntPage = 10;
	
	public PagingVO() {
//		this.start = 1;	// 의미 없음.
//		this.end = 10;
	}
	
	public PagingVO(int total, int nowPage, int cntPerPage) {
		setNowPage(nowPage);
		setCntPerPage(cntPerPage);
		setTotal(total);
		calcLastPage(getTotal(), getCntPerPage());
		calcStartEndPage(getNowPage(), cntPage);
		calcStartEnd(getNowPage(), getCntPerPage());
	}
	
	// lastPage: 제일 마지막 페이지 계산 , Math.ceil 바로 소수점 올림하는 계산 
	// ex. 110/10 == 11()
	public void calcLastPage(int total, int cntPerPage) {
		setLastPage((int) Math.ceil((double)total / (double)cntPerPage));
	}
	
	// startPage, endPage 시작, 끝 페이지 계산
	public void calcStartEndPage(int nowPage, int cntPage) {
		// endPage 끝 페이지 계산 ex. Math.ceil(11 / 10) == 2 * 10 = 20(endPage)
		setEndPage(((int)Math.ceil((double)nowPage / (double)cntPage)) * cntPage);
		
		if (getLastPage() < getEndPage()) {
			setEndPage(getLastPage());
		}
		// startPage 시작 페이지 계산 11-10 +1 == 2(startPage)
		setStartPage(getEndPage() - cntPage + 1);
		if (getStartPage() < 1) {
			setStartPage(1);	// 무조건 startpage는 1로 설정!
		}
	}
	
	// DB 쿼리에서 사용할 start, end값 계산
	public void calcStartEnd(int nowPage, int cntPerPage) {
		setEnd(nowPage * cntPerPage);		// 11*10 == 110(end)
		setStart(getEnd() - cntPerPage);	// 110-10 == 100(start)
	}
	
	public int getNowPage() {
		return nowPage;
	}
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCntPerPage() {
		return cntPerPage;
	}
	public void setCntPerPage(int cntPerPage) {
		this.cntPerPage = cntPerPage;
	}
	public int getLastPage() {
		return lastPage;
	}
	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}	
	public int setCntPage() {
		return cntPage;
	}
	public void getCntPage(int cntPage) {
		this.cntPage = cntPage;
	}
	
	@Override
	public String toString() {
		return "PagingVO [nowPage=" + nowPage + ", startPage=" + startPage + ", endPage=" + endPage + ", total=" + total
				+ ", cntPerPage=" + cntPerPage + ", lastPage=" + lastPage + ", start=" + start + ", end=" + end
				+ ", cntPage=" + cntPage + "]";
	}
}
