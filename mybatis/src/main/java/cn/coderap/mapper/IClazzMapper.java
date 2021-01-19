package cn.coderap.mapper;

import cn.coderap.pojo.Clazz;

import java.util.List;

/**
 * Created by yw
 * 2021/1/18
 */
public interface IClazzMapper {

    //一对多：查询所有用户，同时查询每个用户关联的订单信息
    public List<Clazz> findAll();
}
