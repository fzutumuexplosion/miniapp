import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.InputStream;
/**
 * MyBatis⼊⻔程序
 * @author ⽼杜
 * @since 1.0
 * @version 1.0
 */
public class MyBatisIntroductionTest {
    public static void main(String[] args) {
        // 1. 创建SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSession
        FactoryBuilder();
        // 2. 创建SqlSessionFactory对象
        InputStream is = Thread.currentThread().getContextClassLoader().ge
        tResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.bui
        ld(is);
        // 3. 创建SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        // 4. 执⾏sql
        int count = sqlSession.insert("insertCar"); // 这个"insertCar"必须是
        sql的id
        System.out.println("插⼊⼏条数据：" + count);
        // 5. 提交（mybatis默认采⽤的事务管理器是JDBC，默认是不提交的，需要⼿动交。）
        sqlSession.commit();
        // 6. 关闭资源（只关闭是不会提交的）
        sqlSession.close();
    }
}