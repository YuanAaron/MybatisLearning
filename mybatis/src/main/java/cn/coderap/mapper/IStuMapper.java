package cn.coderap.mapper;

import cn.coderap.pojo.Stu;

import java.util.List;

/**
 * Created by yw
 * 2021/1/18
 */
public interface IStuMapper {

    //一对一：查询学生的同时还查询该学生所属的班级
    public List<Stu> findStuAndClazz();
}
