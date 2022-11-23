package mybatis_learning;

import org.apache.ibatis.io.Resources;
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
public class Main {
    public static void main(String[] args) throws Exception{
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int count = sqlSession.insert("insertCar"); //返回值是数据库中受影响的记录条数
        System.out.println("插入了"+count+"条数据");
        sqlSession.commit();

    }
}