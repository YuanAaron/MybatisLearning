package cn.coderap.test;

import cn.coderap.dao.IUserDao;
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
//        List<User> users = sqlSession.selectList("user.selectList");
//        System.out.println(users);

        IUserDao userDao = (IUserDao) sqlSession.getMapper(IUserDao.class); //userDao即为IUserDao接口的代理实现类对象
        List<User> users= userDao.findAll(); //代理对象去调用IUserDao接口中的任何方法（findAll、findByCondition），都会去执行invoke方法
        System.out.println(users);
        user = userDao.findByCondition(user);
        System.out.println(user);
    }
}
