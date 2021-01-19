package cn.coderap.mapper;

import cn.coderap.pojo.Person;

import java.util.List;

/**
 * Created by yw
 * 2021/1/19
 */
public interface IPersonMapper {

    //查询所有用户、同时查询每个用户关联的角色信息
    public List<Person> findAllPersonAndRole();
}
