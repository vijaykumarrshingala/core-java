package mybatis;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import mybatis.domain.Student;
import mybatis.mapper.StudentMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ExampleApplication {
    private static SqlSessionFactory sessionFactory;

    public static void main(String[] args) throws IOException {

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

        //Reader reader = Resources.getResourceAsReader("mybatis/mybatis-config.xml");
        //SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        SqlSession session = sessionFactory.openSession();

        Student s = new Student();

        //1st WAY
        List<Student> student = session.selectList("mybatis.mapper.StudentMapper.getAll");
        System.out.println(student.get(0).getBranch());



        //2nd WAY

        //With Mapper class usage if we add mapper in configuration  i.e configuration.addMappers
        try {
            StudentMapper categoryMapper = session.getMapper(StudentMapper.class);
            List<Student> student2 = categoryMapper.getAll();
            System.out.println("record inserted successfully : "+student2.get(0).getBranch());
            session.commit();
        } finally {
            session.close();
        }



       /* try {
            setupDataSource();
            initializeSqlSessionFactory();

            // Now you can use the SqlSessionFactory to create SqlSessions and execute SQL queries
            // For example:
            try (SqlSession session = sessionFactory.openSession()) {
                MyObject result = session.selectOne("selectMyObject", parameterObject);
                System.out.println(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    private static void setupDataSource() {
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
    }

    private static void initializeSqlSessionFactory() throws IOException {
        try (InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml")) {
            sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }
    }

    private static SqlSessionFactory createSqlSessionFactory(HikariDataSource dataSource) {
        TransactionFactory transactionFactory = new JdbcTransactionFactory();

        org.apache.ibatis.mapping.Environment environment =
                new org.apache.ibatis.mapping.Environment("development", transactionFactory, dataSource);

        org.apache.ibatis.session.Configuration configuration =
                new org.apache.ibatis.session.Configuration(environment);

        // Load the iBATIS mapping XML file

        configuration.addMappers("mybatis.mapper"); // Add your mapper interfaces here if using mappers

        return new SqlSessionFactoryBuilder().build(configuration);
    }


}



