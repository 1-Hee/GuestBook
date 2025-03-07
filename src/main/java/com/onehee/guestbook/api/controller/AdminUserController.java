package com.onehee.guestbook.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onehee.guestbook.config.SizeConstant;
import com.onehee.guestbook.model.BoardDTO;
import com.onehee.guestbook.service.BoardService;
import com.onehee.guestbook.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/")
@CrossOrigin("*")
@Api("어드민 컨트롤러  API V1")
public class AdminUserController {

	private static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);

	private UserService userService;
	private BoardService boardService;

	@Autowired
	public AdminUserController(
			UserService userService, BoardService boardService
	) {
		this.userService = userService;
		this.boardService = boardService;
	}

	@ApiOperation(value = "방명록 조회", notes = "방명록 전체 목록을 반환해 줍니다.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "방명록 ok"), 
		@ApiResponse(code = 404, message = "no content"),
		@ApiResponse(code = 500, message = "server error") 
	})
	@GetMapping(value = "/dashboard/list")
	public ResponseEntity<?> boardList(
			int pgno, String word
	) {
		logger.debug("boardList call");
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.putIfAbsent("pgNo", String.valueOf(pgno));
			params.putIfAbsent("word", word);						
			List<BoardDTO> list = boardService.readBoardList(params, false);
			if (list != null && !list.isEmpty()) {
				return new ResponseEntity<List<BoardDTO>>(list, HttpStatus.OK);
//				return new ResponseEntity<List<MemberDto>>(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}

	}

//	@ApiOperation(value = "회원등록", notes = "회원의 정보를 받아 처리.")
//	@PostMapping(value = "/user")
//	public ResponseEntity<?> userRegister(@RequestBody MemberDto memberDto) {
//		logger.debug("userRegister memberDto : {}", memberDto);
//		try {
//			memberService.joinMember(memberDto);
//			List<MemberDto> list = memberService.listMember(null);
//			return new ResponseEntity<List<MemberDto>>(list, HttpStatus.OK);
////			return new ResponseEntity<Integer>(cnt, HttpStatus.CREATED);
//		} catch (Exception e) {
//			return exceptionHandling(e);
//		}
//
//	}
//
//	@ApiOperation(value = "회원정보", notes = "회원한명에 대한 정보.")
//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "userid", value = "아이디", required = true, dataType = "String", paramType = "path")
////			@ApiImplicitParam(name = "param1", value = "파라미터1", required = true, dataType = "String", paramType = "query"),
////			@ApiImplicitParam(name = "param2", value = "파마미터2", required = false, dataType = "int", paramType = "query") 
//	})
//	@GetMapping(value = "/user/{userid}")
//	public ResponseEntity<?> userInfo(@PathVariable("userid") String userId) {
//		logger.debug("userInfo userid : {}", userId);
//		try {
//			MemberDto memberDto = memberService.getMember(userId);
//			if (memberDto != null)
//				return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
//			else
//				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//		} catch (Exception e) {
//			return exceptionHandling(e);
//		}
//	}
//
//	@ApiOperation(value = "회원정보수정", notes = "회원정보를 수정합니다.")
//	@PutMapping(value = "/user")
//	public ResponseEntity<?> userModify(@RequestBody MemberDto memberDto) {
//		logger.debug("userModify memberDto : {}", memberDto);
//		try {
//			memberService.updateMember(memberDto);
//			List<MemberDto> list = memberService.listMember(null);
//			return new ResponseEntity<List<MemberDto>>(list, HttpStatus.OK);
//		} catch (Exception e) {
//			return exceptionHandling(e);
//		}
//	}
//
//	@ApiOperation(value = "회원정보삭제", notes = "회원정보를 삭제합니다.")
//	@DeleteMapping(value = "/user/{userid}")
//	public ResponseEntity<?> userDelete(@PathVariable("userid") String userId) {
//		logger.debug("userDelete userid : {}", userId);
//		try {
//			memberService.deleteMember(userId);
//			List<MemberDto> list = memberService.listMember(null);
//			return new ResponseEntity<List<MemberDto>>(list, HttpStatus.OK);
//		} catch (Exception e) {
//			return exceptionHandling(e);
//		}
//	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
