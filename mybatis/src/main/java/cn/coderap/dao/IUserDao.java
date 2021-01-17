package cn.coderap.dao;

import cn.coderap.pojo.User;

import java.io.IOException;
import java.util.List;

public interface IUserDao {

    public List<User> findAll() throws IOException;

    //多条件组合查询：演示if标签
    public List<User> findByCondition(User user);

    //多值查询：演示foreach标签
    //public List<User> findByIds(int[] ids);
    public List<User> findByIds(List<Integer> ids);
}
