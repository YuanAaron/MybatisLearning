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
}
