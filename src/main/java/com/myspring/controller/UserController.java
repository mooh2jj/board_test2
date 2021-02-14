package com.myspring.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myspring.dto.UserVO;

@Controller
@RequestMapping("/member/*")
public class UserController {

	@Autowired
	SqlSession sqlSession;
	
	@RequestMapping("/list")
	public String selectList(Model model
			, @RequestParam(value = "searchOption", defaultValue = "id") String searchOption
			, @RequestParam(value = "keyword", defaultValue = "") String keyword) {
		
		Map<String, Object> parammap = new HashMap<String, Object>();
		
		parammap.put("searchOption", searchOption);
		parammap.put("keyword", keyword);
		
		List<UserVO> selectAll = sqlSession.selectList("usermapper.select",parammap);
		model.addAttribute("selectAll", selectAll);
		
		System.out.println("selectAll: "+ selectAll);
		
		return "member/list";
	}
	
	
	@RequestMapping("/join")
	public String join() {
		
		return "member/join";
	}
	
	@RequestMapping("/insert")
	public String insert(UserVO uvo) {
		
		sqlSession.insert("usermapper.insert", uvo);
		
		return "redirect:list";
	}
	
	@RequestMapping("/view")
	public String view(@RequestParam("id") String id, Model model) {
		
		UserVO viewOne = sqlSession.selectOne("usermapper.view", id);
		
		System.out.println("viewOne: "+ viewOne);
		
		model.addAttribute("viewOne", viewOne);
		
		return "member/view";
	}
	
	@RequestMapping("/modify")
	public String modify(UserVO uvo) {
		
		sqlSession.update("usermapper.modify", uvo);
		System.out.println("update_uvo: "+ uvo);
		
		return "redirect:list";
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam("id") String id) {
		
		sqlSession.delete("usermapper.delete", id);
		System.out.println("delete 되었음...");
		
		return "redirect:list";
	}
	
	@ResponseBody
	@RequestMapping("/idcheck")
	public String idcheck(HttpServletRequest request) {
		
		String id = request.getParameter("id").trim();	// 중간에 공백은 제거 못해! ex. h ong => hong (x)
		
		int resultCount = sqlSession.selectOne("usermapper.idcheck", id);
		
		System.out.println("idcheck 되었음...id:"+id);
		
		String result = String.valueOf(resultCount);
		
		System.out.println("idcheck_result :"+result);
		
		return result;
	}
	
	
	
}
