package cn.coderap.dao;

import cn.coderap.pojo.User;

import java.io.IOException;
import java.util.List;

public interface IUserDao {

    public List<User> findAll() throws IOException;
}
