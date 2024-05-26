package com.onehee.guestbook.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.onehee.guestbook.model.UserDTO;
import com.onehee.guestbook.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private final UserService userService;
	
	@Autowired
	private UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/login")
	public String login() {				
		return "user/login";
	}
	
	@PostMapping("/login")
	public String doLogin(
			UserDTO userDto, Model model, 
			HttpSession session, HttpServletResponse response
	) {
		logger.info("user sumbit login info => {} , {} ", userDto.getUserName(), userDto.getUserPassword());		
		try {
			UserDTO dUserDTO = userService.loginUser(userDto);
			logger.debug("[DB] user info => : {}", userDto.toString());
			if(dUserDTO != null) {
				session.setAttribute("userinfo", userDto);				
				Cookie cookie = new Cookie("user_name", userDto.getUserName());
				String path = session.getServletContext().getContextPath();
				cookie.setPath(path);
//				if("ok".equals(member.getSaveId())) {
//					cookie.setMaxAge(60*60*24*365*40);
//				} else {
//					cookie.setMaxAge(0);
//				}
				cookie.setMaxAge(60*60*24);
				response.addCookie(cookie);
				return "redirect:/guestbook";
			} else {
				model.addAttribute("msg", "닉네임 또는 비밀번호 확인 후 다시 로그인 해주세요.");
				return "user/login";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "로그인 중 문제 발생!!!");
			return "redirect:/";
		}		
	}
		
	@GetMapping("/register")
	public String register() {
		return "user/register";		
	}

	@GetMapping("/profile")
	public String profile(HttpSession session, HttpServletResponse response) {		
		Object userInfo = session.getAttribute("userinfo");				
		if(userInfo == null) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			session.setAttribute("msg", "허용되지 않은 접근입니다.");		
			return "redirect:/user/login";
		}else {
			response.setStatus(HttpServletResponse.SC_OK);
			return "user/profile";					
		}		
	}
		
	// 회원가입
	@PostMapping("/register")
	public String doRegister(UserDTO userDTO, Model model, HttpSession session) throws Exception {
		logger.debug("user request register : {}", userDTO.toString());
		
		try {
			int result = userService.joinUser(userDTO);			
			if(result > 0) {
				session.setAttribute("msg", "회원가입이 완료되었습니다.");				
				return "redirect:/user/login";
				
			}else {
				session.setAttribute("msg", "회원가입 중 오류가 발생하였습니다");			
				return "redirect:/user/register";				
			}			
		}catch(Exception e) {
			session.setAttribute("msg", "회원가입 중 오류가 발생하였습니다");					
			return "redirect:/user/register";
		}		
	}
	
	// 닉네임 체크
	@GetMapping("/idcheck/{userName}")
	@ResponseBody
	public String idCheck(@PathVariable("userName") String userName) throws Exception {
		logger.debug("idCheck userid : {}", userName);
		boolean result = userService.checkUserName(userName);			
		return String.valueOf(result);
	}
	
	
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	// 비밀번호 변경
	@PostMapping("/changepwd")
	public String changePwd(@RequestParam Map<String, String> argments, Model model, HttpSession session) {
		String emptyValue = "null";
		String qUserName = argments.getOrDefault("userName", emptyValue);
		String qCurrentPwd = argments.getOrDefault("currentPassword", emptyValue);
		String qNewPwd= argments.getOrDefault("newPassword", emptyValue);		
		logger.info("server accept info => {}", qUserName);						
		logger.info("server accept info => {}", qCurrentPwd);		
		logger.info("server accept info => {}", qNewPwd);						
		try {
			UserDTO qUserDTO = new UserDTO(qUserName, qCurrentPwd);
			int result = userService.editUserPwd(qUserDTO, qNewPwd);
			if(result > 0) {
				session.invalidate();
			}else {
				session.invalidate();
				logger.info("user fail to update password! {}", qUserName);						
			}			
		}catch(Exception e) {
			session.invalidate();
			logger.info("user fail to update password! {}", qUserName);						
		}		
		return "redirect:/user/login";									
	}
	
	// 회원 탈퇴
	@PostMapping("/withrawal")
	public String withrawalUser(String userName, Model model, HttpSession session) {
		session.invalidate();
		logger.info("user try withrawal => {}.", userName);		
		
		try {
			userService.removeUserInfo(userName);			
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "회원 탈퇴 중  문제가 발생했습니다.");
		}
		return "redirect:/";
	}
}
