package com.onehee.guestbook.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.onehee.guestbook.model.UserDTO;

@Mapper
public interface UserMapper {
	// C
	int addUser(UserDTO userDTO) throws SQLException;	
	// R
	List<UserDTO> selectUserList(Map<String, Integer> map) throws SQLException; // paging 추가를 위해..
	UserDTO getUserInfo(String userName) throws SQLException;
	UserDTO loginUser(UserDTO userDto) throws SQLException;
	boolean checkUserName(String userName)throws SQLException;
	// U
	void updateUserInfo(UserDTO userDTO) throws SQLException;
	int updateUserPwd(Map<String, String> pwdInfo) throws SQLException;	
	// D
	void deleteUserInfo(String userName) throws SQLException;
}
