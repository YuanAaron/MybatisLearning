package cn.coderap.sqlSession;

import cn.coderap.config.BoundSql;
import cn.coderap.pojo.Configuration;
import cn.coderap.pojo.MappedStatement;
import cn.coderap.utils.GenericTokenParser;
import cn.coderap.utils.ParameterMapping;
import cn.coderap.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yw
 * 2021/1/11
 */
public class SimpleExecutor implements Executor{

    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception {
        //1、注册驱动、获取连接
        Connection connection = configuration.getDataSource().getConnection();
        //2、获取sql语句 select * from `user` where name like #{name} and age > #{age}，之所以写成这样是为了实现User实体类中的属性值赋给对应的字段
        String sql = mappedStatement.getSql();
        //3、转换sql语句 select * from `user` where name like ？ and age > ？
        BoundSql boundSql = getBoundSql(sql);
        //4、获取预处理对象PrepareStatement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        //5、设置参数
          //获取参数的全路径,即cn.coderap.pojo.User
          String parameterType = mappedStatement.getParameterType();
          //获取Class对象
          Class<?> parameterTypeClass = getClassType(parameterType);

        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            //#{}里面的参数名称
            String content = parameterMappingList.get(i).getContent();

            //根据反射获取属性
            Field declaredField = parameterTypeClass.getDeclaredField(content);
            declaredField.setAccessible(true); //防止私有，暴力访问
            //返回user对象中declaredField属性的值
            Object o = declaredField.get(params[0]); //传入user对象

            preparedStatement.setObject(i+1, o);

        }
        //6、执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        //7、封装返回结果集
        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = getClassType(resultType);

        List<Object> objects = new ArrayList<>();
        while (resultSet.next()) {
            Object o = resultTypeClass.newInstance();

            //获取元数据 里面包含返回的某条记录的列名及其值
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <=metaData.getColumnCount(); i++) {
                //获取字段名
                String columnName = metaData.getColumnName(i);
                //获取字段的值
                Object value = resultSet.getObject(columnName);

                //通过反射或内省，根据数据库表和实体的对应关系完成封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o,value);
            }
            objects.add(o);
        }
        return (List<E>)objects;
    }

    private Class<?> getClassType(String parameterType) throws ClassNotFoundException {
        if (parameterType != null) {
            return Class.forName(parameterType);
        }
        return null;
    }

    /**
     * 1、将#{}替换为？
     * 2、解析出#{}里面的参数名称进行存储
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {
        //标记处理类：配合标记解析器来完成对占位符的解析处理工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        //标记解析器
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        //解析出来的sql
        String parseSql = genericTokenParser.parse(sql);
        //#{}里面的参数名称
        List<ParameterMapping> parameterMappingList = parameterMappingTokenHandler.getParameterMappings();

        BoundSql boundSql = new BoundSql(parseSql,parameterMappingList);
        return boundSql;
    }
}
