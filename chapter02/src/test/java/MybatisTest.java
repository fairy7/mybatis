import com.study.chapter02.bean.Department;
import com.study.chapter02.bean.Employee;
import com.study.chapter02.dao.DepartmentMapper;
import com.study.chapter02.dao.EmployeeMapper;
import com.study.chapter02.dao.EmployeeMapperAnnotation;
import com.study.chapter02.dao.EmployeeMapperPlus;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1.接口式编程
 *      原生 ：        Dao    ==> DaoImpl
 *      mybatis ：    Dao(Mapper)  ==>  xxMapper.xml
 * 2.sqlSession代表和数据库的一次会话，用完必须关闭
 * 3.sqlSession和connection一样，都是非线程安全的。每次使用都应该去获取新的对象。
 * 4.mapper接口没有实现类，但是mybatis会为这个接口生成一个代理对象。
 *      （将接口和xml进行绑定）
 * 5.有两个重要的配置文件：
 *      mybatis的全区配置文件：包含数据库连接池信息，事务管理器信息。。。系统运行环境信息
 *      sql映射文件：保存了每一个sql语句的映射信息。
 */
public class MybatisTest {

    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }
    /**
     * 1.根据mybatis-config.xml（全局）配置文件创建一个SqlSessionFactory对象
     * 2.sql映射文件；配置了每一个sql，以及sql的封装规则等。
     * 3.将sql映射问及那注册在全局配置文件中
     * 4.写代码：
     *      1）根据全配置文件得到sqlSessionFactory;
     *      2）使用sqlSessionFactory得到sqlSession对象进行增删改查
     *          一个sqlSession就代表和数据库的一次会话，用完关闭
     * */
    @Test
    public void test() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //2.获取sqlSession实例，能直接执行已经映射的sql语句
        SqlSession openSession = sqlSessionFactory.openSession();
        try {
            Employee employee = openSession.selectOne("com.study.chapter02.bean.Employee.selectEmp", 1);
            System.out.println(employee.toString());
        } finally {
            openSession.close();
        }

    }

    @Test
    public void test01() throws IOException {
        //1.获取sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //2.获取sqlSession对象
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            //3.获取接口的实现类对象
            //会为接口自动创建一个代理对象，代理对象去执行增删改查
            EmployeeMapper employeeMapper = openSession.getMapper(EmployeeMapper.class);
            Employee employee = employeeMapper.getEmpById(1);
            System.out.println(employeeMapper.getClass());
            System.out.println(employee.toString());
        } finally {
            openSession.close();
        }

    }

    @Test
    public void test02() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            EmployeeMapperAnnotation mapper = openSession.getMapper(EmployeeMapperAnnotation.class);
            Employee employee = mapper.getEmpById(1);
            System.out.println(mapper.getClass());
            System.out.println(employee.toString());
        } finally {
            openSession.close();
        }
    }

    /**
     * 测试增删改
     * 1. mybatis允许增删改直接定义一下类型返回值：
     *      Integer, Long, Boolean, void
     * 2. 我们需要手动提交数据
     *      sqlSessionFactory.openSession() ===> 手动提交
     *      sqlSessionFactory.openSession(true) ===> 自动提交
     * @throws IOException
     */
    @Test
    public void test03() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //1.获取到的openSession不会自动提交数据
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);

            Employee employee = new Employee(null,"lucy", "lucy@qq.com", "0");
            mapper.addEmp(employee);
            System.out.println(employee.getId());

            //System.out.println(mapper.updateEmp(new Employee(3, "jerry2", "123@11", "0")));

            //2.手动提交数据
            openSession.commit();
        } finally {
            openSession.close();
        }
    }

    @Test
    public void test04() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //1.获取到的openSession不会自动提交数据
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);

            List<Employee> emps = mapper.getEmpsByLastNameLike("jerry%");
            System.out.println(emps);

            Map<String, Object> map = mapper.getEmpByIdReturnMap(1);
            System.out.println(map);

            Map<Integer, Employee> map2 = mapper.getEmpByLastNameLikeReturnMap("jerry%");
            System.out.println(map2);

            //2.手动提交数据
            openSession.commit();
        } finally {
            openSession.close();
        }
    }

    @Test
    public void test05() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //1.获取到的openSession不会自动提交数据
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            EmployeeMapperPlus mapper = openSession.getMapper(EmployeeMapperPlus.class);

            /*Employee emp = mapper.getEmpById(1);
            System.out.println(emp);

            Employee employee = mapper.getEmpAndDept(1);
            System.out.println(employee);*/

            Employee employee1 = mapper.getEmpByIdStep(1);
            System.out.println(employee1);
            //2.手动提交数据
            openSession.commit();
        } finally {
            openSession.close();
        }
    }

    @Test
    public void test06() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //1.获取到的openSession不会自动提交数据
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            DepartmentMapper mapper = openSession.getMapper(DepartmentMapper.class);
/*
            Department dept = mapper.getDeptByIdPlus(1);
            System.out.println(dept);
            System.out.println(dept.getEmps());*/
            Department dept = mapper.getDeptByIdStep(1);
            System.out.println(dept.getDepartmentName());
            System.out.println(dept.getEmps());
            //2.手动提交数据
            openSession.commit();
        } finally {
            openSession.close();
        }
    }
}
