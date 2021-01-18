package cn.coderap.pojo;

import lombok.Data;
import lombok.ToString;

/**
 * 学生
 * Created by yw
 * 2021/1/18
 */
@Data
@ToString
public class Stu {
    private Integer id;
    private String stuName;

    //一对一：表明该学生属于哪个班级
    private Clazz clazz;
}
