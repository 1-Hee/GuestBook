package com.onehee.guestbook.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.onehee.guestbook.model.BoardDTO;
import com.onehee.guestbook.model.UserDTO;
import com.onehee.guestbook.service.BoardService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private final BoardService boardService;

	@Autowired
	private HomeController(BoardService boardService) {
		this.boardService = boardService;
	}
		
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@GetMapping("/")
	public String index(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);		
		String formattedDate = dateFormat.format(date);		
		model.addAttribute("serverTime", formattedDate );		
		return "index";
	}
	
	// 게시글 관련
	@GetMapping("/guestbook")
	public String geustbook() {		
		return "guestbook/home";
	}
	
	@GetMapping("/write")
	public String moveWrite() {
		return "guestbook/write";		
	}
	
	@GetMapping("/article")
	public String moveArticle(
			@RequestParam(name = "no") int no, 
		HttpSession session, HttpServletResponse response
	) {	
		try {
			if(no < 1) { throw new Exception(); }
			BoardDTO dBoardDto = boardService.getBoardInfo(no);
			logger.debug("[DB] baord info => : {}", dBoardDto.toString());			
			session.setAttribute("article", dBoardDto);
			return "guestbook/article";		
		}catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return "redirect:/guestbook";		
		}	
	}
	
	@GetMapping("/edit")
	public String moveEdit(
		@RequestParam("idx") int idx,
		HttpSession session, HttpServletResponse response
	) throws Exception {
		if(idx <= 0) {
			return "redirect:/guestbook";
		}				
		logger.info("server get idx => {}", idx);
		BoardDTO board = boardService.getBoardInfo(idx);		
		session.setAttribute("boardInfo", board);
		return "guestbook/edit";		
	}			
}
