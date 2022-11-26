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
            sqlSession.insert("insertClass");
            sqlSession.insert("insertMessage");
            sqlSession.insert("insertUser");
            sqlSession.insert("insertMessageRecord");
            sqlSession.insert("insertVideo");
            sqlSession.insert("insertanniversary_schedule");
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