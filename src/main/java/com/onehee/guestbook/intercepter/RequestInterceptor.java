package com.onehee.guestbook.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class RequestInterceptor implements HandlerInterceptor { 

	@Override
	public boolean preHandle(
		HttpServletRequest request, 
		HttpServletResponse response, Object handler
	)throws Exception {
		HttpSession session = request.getSession();
		
		/*		
		if(memberDto == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		*/		
		return true;
	}
	
} 
