package com.onehee.guestbook.service;

import java.util.List;
import java.util.Map;

import com.onehee.guestbook.model.UserDTO;

public interface UserService {

	// C
	int joinUser(UserDTO userDTO) throws Exception;	
	// R
	List<UserDTO> readUserList(Map<String, String> map) throws Exception; // paging 추가를 위해..
	UserDTO getUserInfo(String userName) throws Exception;
	UserDTO loginUser(UserDTO userDTO) throws Exception;
	boolean checkUserName(String userName)throws Exception;
	// U
	void editUserInfo(UserDTO userDTO) throws Exception;
	int editUserPwd(UserDTO userDTO, String newPwd) throws Exception;
	// D
	void removeUserInfo(String userName) throws Exception;
}
