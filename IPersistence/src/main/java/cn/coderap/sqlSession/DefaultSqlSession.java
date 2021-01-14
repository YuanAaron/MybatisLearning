package cn.coderap.sqlSession;

import cn.coderap.pojo.Configuration;
import cn.coderap.pojo.MappedStatement;

import java.lang.reflect.*;
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

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        //使用JDK动态代理为Dao接口生成代理对象(我感觉这句话有问题)
        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            /**
             * 以下面两个为例：
             * List<User> all = userDao.findAll();
             * User user = userDao.findByCondition(user);
             * @param proxy 当前代理对象的引用 userDao的引用
             * @param method 当前被调用方法的引用 findAll()方法的引用
             * @param args 传递的参数 user参数
             * @return
             * @throws Throwable
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //不管如何封装，底层都是去执行JDBC代码，因为JDBC封装在simpleExecutor.query中，因此invoke执行过程中还是要调用该方法
                //但在这里不直接调用query方法，而是根据不同情况，来调用selectList或者selectOne，然后再由这两个方法去调用query方法，以执行JDBC

                //1、准备selectList和selectOne的参数 statementId和params
                //statementId：namespace.id，但在该方法中无法拿到配置文件中的namespace和id(但是在invoke中借助method可以获取到当前的方法名及其所在类的全限定名)，
                //因此在使用getMapper时，namespace和id的值就不能随便写，而应该符合一定的规范，即namespace为传入接口的全限定名(cn.coderap.dao.IUserDao)，
                //id为接口中的方法名(findAll)
                String name = method.getName(); //findAll
                String className = method.getDeclaringClass().getName(); //cn.coderap.dao.IUserDao
                String statementId = className + "." + name;

                //2、调用selectList或selectOne
                //判断被调用方法method的返回值是集合还是实体
                Type genericReturnType = method.getGenericReturnType(); //被调用方法的返回值类型
                // 判断是否进行了泛型类型参数化，即判断返回值类型是否有泛型（简单判断，能达到目的）
                if (genericReturnType instanceof ParameterizedType) {
                    List<Object> objects = selectList(statementId, args);
                    return objects;
                }
                return selectOne(statementId, args);
            }
        });
        return (T)proxyInstance;
    }
}
