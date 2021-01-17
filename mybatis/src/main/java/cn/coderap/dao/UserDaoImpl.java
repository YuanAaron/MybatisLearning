package cn.coderap.dao;

import cn.coderap.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UserDaoImpl implements IUserDao{

    @Override
    public List<User> findAll() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream); //用到了建造者模式
        SqlSession sqlSession = sqlSessionFactory.openSession(); //用到了工厂模式
        List<User> objects = sqlSession.selectList("user.findAll");
        sqlSession.close();
        return objects;
    }
}
