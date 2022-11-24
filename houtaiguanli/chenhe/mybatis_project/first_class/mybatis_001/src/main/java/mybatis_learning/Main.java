package mybatis_learning;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        SqlSession sqlSession = null;
        try {
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
            sqlSession = sqlSessionFactory.openSession();
            // 以下开始运行sql文件
            int count = sqlSession.insert("insertClass");
            sqlSession.insert("insertMessage");
            sqlSession.insert("insertUser");
            System.out.println("插入了"+count+"条元素");
            sqlSession.commit();
        }catch (Exception e){
            if(sqlSession!=null) {
                sqlSession.rollback();
            }
            e.printStackTrace();
        }
        finally {
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
    }
}