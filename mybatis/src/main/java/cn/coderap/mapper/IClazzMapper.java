package cn.coderap.mapper;

import cn.coderap.pojo.Clazz;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by yw
 * 2021/1/18
 */
public interface IClazzMapper {

    //一对多：查询所有用户，同时查询每个用户关联的订单信息
    public List<Clazz> findAll();

    //注意：这里必须取别名为className，否则将clazz_name字段的值封装到实体的clazzName属性中
    @Select("select id,clazz_name as clazzName from t_clazz where id = #{id}")
    public Clazz findUserById(Integer id);
}
