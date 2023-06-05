package com.miit.sep22.java.mybatis;

import com.miit.sep22.java.mybatis.mapper.StudentMapperV2;
import com.miit.sep22.java.mybatis.domain.Student;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ExampleApplication {
    private static SqlSessionFactory sessionFactory;

    public static void main(String[] args) throws IOException, InterruptedException, SQLException {

        //apacheDBCP();
        //hikariCP();

        hikariCP2();

    }

    private static void hikariCP2() throws IOException, InterruptedException, SQLException {

        Reader reader = Resources.getResourceAsReader("com/miit/sep22/java/mybatis/mapper/mybatis-hikari.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession();
        Thread.sleep(4000);
        HikariCPDataSourceFactory.getPoolInfo();
        List<Student> student = session.selectList("getAll");
        HikariCPDataSourceFactory.getPoolInfo();
        System.out.println(student);
        SqlSession session1 = sqlSessionFactory.openSession();
        HikariCPDataSourceFactory.getPoolInfo();
        Student s = session1.selectOne("getById", 1);
        System.out.println(s.getBranch());
    }

    private static void hikariCP() throws IOException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/mysql_demo?characterEncoding=UTF-8");
        config.setUsername("root");
        config.setPassword("admin");

        // Additional HikariCP configuration options
        config.setMinimumIdle(2);
        config.setMaximumPoolSize(10);
        // ... more config options ...

        HikariDataSource dataSource = new HikariDataSource(config);
        sessionFactory = createSqlSessionFactory(dataSource);
        SqlSession session = sessionFactory.openSession();

        //1st WAY
        List<Student> student = session.selectList("com.miit.sep22.java.mybatis.mapper.StudentMapperV2.getAll");
        System.out.println(student.get(0).getBranch());

        //2nd WAY

        //With Mapper class usage if we add mapper in configuration  i.e configuration.addMappers
        try {
            StudentMapperV2 categoryMapper = session.getMapper(StudentMapperV2.class);
            List<Student> student2 = categoryMapper.getAll();
            System.out.println("record inserted successfully : "+student2.get(0).getBranch());
            session.commit();
        } finally {
            session.close();
        }
    }

    private static void apacheDBCP() throws IOException {

        Reader reader = Resources.getResourceAsReader("mybatis/mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sqlSessionFactory.openSession();
        List<Student> student = session.selectList("getAll");
        System.out.println(student);
    }


    private static SqlSessionFactory createSqlSessionFactory(HikariDataSource dataSource) {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();

        org.apache.ibatis.mapping.Environment environment =
                new org.apache.ibatis.mapping.Environment("development", transactionFactory, dataSource);

        org.apache.ibatis.session.Configuration configuration =
                new org.apache.ibatis.session.Configuration(environment);

        // Load the iBATIS mapping XML file

        configuration.addMappers("com.miit.sep22.java.mybatis.mapper"); // Add your mapper interfaces here if using mappers

        return new SqlSessionFactoryBuilder().build(configuration);
    }


}



