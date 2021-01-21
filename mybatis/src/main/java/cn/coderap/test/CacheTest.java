package cn.coderap.test;

import cn.coderap.mapper.IPersonMapper;
import cn.coderap.pojo.Person;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yw
 * 2021/1/20
 */
public class CacheTest {

    private IPersonMapper mapper;

    private SqlSession sqlSession;

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void before() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream); //用到了建造者模式
        sqlSession = sqlSessionFactory.openSession(); //用到了工厂模式
        mapper = sqlSession.getMapper(IPersonMapper.class);
    }

    /**
     * 验证一级缓存存在
     */
    @Test
    public void test1() {
        //第一次查询id为1的人
        Person person1 = mapper.findPersonById(1);
        //sqlSession.clearCache(); //起作用
        //第二次查询id为1的人
        Person person2 = mapper.findPersonById(1);
        System.out.println(person1 == person2);
    }

    /**
     * 验证sql.commit()会清空一级缓存
     */
    @Test
    public void test2() {
        //第一次查询id为1的人
        Person person1 = mapper.findPersonById(1);
        //更新操作
        Person person = new Person();
        person.setUserName("zhangsan");
        person.setAge(30);
        mapper.updatePerson(person);
        sqlSession.commit();

        //第二次查询id为1的人
        Person person2 = mapper.findPersonById(1);
        System.out.println(person1 == person2);
    }

    /**
     * 验证二级缓存与sqlSession无关，而是基于mapper的
     */
    @Test
    public void test3() {
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();

        IPersonMapper mapper1 = sqlSession1.getMapper(IPersonMapper.class);
        IPersonMapper mapper2 = sqlSession2.getMapper(IPersonMapper.class);

        Person person1 = mapper1.findPersonById(1);
        //SqlSession的修改、添加、删除、commit()、close()以及clearCache都会清空一级缓存,
        //但奇怪的是使用clearCache后，mapper2.findPersonById(1)会重新查询数据库，而使用其他时不会
        //sqlSession1.clearCache();
        sqlSession1.close();
        Person person2 = mapper2.findPersonById(1);
        //尽管走了二级缓存，但是仍返回false，相关的说法是二级缓存缓存的是数据而非对象，即将拿到的二级缓存数据封装成了一个新对象，因此false
        System.out.println(person1==person2);
        sqlSession2.close();
    }

    /**
     * 验证commit操作可以清空二级缓存数据
     */
    @Test
    public void test4() {
        SqlSession sqlSession1 = sqlSessionFactory.openSession();
        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        SqlSession sqlSession3 = sqlSessionFactory.openSession();

        IPersonMapper mapper1 = sqlSession1.getMapper(IPersonMapper.class);
        IPersonMapper mapper2 = sqlSession2.getMapper(IPersonMapper.class);
        IPersonMapper mapper3 = sqlSession3.getMapper(IPersonMapper.class);

        Person person1 = mapper1.findPersonById(1);
        sqlSession1.close(); //清除一级缓存

        person1.setUserName("zhangsan");
        person1.setAge(35);
        mapper3.updatePerson(person1);
        sqlSession3.commit(); //更新后commit()

        Person person2 = mapper2.findPersonById(1);
        System.out.println(person1==person2);
        sqlSession2.close();
    }
}
