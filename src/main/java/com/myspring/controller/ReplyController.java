package com.myspring.controller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myspring.dto.ReplyVO;


@RestController
@RequestMapping("/reply/*")
public class ReplyController {

	@Autowired
	private SqlSession sqlSession;
	
	
	// 댓글 입력
	@RequestMapping(value = "insert")
	public void insert(ReplyVO vo) {
		
		System.out.println("vo 받은 거:" +  vo);
		int result = sqlSession.insert("replymapper.insertReply", vo);
		
		System.out.println("결과 result : "+ result);
	}
	
	// 2) ajax 방식
	@RequestMapping("listJson")
	@ResponseBody	// 리턴데이터를 json으로 변환(생략가능)
	public List<ReplyVO> listJson(@RequestParam("bno") int bno) {
		
		List<ReplyVO> list = sqlSession.selectList("replymapper.listReply", bno);
		
		System.out.println("list:"+ list);
		
		return list;
	}
}
