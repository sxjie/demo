package com.hexin.service;

import com.hexin.entity.Role;
import com.hexin.entity.User;
import com.hexin.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public Integer insertUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public User findUserById(Integer id) {
        return userMapper.findUserById(id);
    }

    @Override
    public Integer delUserById(Integer id) {
        return userMapper.delUserById(id);
    }

    @Override
    public Integer updateUserById(User user) {
        return userMapper.updateUserById(user);
    }

    @Override
    public List<User> findAllByName(User user) {
        return userMapper.findAllByName(user);
    }

    @Override
    public List<Map<String, String>> userInfo() {
        return userMapper.userInfo();
    }

    @Override
    public void insertList(List<Role> list) {
        userMapper.insertList(list);
    }

    @Override
    public List<Object> selectByPage() {
        return userMapper.selectByPage();
    }

    @Override
    public String login(User user) {
        // 通过用户名获取用户
        User dbUser = userMapper.login(user.getUsername());
        if (dbUser == null){
            return "该用户不存在";
        }else if (!dbUser.getPassword().equals(user.getPassword())){
            return "密码错误";
        }else {
            user.setId(dbUser.getId());
            user.setCreateTime(dbUser.getCreateTime());
            return "登陆成功";
        }
    }
//    //用户注册逻辑
//    public String  register(User user) {
//        //判断用户是否存在
//        if (userDao.getOneUser(user.getUsername()) == null) {
//            userDao.setOneUser(user);
//            return "注册成功";
//        }
//        else {
//            return "该用户名已被使用";
//        }
//    }
//    //用户登陆逻辑
//    public String login(User user) {
//        //通过用户名获取用户
//        User dbUser = userDao.getOneUser(user.getUsername());
//        //若获取失败
//        if (dbUser == null) {
//            return "该用户不存在";
//        }
//        //获取成功后，将获取用户的密码和传入密码对比
//        else if (!dbUser.getPassword().equals(user.getPassword())){
//            return "密码错误";
//        }
//        else {
//            //若密码也相同则登陆成功
//            //让传入用户的属性和数据库保持一致
//            user.setId(dbUser.getId());
//            user.setCreateTime(dbUser.getCreateTime());
//            return "登陆成功";
//        }
//    }
}
