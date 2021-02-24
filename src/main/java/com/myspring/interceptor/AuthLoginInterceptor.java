package com.myspring.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter{

	private static final Logger log = LoggerFactory.getLogger(AuthLoginInterceptor.class);
	
	// preHandle() : 컨트롤러보다 먼저 수행되는 메서드
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("preHandle start...");
		HttpSession session = request.getSession();
		
		Object obj = session.getAttribute("login");
		
		//exclude-mapping 대신, 요청된 url
		String requestUrl = request.getRequestURL().toString();
		
		//하단의 Url 체크를 통해, login 페이지는 예외처리를 해줘야 무한 리디렉션에서 벗어날 수 있다
		if(requestUrl.contains("/member/login")){
			return true;
		}
		//세션 체크
		if( obj == null ) {
			// 로그인이 안되어 있는 상태임으로 로그인 폼으로 다시 돌려보냄(redirect)
			response.sendRedirect("/member/login");
			return false;	// 더이상 컨트롤러 요청으로 가지 않도록 false로 반환함
		}
		// preHandle의 return은 컨트롤러 요청 uri로 가도 되냐 안되냐를 허가하는 의미임
		// 따라서 true로하면 컨트롤러 uri로 가게 됨.
		return true;
	}
	
    // 컨트롤러가 수행되고 화면이 보여지기 직전에 수행되는 메서드
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    	log.info("postHandle start...");
        super.postHandle(request, response, handler, modelAndView);
    }  
}
