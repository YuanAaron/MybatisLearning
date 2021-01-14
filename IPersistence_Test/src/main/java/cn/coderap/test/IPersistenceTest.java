package cn.coderap.test;

import cn.coderap.io.Resources;
import cn.coderap.pojo.User;
import cn.coderap.sqlSession.SqlSession;
import cn.coderap.sqlSession.SqlSessionFactory;
import cn.coderap.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

/**
 * Created by yw
 * 2021/1/11
 */
public class IPersistenceTest {

    @Test
    public void test() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User();
        user.setId(1);
        user.setName("%san");
        user.setAge(23);
//        user = (User)sqlSession.selectOne("user.selectOne", user);
//        System.out.println(user);
        List<User> users = sqlSession.selectList("user.selectList");
        System.out.println(users);
    }
}
