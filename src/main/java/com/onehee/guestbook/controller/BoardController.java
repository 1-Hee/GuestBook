package com.onehee.guestbook.controller;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.onehee.guestbook.config.SizeConstant;
import com.onehee.guestbook.model.BoardDTO;
import com.onehee.guestbook.model.Contents;
import com.onehee.guestbook.service.BoardService;
import com.onehee.guestbook.util.PageNavigation;

@RestController
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private final BoardService boardService;
	
	@Autowired
	private BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	// 게시글 작성
	@PostMapping("/write")
	public ModelAndView writeArticle(
			HttpServletResponse response,
			BoardDTO boardDTO
	) {		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/guestbook");		
		logger.info("Server accept dto => {}", boardDTO.toString());				
		try {
			int rowNo = boardService.writeArticle(boardDTO);			
		}catch (Exception e) {
			e.printStackTrace();
			logger.info("server accept error cause : { }", e.getCause());
		}		
		return mav;
	}
			
	// 게시글 불러오기 
	@GetMapping("/list")
	public Contents getList(
			HttpServletRequest request,
			@RequestParam(name = "pgno") int pgno, 
			@RequestParam(name = "word") String word,
			HttpServletResponse response
	) {		
		String userAgent = request.getHeader("User-Agent");
		logger.info("parms => {} , {}", pgno, word);			
		Contents contents = new Contents();
		boolean isMobile = userAgent.toLowerCase().contains("mobile"); // 모바일이라면 리스트 크기가 달라져야 함.
		logger.info("agent => {} {} ", isMobile, userAgent);					
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("pgno", String.valueOf(pgno));
			params.put("word", word);						
			List<BoardDTO> boardList = boardService.readBoardList(params, isMobile);
			// logger.info("list => {}", boardList);			
			contents.setBoardList(boardList);
			PageNavigation pgNav = boardService.getPageNav(params, isMobile);
			contents.setPgNav(pgNav);
			contents.setKeyword(word);
			response.setStatus(HttpServletResponse.SC_OK);
		}catch (Exception e) {
			e.printStackTrace();			
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		}		
		return contents;
	}
	
	// 게시글 삭제
	@DeleteMapping("/delete")
	public ResponseEntity<Void> deleteArticle(@RequestParam("idx") int idx) {		
		
		logger.info("Server accept dto => {}", idx);		
		
		try {
			boardService.removeBoard(idx);
		}catch (Exception e) {
			e.printStackTrace();
			logger.info("server accept error cause : { }", e.getCause());
		}		
		return ResponseEntity.ok(null);
	}
	
	// 게시글 수정
	@PutMapping("/edit")
	public ResponseEntity<Void> editArticle(@RequestBody BoardDTO boardDTO) {		
		
		logger.info("Server accept dto => {}", boardDTO.toString());		
		
		try {
			// boardService.removeBoard(idx);
			boardService.editBoard(boardDTO);
			return ResponseEntity.ok(null);			
		}catch (Exception e) {
			e.printStackTrace();
			logger.info("server accept error cause : { }", e.getCause());
			return (ResponseEntity<Void>) ResponseEntity.internalServerError();
		}		
	}
	
	
}
