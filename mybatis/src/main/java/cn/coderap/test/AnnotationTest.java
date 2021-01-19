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
import java.util.List;

/**
 * Created by yw
 * 2021/1/19
 */
public class AnnotationTest {

    private IPersonMapper mapper;

    @Before
    public void before() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream); //用到了建造者模式
        SqlSession sqlSession = sqlSessionFactory.openSession(true); //用到了工厂模式
        mapper = sqlSession.getMapper(IPersonMapper.class);
    }

    @Test
    public void test1() {
        Person person = new Person();
        person.setUserName("ceshi1");
        person.setAge(100);
        mapper.addPerson(person);
    }

    @Test
    public void test2() {
        Person person = new Person();
        person.setUserName("ceshi1");
        person.setAge(99);
        mapper.updatePerson(person);
    }

    @Test
    public void test3() {
        List<Person> personList = mapper.selectPerson();
        System.out.println(personList);
    }

    @Test
    public void test4() {
        mapper.deletePerson(6);
    }

}