package cn.coderap.dao;

import cn.coderap.io.Resources;
import cn.coderap.pojo.User;
import cn.coderap.sqlSession.SqlSession;
import cn.coderap.sqlSession.SqlSessionFactory;
import cn.coderap.sqlSession.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

/**
 * Created by yw
 * 2021/1/14
 */
public class UserDaoImpl implements IUserDao{
    @Override
    public List<User> findAll() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        List<User> users = sqlSession.selectList("user.selectList");
        return users;
    }

    @Override
    public User findByCondition(User user) throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        user = (User)sqlSession.selectOne("user.selectOne", user);
        return user;
    }
}
