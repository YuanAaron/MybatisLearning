package cn.coderap.dao;

import cn.coderap.pojo.User;

import java.util.List;

public interface IUserDao {

    //查询所有用户
    public List<User> findAll() throws Exception;

    //查询单个用户
    public User findByCondition(User user) throws Exception;
}
