package com.onehee.guestbook.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.onehee.guestbook.model.UserDTO;

public interface UserDao {
	// C
	int addUser(UserDTO userDTO) throws SQLException;	
	// R
	List<UserDTO> selectUserList(Map<String, String> map) throws SQLException; // paging 추가를 위해..
	UserDTO getUserInfo(String userName) throws SQLException;
	UserDTO loginUser(UserDTO userDto) throws SQLException;
	boolean checkUserName(String userName)throws SQLException;
	// U
	void updateUserInfo(UserDTO userDTO) throws SQLException;
	int updateUserPwd(UserDTO userDTO, String newPwd) throws SQLException;	
	// D
	void deleteUserInfo(String userName) throws SQLException;
}
