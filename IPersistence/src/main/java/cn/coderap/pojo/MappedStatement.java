package cn.coderap.pojo;

import lombok.Data;

/**
 * Created by yw
 * 2021/1/11
 */
@Data
public class MappedStatement {

    //id标识
    private String id;
    //返回值类型
    private String resultType;
    //参数类型
    private String parameterType;
    //sql语句
    private String sql;
}
