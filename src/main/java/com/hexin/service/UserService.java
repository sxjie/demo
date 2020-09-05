package com.hexin.service;

import com.hexin.entity.Role;
import com.hexin.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

	List<User> findAll();

	Integer insertUser(User user);

	User findUserById(Integer id);

	Integer delUserById(Integer id);

	Integer updateUserById(User user);

	String login(User user);

    List<User> findAllByName(User user);

	List<Map<String, String>> userInfo();

	void insertList(List<Role> list);

	List<Object> selectByPage();

}
