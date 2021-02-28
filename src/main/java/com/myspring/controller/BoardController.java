package com.myspring.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.myspring.dto.BoardDto;
import com.myspring.dto.PagingVO;



/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/board/*") // */board/* 앞에 * 빼야!
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping("/list")
	public String list(PagingVO pvo, Model model
			, @RequestParam(value="nowPage", required=false)String nowPage
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
	
	@RequestMapping("/insert.do")
	public String insert(BoardDto dto) {
		
		sqlSession.insert("boardmapper.insert", dto);
		
		return "redirect:list.do";
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
	
	
	
}
