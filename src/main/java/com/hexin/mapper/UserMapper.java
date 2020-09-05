package com.hexin.mapper;

import com.hexin.entity.Role;
import com.hexin.entity.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    
    List<User> findAll();

    Integer insertUser(User user);

    User findUserById(Integer id);

    Integer delUserById(Integer id);

    Integer updateUserById(User user);

    User login(String user);

    List<User> findAllByName(User user);

    List<Map<String, String>> userInfo();

    void insertList(List<Role> list);

    List<Object> selectByPage();

    List<Role> findRole();
}
