package cn.coderap.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yw
 * 2021/1/19
 */
@Data
@ToString
public class Person {
    private Integer id;
    private String userName;
    private int age;

    //一对多：代表当前用户具备哪些角色
    private List<Role> roleList = new ArrayList<>();
}
