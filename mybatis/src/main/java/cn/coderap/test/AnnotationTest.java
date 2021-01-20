package cn.coderap.test;

import cn.coderap.mapper.IClazzMapper;
import cn.coderap.mapper.IPersonMapper;
import cn.coderap.mapper.IStuMapper;
import cn.coderap.pojo.Clazz;
import cn.coderap.pojo.Person;
import cn.coderap.pojo.Stu;
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
    //private IStuMapper mapper;
    //private IClazzMapper mapper;

    @Before
    public void before() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream); //用到了建造者模式
        SqlSession sqlSession = sqlSessionFactory.openSession(true); //用到了工厂模式
        mapper = sqlSession.getMapper(IPersonMapper.class);
        //mapper = sqlSession.getMapper(IStuMapper.class);
        //mapper = sqlSession.getMapper(IClazzMapper.class);
    }

//    @Test
//    public void test1() {
//        Person person = new Person();
//        person.setUserName("ceshi1");
//        person.setAge(100);
//        mapper.addPerson(person);
//    }
//
//    @Test
//    public void test2() {
//        Person person = new Person();
//        person.setUserName("ceshi1");
//        person.setAge(99);
//        mapper.updatePerson(person);
//    }
//
//    @Test
//    public void test3() {
//        List<Person> personList = mapper.selectPerson();
//        System.out.println(personList);
//    }
//
//    @Test
//    public void test4() {
//        mapper.deletePerson(6);
//    }

//    @Test
//    public void test5() {
//        List<Stu> stuList = mapper.findStuAndClazz1();
//        for (Stu stu : stuList) {
//            System.out.println(stu);
//        }
//    }

//    @Test
//    public void test6() {
//        List<Clazz> clazzList = mapper.findAll1();
//        for (Clazz clazz : clazzList) {
//            System.out.println(clazz);
//        }
//    }

    @Test
    public void test7() {
        List<Person> personList = mapper.findAllPersonAndRole1();
        for (Person person : personList) {
            System.out.println(person);
        }
    }

}