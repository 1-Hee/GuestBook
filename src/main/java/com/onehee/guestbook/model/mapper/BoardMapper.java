package com.onehee.guestbook.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.onehee.guestbook.model.BoardDTO;

@Mapper
public interface BoardMapper {
	// C
	int insertArticle(BoardDTO boardDTO) throws SQLException;	
	// R
	List<BoardDTO> selectBoardList(Map<String, Object> map) throws SQLException; // paging 추가를 위해..
	BoardDTO getBoardInfo(int idx) throws SQLException;
	int getNavCnt(Map<String, Object> params) throws SQLException;
	// U
	void updateBoard(BoardDTO boardDTO) throws SQLException;
	// D
	void deleteBoard(int idx) throws SQLException;
}
