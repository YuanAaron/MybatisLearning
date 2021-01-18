package cn.coderap.test;

import cn.coderap.dao.IUserDao;
import cn.coderap.mapper.IStuMapper;
import cn.coderap.pojo.Stu;
import cn.coderap.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
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
        //1、Resources工具类，配置文件的加载，把配置文件加载成字节输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        //2、解析了配置文件，并创建了sqlSessionFactory工厂
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream); //用到了建造者模式
        //3、生产sqlSession(sqlSession中封装了selectList、selectOne等方法)
        //openSession中默认开启一个事务，但该事务不会自动提交，所以在进行增删改操作时要手动提交事务; 加上true就会自动提交了
        SqlSession sqlSession = sqlSessionFactory.openSession(true); //用到了工厂模式
        //4、调用sqlSession中的方法，执行JDBC操作
        sqlSession.delete("user.deleteUser","wangwu");
//        sqlSession.commit(); //增删改需要提交事务
        sqlSession.close();
    }

    @Test
    public void test5() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream); //用到了建造者模式
        SqlSession sqlSession = sqlSessionFactory.openSession(); //用到了工厂模式
        IUserDao mapper = sqlSession.getMapper(IUserDao.class);
        List<User> all = mapper.findAll();
        System.out.println(all);
    }

    @Test
    public void test6() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream); //用到了建造者模式
        SqlSession sqlSession = sqlSessionFactory.openSession(); //用到了工厂模式
        IUserDao mapper = sqlSession.getMapper(IUserDao.class);

        User user = new User();
        user.setName("%san");
        user.setAge(23);
        List<User> all = mapper.findByCondition(user);
        System.out.println(all);
    }

    @Test
    public void test7() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream); //用到了建造者模式
        SqlSession sqlSession = sqlSessionFactory.openSession(); //用到了工厂模式
        IUserDao mapper = sqlSession.getMapper(IUserDao.class);

        Integer[] ids = {1,2};
        //List<User> all = mapper.findByIds(ids);
        List<User> all = mapper.findByIds(Arrays.asList(ids));
        System.out.println(all);
    }

    @Test
    public void test8() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream); //用到了建造者模式
        SqlSession sqlSession = sqlSessionFactory.openSession(); //用到了工厂模式
        IStuMapper mapper = sqlSession.getMapper(IStuMapper.class);
        List<Stu> stuList = mapper.findStuAndClazz();
        for (Stu stu : stuList) {
            System.out.println(stu);
        }

    }
}
