package cn.coderap.sqlSession;

import cn.coderap.pojo.Configuration;
import cn.coderap.pojo.MappedStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by yw
 * 2021/1/11
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object... params) throws Exception {

        //调用SimpleExecutor中的query方法，以执行JDBC代码
        Executor executor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<Object> list = executor.query(configuration, mappedStatement, params);
        return (List<E>)list;
    }

    @Override
    public <T> T selectOne(String statementId, Object... params) throws Exception {
        List<Object> objects = selectList(statementId, params);
        if (objects.size() == 1) {
            return (T)objects.get(0);
        } else {
            throw new RuntimeException("查询结果为空或返回结果过多");
        }
    }
}
