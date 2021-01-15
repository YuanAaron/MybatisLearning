package cn.coderap.test;

import cn.coderap.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by yw
 * 2021/1/15
 */
public class MybatisTest {

    @Test
    public void test1() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream); //用到了建造者模式
        SqlSession sqlSession = sqlSessionFactory.openSession(); //用到了工厂模式
        List<User> objects = sqlSession.selectList("user.findAll");
        System.out.println(objects);
        sqlSession.close();
    }

    @Test
    public void test2() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream); //用到了建造者模式
        SqlSession sqlSession = sqlSessionFactory.openSession(); //用到了工厂模式
        User user = new User();
        user.setName("wangwu");
        user.setAge(25);
        sqlSession.insert("user.saveUser",user);
        sqlSession.commit(); //增删改需要提交事务
        sqlSession.close();
    }

    @Test
    public void test3() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream); //用到了建造者模式
        SqlSession sqlSession = sqlSessionFactory.openSession(); //用到了工厂模式
        User user = new User();
        user.setName("wangwu");
        user.setAge(26);
        sqlSession.update("user.updateUser",user);
        sqlSession.commit(); //增删改需要提交事务
        sqlSession.close();
    }

    @Test
    public void test4() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream); //用到了建造者模式
        SqlSession sqlSession = sqlSessionFactory.openSession(); //用到了工厂模式
        sqlSession.delete("user.deleteUser","wangwu");
        sqlSession.commit(); //增删改需要提交事务
        sqlSession.close();
    }
}
