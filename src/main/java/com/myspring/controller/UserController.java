package com.myspring.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myspring.dto.UserVO;

@Controller
@RequestMapping("/member/*")
public class UserController {

	@Autowired
	SqlSession sqlSession;
	
	@RequestMapping("/home")
	public String login() {
		
		return "member/home";
	}
	
	@RequestMapping("/login")
	public String login(UserVO uvo, HttpServletRequest req, RedirectAttributes rttr, Model model) {
		
		System.out.println("post login start...");
		
		HttpSession session = req.getSession();
		
		UserVO lvo = sqlSession.selectOne("usermapper.login", uvo);
		
		if(lvo == null) {
			System.out.println("로그인 실패");
//			session.setAttribute("member", null);
//			rttr.addFlashAttribute("msg", false); 이거 안됨
			model.addAttribute("msg", "false");
			return "member/home";
		}else {
			System.out.println("로그인 성공");
			session.setAttribute("id", lvo.getId());
			session.setAttribute("name", lvo.getName());
			return "member/list";
		}
		
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest req, RedirectAttributes rttr, Model model) {
		
		System.out.println("post logout...");
		
		req.getSession().invalidate();
		req.getSession(true);
		// invalidate()로 현재 사용하고 있는 세션을 무효화한다. 그리고 getSession(true)를 통해서 새로운 세션ID를 발급해준다. 이렇게 처리하여 세션을 초기화
//		rttr.addFlashAttribute("msg", "logout");
		model.addAttribute("msg", "logout");
		return "member/home";
	}
	
	@RequestMapping("/list")
	public String selectList(Model model
			, @RequestParam(value = "searchOption", defaultValue = "all") String searchOption
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
	public String insert(UserVO uvo) throws IOException {
		
		System.out.println("before insert uvo: "+uvo);
		String fileName = null;
		MultipartFile uploadFile = uvo.getUploadFile();
		if(!uploadFile.isEmpty()) {
			String originalFileName = uploadFile.getOriginalFilename();
			String ext = FilenameUtils.getExtension(originalFileName);	// 확장자 구하기
			UUID uuid = UUID.randomUUID();	// UUID 구하기
			fileName = uuid+"."+ext;
			uploadFile.transferTo(new File("I:\\upload\\"+fileName));
		}
		System.out.println("fileName: "+fileName);
		uvo.setFileName(fileName);
		System.out.println("after insert uvo: "+uvo);
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
	
	// id 중복체크
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
