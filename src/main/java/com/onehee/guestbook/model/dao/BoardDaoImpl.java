package com.onehee.guestbook.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onehee.guestbook.model.BoardDTO;
import com.onehee.guestbook.util.DBUtil;

@Repository
public class BoardDaoImpl implements BoardDao {

	private final DataSource dataSource;
	private final DBUtil dbUtil;

	@Autowired
	private BoardDaoImpl(DataSource dataSource, DBUtil dbUtil) {
		this.dataSource = dataSource;
		this.dbUtil = dbUtil;
	}

	
	@Override
	public int insertArticle(BoardDTO boardDTO) throws SQLException {
		int rowNo = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO guest_book\n ");
			sql.append("(title, author, content) VALUES (?, ?, ?); ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, boardDTO.getTitle());
			pstmt.setString(2, boardDTO.getAuthor());
			pstmt.setString(3, boardDTO.getContent());
			rowNo = pstmt.executeUpdate();						
		}finally {
			dbUtil.close(pstmt, conn);
		}
		return rowNo;
	}

	@Override
	public List<BoardDTO> selectBoardList(Map<String, String> map) throws Exception {
		List<BoardDTO> mBoardList = new ArrayList<BoardDTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int pgNo = 1;
		int size = 10;
		try  {
			pgNo = Integer.parseInt(map.get("pgNo"));		
			size = Integer.parseInt(map.get("size"));					
		}catch(Exception e){
			
		}
		
		try {
			conn = dataSource.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT idx, title, author, content, create_at\n ");
			sql.append("FROM guest_book ");
			sql.append("WHERE board_status != 0 \n");
			sql.append("ORDER BY create_at DESC LIMIT ?, ?; ");
			pstmt = conn.prepareStatement(sql.toString());			
			pstmt.setInt(1, (pgNo-1));
			pstmt.setInt(2, size);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int dIdx = rs.getInt("idx");
				String dTitle = rs.getString("title");
				String dAuthor = rs.getString("author");
				String dContent = rs.getString("content");				
				Date dCreateAt = rs.getDate("create_at");
				BoardDTO dBoardDto = new BoardDTO(
						dIdx, dTitle, dAuthor, dContent, dCreateAt
				);
				mBoardList.add(dBoardDto);
			}						
		}finally {
			dbUtil.close(pstmt, conn);
		}		
		return mBoardList;
	}

	@Override
	public BoardDTO getBoardInfo(int idx) throws Exception {
		BoardDTO dBoardDto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT idx, title, author, content, create_at\n ");
			sql.append("FROM guest_book \n");
			sql.append("WHERE idx = ? ; ");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				int dIdx = rs.getInt("idx");
				String dTitle = rs.getString("title");
				String dAuthor = rs.getString("author");
				String dContent = rs.getString("content");				
				Date dCreateAt = rs.getDate("create_at");
				dBoardDto = new BoardDTO(
						dIdx, dTitle, dAuthor, dContent, dCreateAt
				);
			}			
		}finally {
			dbUtil.close(pstmt, conn);
		}		
		return dBoardDto;
	}

	@Override
	public void updateBoard(BoardDTO boardDTO) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowNo = 0;
		try {
			conn = dataSource.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE guest_book \n");
			sql.append("SET title = ?, content = ? \n");
			sql.append("WHERE idx = ? AND author = ?;");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, boardDTO.getTitle());
			pstmt.setString(2, boardDTO.getContent());
			pstmt.setInt(3, boardDTO.getIdx());
			pstmt.setString(4, boardDTO.getAuthor());
			rowNo = pstmt.executeUpdate();					
		}finally {
			dbUtil.close(pstmt, conn);
		}		
	}

	@Override
	public void deleteBoard(int idx) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowNo = 0;
		try {
			conn = dataSource.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE guest_book \n");
			sql.append("SET board_status = ? \n");
			sql.append("WHERE idx = ?;");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, 0);
			pstmt.setInt(2, idx);			
			rowNo = pstmt.executeUpdate();						
		}finally {
			dbUtil.close(pstmt, conn);
		}				
	}

}
