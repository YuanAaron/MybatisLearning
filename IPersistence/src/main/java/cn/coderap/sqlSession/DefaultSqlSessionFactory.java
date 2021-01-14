package cn.coderap.sqlSession;

import cn.coderap.pojo.Configuration;

/**
 * Created by yw
 * 2021/1/11
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory{
    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
