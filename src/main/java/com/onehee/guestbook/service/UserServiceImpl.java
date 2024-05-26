package com.onehee.guestbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onehee.guestbook.model.UserDTO;
import com.onehee.guestbook.model.dao.UserDao;
import com.onehee.guestbook.model.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
	private UserMapper userMapper;
	private final UserDao userDao;
	
	@Autowired
	private UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public int joinUser(UserDTO userDTO) throws Exception {
		// return userDao.addUser(userDTO);
		return userMapper.addUser(userDTO);
	}

	@Override
	public List<UserDTO> readUserList(Map<String, String> map) throws Exception {
		int pgNo = 0;
		int size = 10;
		Map<String, Integer> pageMap = new HashMap<String, Integer>();
		try  {
			pgNo = Integer.parseInt(map.get("pgNo")) - 1;		
			size = Integer.parseInt(map.get("size"));					
		}catch(Exception e){			
		}
		pageMap.put("pgNo", pgNo);
		pageMap.put("size", size);		
		
		return userMapper.selectUserList(pageMap);
		// return userDao.selectUserList(map);
		
	}

	@Override
	public UserDTO getUserInfo(String userName) throws Exception {
		return userMapper.getUserInfo(userName);
		// return userDao.getUserInfo(userName);
	}
	
	@Override
	public UserDTO loginUser(UserDTO userDto) throws Exception {
		// return userDao.loginUser(userDto);
		return userMapper.loginUser(userDto);
	}
	
	@Override
	public boolean checkUserName(String userName) throws Exception {
		return userMapper.checkUserName(userName);
		// return userDao.checkUserName(userName);
	}

	@Override
	public void editUserInfo(UserDTO userDTO) throws Exception {
		userMapper.updateUserInfo(userDTO);
		// userDao.updateUserInfo(userDTO);		
	}
	
	@Override
	public int editUserPwd(UserDTO userDTO, String newPwd) throws Exception {
		Map<String, String> pwdMap = new HashMap<String, String>();
		pwdMap.put("newPwd", newPwd);
		pwdMap.put("userName", userDTO.getUserName());
		pwdMap.put("userPassword", userDTO.getUserPassword());
		return userMapper.updateUserPwd(pwdMap);		
		// return userDao.updateUserPwd(userDTO, newPwd);
	}

	@Override
	public void removeUserInfo(String userName) throws Exception {
		userMapper.deleteUserInfo(userName);
		// userDao.deleteUserInfo(userName);		
	}

}
