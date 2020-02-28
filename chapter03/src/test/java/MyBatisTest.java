import com.study.bean.Employee;
import com.study.chapter03.dao.EmployeeMapperDynamicSQL;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyBatisTest {


    public SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void test01() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        //1.获取到的openSession不会自动提交数据
        SqlSession openSession = sqlSessionFactory.openSession();

        try {
            EmployeeMapperDynamicSQL mapper = openSession.getMapper(EmployeeMapperDynamicSQL.class);
            List<Employee> emps = mapper.getEmpsByConditionIf(new Employee(null, "%e%", "", "1"));
            System.out.println(emps);

        } finally {
            openSession.close();
        }
    }

}
