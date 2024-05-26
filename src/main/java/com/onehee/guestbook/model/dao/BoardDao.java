package com.onehee.guestbook.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.onehee.guestbook.model.BoardDTO;

public interface BoardDao {
	// C
	int insertArticle(BoardDTO boardDTO) throws SQLException;	
	// R
	List<BoardDTO> selectBoardList(Map<String, String> map) throws Exception; // paging 추가를 위해..
	BoardDTO getBoardInfo(int idx) throws Exception;
	// U
	void updateBoard(BoardDTO boardDTO) throws Exception;
	// D
	void deleteBoard(int idx) throws Exception;

}
