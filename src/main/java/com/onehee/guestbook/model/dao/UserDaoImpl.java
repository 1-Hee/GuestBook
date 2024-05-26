package com.onehee.guestbook.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.onehee.guestbook.model.UserDTO;
import com.onehee.guestbook.util.DBUtil;

@Repository
public class UserDaoImpl implements UserDao {
	
	private final DataSource dataSource;
	private final DBUtil dbUtil;
	@Autowired
	private UserDaoImpl(DataSource dataSource, DBUtil dbUtil) {
		this.dataSource = dataSource;
		this.dbUtil = dbUtil;
	}

	@Override
	public int addUser(UserDTO userDTO) throws SQLException {
		int rowNo = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("insert into user_info(user_name, user_password)\n ");
			sql.append("values (?, ?);");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, userDTO.getUserName());
			pstmt.setString(2, userDTO.getUserPassword());
			rowNo = pstmt.executeUpdate();			
		}finally {
			dbUtil.close(pstmt, conn);
		}		
		return rowNo;
	}

	@Override
	public List<UserDTO> selectUserList(Map<String, String> map) throws SQLException {		
		List<UserDTO> mUserList = new ArrayList<UserDTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT user_name, user_password\n ");
			sql.append("FROM user_info \n ");
			sql.append("WHERE user_status = 1 ;");
			pstmt = conn.prepareStatement(sql.toString());
			// todo 페이징 처리
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String dUserName = rs.getString(1);
				String dUserPassword = rs.getString(2);
				UserDTO mUserDTO = new UserDTO(dUserName, dUserPassword);
				mUserList.add(mUserDTO);
			}			
		}finally {
			dbUtil.close(pstmt, conn);
		}
		return mUserList;
	}

	@Override
	public UserDTO getUserInfo(String userName) throws SQLException {
		UserDTO mUserDTO = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT user_name, user_password\n");
			sql.append("FROM user_info \n ");
			sql.append("WHERE WHERE user_status = 1 AND user_name = ? ;");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, userName);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String dUserName = rs.getString(1);
				String dUserPassword = rs.getString(2);
				mUserDTO = new UserDTO(dUserName, dUserPassword);
			}			
		}finally {
			dbUtil.close(pstmt, conn);
		}		
		return mUserDTO;
	}
	
	@Override
	public UserDTO loginUser(UserDTO userDto) throws SQLException {
		UserDTO dUserDTO = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT user_name, user_password\n");
			sql.append("FROM user_info \n");
			sql.append("WHERE user_status = 1 AND user_name = ? AND user_password = ?;");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, userDto.getUserName());
			pstmt.setString(2, userDto.getUserPassword());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String dUserName = rs.getString(1);
				String dUserPwd = rs.getString(2);				
				dUserDTO = new UserDTO(dUserName, dUserPwd);
			}			
		}finally {
			dbUtil.close(pstmt, conn);
		}
		return dUserDTO;
	}


	@Override
	public boolean checkUserName(String userName)  throws SQLException {
		boolean dbFlag = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ( COUNT(idx) = 0 ) as cnt\n ");
			sql.append("FROM user_info\n ");
			sql.append("WHERE user_name = ?;");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, userName);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dbFlag = rs.getBoolean(1);
			}			
		}finally {
			dbUtil.close(pstmt, conn);
		}					
		return dbFlag;
	}

	@Override
	public void updateUserInfo(UserDTO userDTO) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowNo = 0;
		try {
			conn = dataSource.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE user_info \n");
			sql.append("SET user_name = ?, user_password = ? \n");
			sql.append("WHERE user_name = ?;");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, userDTO.getUserName());
			pstmt.setString(2, userDTO.getUserPassword());
			pstmt.setString(3, userDTO.getUserName());
			rowNo = pstmt.executeUpdate();			
		}finally {
			dbUtil.close(pstmt, conn);
		}		
	}
	
	@Override
	public int updateUserPwd(UserDTO userDTO, String newPwd) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowNo = -1;
		try {
			conn = dataSource.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE user_info \n");
			sql.append("SET user_password = ? \n");
			sql.append("WHERE user_name = ? AND user_password = ?;");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, newPwd);
			pstmt.setString(2, userDTO.getUserName());
			pstmt.setString(3, userDTO.getUserPassword());
			rowNo = pstmt.executeUpdate();			
		}finally {
			dbUtil.close(pstmt, conn);
		}		
		return rowNo;
	}

	@Override
	public void deleteUserInfo(String userName) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int rowNo = 0;
		try {
			conn = dataSource.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE user_info \n");
			sql.append("SET user_status = ? \n");
			sql.append("WHERE user_name = ?;");
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, 0);
			pstmt.setString(2, userName);			
			rowNo = pstmt.executeUpdate();			
		}finally {
			dbUtil.close(pstmt, conn);
		}						
	}

}
