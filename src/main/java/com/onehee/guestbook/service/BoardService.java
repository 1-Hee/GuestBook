package com.onehee.guestbook.service;

import java.util.List;
import java.util.Map;

import com.onehee.guestbook.model.BoardDTO;
import com.onehee.guestbook.util.PageNavigation;

public interface BoardService {
	// C
	int writeArticle(BoardDTO boardDTO) throws Exception;	
	// R
	List<BoardDTO> readBoardList(Map<String, Object> map, boolean isMobile) throws Exception; // paging 추가를 위해..
	BoardDTO getBoardInfo(int idx) throws Exception;
	public PageNavigation getPageNav(Map<String, Object> params, boolean isMobile) throws Exception;
	// U
	void editBoard(BoardDTO boardDTO) throws Exception;
	// D
	void removeBoard(int idx) throws Exception;
}
