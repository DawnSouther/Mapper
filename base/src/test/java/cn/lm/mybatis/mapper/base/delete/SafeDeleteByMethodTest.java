package cn.lm.mybatis.mapper.base.delete;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import cn.lm.mybatis.mapper.base.BaseTest;
import cn.lm.mybatis.mapper.base.Country;
import cn.lm.mybatis.mapper.base.CountryMapper;
import cn.lm.mybatis.mapper.entity.Config;
import cn.lm.mybatis.mapper.entity.Condition;

public class SafeDeleteByMethodTest extends BaseTest {

    @Override
    protected Config getConfig() {
        Config config = super.getConfig();
        config.setSafeDelete(true);
        //和 SafeDeleteByFieldTest 测试的区别在此，这里将会使后面调用 EntityField.getValue 时，使用 getter 方法获取值
        config.setEnableMethodAnnotation(true);
        return config;
    }

    @Test(expected = PersistenceException.class)
    public void testSafeDelete() {
        SqlSession sqlSession = getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            mapper.delete(new Country());
        } finally {
            sqlSession.close();
        }
    }

    @Test(expected = PersistenceException.class)
    public void testSafeDeleteNull() {
        SqlSession sqlSession = getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            mapper.delete(null);
        } finally {
            sqlSession.close();
        }
    }


    @Test(expected = PersistenceException.class)
    public void testSafeDeleteByExample() {
        SqlSession sqlSession = getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            mapper.deleteByCondition(new Condition(Country.class));
        } finally {
            sqlSession.close();
        }
    }

    @Test(expected = PersistenceException.class)
    public void testSafeDeleteByExampleNull() {
        SqlSession sqlSession = getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            mapper.deleteByCondition(null);
        } finally {
            sqlSession.close();
        }
    }

}
