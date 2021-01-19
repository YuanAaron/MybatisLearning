package cn.coderap.mapper;

import cn.coderap.pojo.Clazz;
import cn.coderap.pojo.Stu;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by yw
 * 2021/1/18
 */
public interface IStuMapper {

    //一对一：查询学生的同时还查询该学生所属的班级
    public List<Stu> findStuAndClazz();

    //一对一的注解方式：xml中的一个sql分成了两个sql
    //clazz_id传给findUserById方法
    @Select("select * from t_student")
    @Results({
            @Result(id = true, column = "id",property = "id"),
            @Result(column = "stu_name",property = "stuName"),
            @Result(column = "clazz_id",property = "clazz", javaType = Clazz.class, one = @One(
                select = "cn.coderap.mapper.IClazzMapper.findClazzById"
            ))
    })
    public List<Stu> findStuAndClazz1();
}
