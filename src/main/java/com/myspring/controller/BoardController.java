package com.myspring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myspring.dto.BoardDto;
import com.myspring.dto.PagingVO;

@Controller
@RequestMapping("/board/*") // */board/* 앞에 * 빼야!
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping("/list")
	public String list(PagingVO pvo, Model model
			, @RequestParam(value="nowPage", required=false, defaultValue = "1")String nowPage
			, @RequestParam(value="cntPerPage", required=false)String cntPerPage
			, @RequestParam(value = "searchOption", defaultValue = "title") String searchOption
			, @RequestParam(value = "keyword", defaultValue = "") String keyword
			) {
		// 게시물 글 총갯수
		int total = sqlSession.selectOne("boardmapper.countBoard");
		
		System.out.println("total: "+ total);
		
		if (nowPage == null && cntPerPage == null) {
			nowPage = "1";
			cntPerPage = "10";
		} else if (nowPage == null) {
			nowPage = "1";
		} else if (cntPerPage == null) { 
			cntPerPage = "10";
		}
		
		// pvo 객체 생성
		pvo = new PagingVO(total, Integer.parseInt(nowPage), Integer.parseInt(cntPerPage));
		
		int start = pvo.getStart();
		int end = pvo.getEnd();
		
		Map<String, Object> parammap = new HashMap<String, Object>();
		
		parammap.put("start", start);
		parammap.put("end", end);
		// 단순히 pvo로 put한 map으로는 sql오류가 남! 각각 따로 start, end를 넣어주어야 됨!
		parammap.put("searchOption", searchOption);
		parammap.put("keyword", keyword);
		List<BoardDto> selectAll = sqlSession.selectList("boardmapper.selectAll", parammap);
		
		System.out.println("pvo : "+pvo);
		model.addAttribute("paging", pvo);
		
		System.out.println("selectAll :" + selectAll);
		model.addAttribute("selectAll", selectAll);
		
		return "board/list";
	}
	
	@RequestMapping("/write.do")
	public String write() {
		
		return "board/write";
	}
	
	@Transactional
	@RequestMapping("/insert.do")
	public String insert(BoardDto dto, HttpSession session) throws Exception {
//		public String insert(BoardDto dto) {
		System.out.println("dto: "+dto);
		
		String writer = (String) session.getAttribute("id");
		System.out.println("id: "+writer );
		dto.setWriter(writer);
		sqlSession.insert("boardmapper.insert", dto);
		
//		String[] files = dto.getFiles(); // 첨부파일 배열
//		if(files != null) { // 첨부파일이 없으면 메서드 종료
//		// 첨부파일들의 정보를 tbl_attach 테이블에 insert
//			for(String name : files){ 
//		    	sqlSession.insert("boardmapper.addAttach", name);
//			}
//		}
		
		return "redirect:list.do";
	}

	@RequestMapping("/getAttach/{bno}")
	@ResponseBody
	public List<String> getAttach(@PathVariable("bno") int bno) {
	    return sqlSession.selectList("boardmapper.getAttach", bno);
	}
	
	
	@RequestMapping("/view.do")
	public String view(Model model, @RequestParam("bno") int bno) {
		
		sqlSession.update("boardmapper.increaseViewcnt", bno);	// view 볼시, 조회수 증가!
		
		BoardDto dto = sqlSession.selectOne("boardmapper.selectView", bno);		//view페이지 데이터 전달 객체 dto 셋팅
		
		System.out.println("selectViewOne :"+ dto);
		
		model.addAttribute("selectViewOne", dto);
		
		return "board/view";
	}
	
	
	@RequestMapping("/update.do")
	public String update(BoardDto dto) {
		// dto.getbno()는 비효율적! 하나끄집어올려고 다가져오면 로드 부하만 커지는 꼴!
		System.out.println("upadte_dto :"+ dto);
		sqlSession.update("boardmapper.update", dto);
		
		// bno, title, content
		return "redirect:list.do";
	}
	
	@RequestMapping("/delete.do")
	public String delete(@RequestParam("bno") int bno) {
		// dto.getbno()는 비효율적! 하나끄집어올려고 다가져오면 로드 부하만 커지는 꼴!
		sqlSession.delete("boardmapper.delete", bno);
		
		return "redirect:list.do";
	}
	
	@RequestMapping("/deleteFile.do")
	public void deleteFile(String fullname) throws Exception {
		// TODO Auto-generated method stub
		sqlSession.delete("boardmapper.deleteFile", fullname);
	}
	
	@RequestMapping("/excelDown.do")
	public void excelDown(HttpServletResponse response) throws Exception{
		
		List<BoardDto> excelList = sqlSession.selectList("selectAll2");
		
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("게시판");

	    Row row = null;
	    Cell cell = null;
	    int rowNo = 0;

	    // 테이블 헤더용 스타일
	    CellStyle headStyle = wb.createCellStyle();
	    
	    // 가는 경계선을 가집니다.
	    headStyle.setBorderTop(BorderStyle.THIN);
	    headStyle.setBorderBottom(BorderStyle.THIN);
	    headStyle.setBorderLeft(BorderStyle.THIN);
	    headStyle.setBorderRight(BorderStyle.THIN);
	    
	    // 배경색은 노란색입니다.
	    headStyle.setFillForegroundColor(HSSFColorPredefined.YELLOW.getIndex());
	    headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

	    // 데이터는 가운데 정렬합니다.
	    headStyle.setAlignment(HorizontalAlignment.CENTER);

	    // 데이터용 경계 스타일 테두리만 지정
	    CellStyle bodyStyle = wb.createCellStyle();
	    bodyStyle.setBorderTop(BorderStyle.THIN);
	    bodyStyle.setBorderBottom(BorderStyle.THIN);
	    bodyStyle.setBorderLeft(BorderStyle.THIN);
	    bodyStyle.setBorderRight(BorderStyle.THIN);

	    // 헤더 생성
	    row = sheet.createRow(rowNo++);
	    
	    cell = row.createCell(0);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("번호");
	    
	    cell = row.createCell(1);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("제목");
	    
	    cell = row.createCell(2);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("내용");
	    
	    cell = row.createCell(3);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("작성자");

	    cell = row.createCell(4);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("작성일");
	    
	    cell = row.createCell(5);
	    cell.setCellStyle(headStyle);
	    cell.setCellValue("조회수");
	    
	    // 데이터 부분 생성

	    for(BoardDto dto : excelList) {

	        row = sheet.createRow(rowNo++);

	        cell = row.createCell(0);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(dto.getBno());

	        cell = row.createCell(1);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(dto.getTitle());
	        
	        cell = row.createCell(2);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(dto.getContent());

	        cell = row.createCell(3);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(dto.getWriter());
	        
	        cell = row.createCell(4);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(dto.getRegdate());
	        
	        cell = row.createCell(5);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(dto.getViewcnt());

	    }

	    // 컨텐츠 타입과 파일명 지정
	    response.setContentType("ms-vnd/excel");
	    response.setHeader("Content-Disposition", "attachment;filename=board_excelDown.xls");

	    // 엑셀 출력
	    wb.write(response.getOutputStream());
	    wb.close();

	}
	
}
