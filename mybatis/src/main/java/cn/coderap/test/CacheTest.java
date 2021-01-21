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

    @Before
    public void before() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream); //用到了建造者模式
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
}
