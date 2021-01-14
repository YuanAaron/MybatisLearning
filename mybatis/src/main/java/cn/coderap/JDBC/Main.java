package cn.coderap.JDBC;

import cn.coderap.pojo.User;

import java.sql.*;

/**
 * JDBC操作数据库演示用例
 * Created by yw
 * 2021/1/11
 */
public class Main {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //1
            //加载数据库驱动
            Class.forName("com.mysql.jdbc.Driver");
            //1
            //通过驱动管理类获取数据库连接
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mybatis?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false", "root", "Kexin!00");

            //2
            String sql = "select * from `user` where name like ? and age > ?"; //定义sql语句 ？表示占位符
            //获取预处理statement
            preparedStatement = connection.prepareStatement(sql);
            //2
            //设置参数 第一个参数为sql语句中参数的序号（从1开始），第二个参数为设置的参数值
            preparedStatement.setString(1,"%san");
            preparedStatement.setInt(2, 18);
            //向数据库发出sql执行查询，查询出结果集
            resultSet = preparedStatement.executeQuery();
            //3
            //遍历查询结果集
            while (resultSet.next()) {
                //2
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");

                //封装User
                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setAge(age);
                System.out.println(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放资源
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
}
