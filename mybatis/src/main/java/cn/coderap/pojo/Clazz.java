package cn.coderap.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 班级
 * Created by yw
 * 2021/1/18
 */
@Data
@ToString
public class Clazz {
    private Integer id;
    private String clazzName;

    //一对多：表示班级关联的学生
    private List<Stu> stuList = new ArrayList<>();
}
