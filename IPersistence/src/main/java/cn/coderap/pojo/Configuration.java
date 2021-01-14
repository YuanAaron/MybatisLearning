package cn.coderap.pojo;

import lombok.Data;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yw
 * 2021/1/11
 */
@Data
public class Configuration {

    private DataSource dataSource;

    /**
     * key: statementId
     * value: 封装好的MappedStatement对象
     */
    Map<String,MappedStatement> mappedStatementMap = new HashMap<>();
}
