package cn.coderap.sqlSession;

import cn.coderap.pojo.Configuration;
import cn.coderap.pojo.MappedStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by yw
 * 2021/1/11
 */
public interface Executor {

    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, Exception;
}
