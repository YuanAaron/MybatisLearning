package cn.coderap.mapper;

import cn.coderap.pojo.Person;
import cn.coderap.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by yw
 * 2021/1/19
 */
public interface IPersonMapper {

    //查询所有用户、同时查询每个用户关联的角色信息
    public List<Person> findAllPersonAndRole();

    //添加
    @Insert("insert into person (user_name,age) values (#{userName},#{age})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    public void addPerson(Person person);

    //更新
    @Update("update person set age = #{age} where user_name = #{userName}")
    public void updatePerson(Person person);

    //查询 注意：这里必须取别名为userName，否则数据库中的字段无法封装到实体的对应属性上
    @Select("select id, age, user_name as userName from person")
    public List<Person> selectPerson();

    //删除
    @Delete("delete from person where id = #{id}")
    public void deletePerson(Integer id);

    //多对多的注解方式：xml中的一个sql分成了两个sql
    @Select("select * from person")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "user_name",property = "userName"),
            @Result(column = "age",property = "age"),
            @Result(column = "id",property = "roleList",javaType = List.class,many = @Many(
                select = "cn.coderap.mapper.IRoleMapper.findRoleByPersonId"
            ))
    })
    public List<Person> findAllPersonAndRole1();
}
