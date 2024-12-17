package cn.lm.mybatis.mapper.base.update;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import cn.lm.mybatis.mapper.base.BaseTest;
import cn.lm.mybatis.mapper.base.Country;
import cn.lm.mybatis.mapper.base.CountryMapper;
import cn.lm.mybatis.mapper.entity.Config;
import cn.lm.mybatis.mapper.entity.Condition;

public class SafeUpdateByFieldTest extends BaseTest {

    @Override
    protected Config getConfig() {
        Config config = super.getConfig();
        config.setSafeUpdate(true);
        return config;
    }

    @Test(expected = PersistenceException.class)
    public void testSafeUpdate() {
        SqlSession sqlSession = getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            mapper.updateByCondition(new Country(), new Condition(Country.class));
        } finally {
            sqlSession.close();
        }
    }

    @Test(expected = PersistenceException.class)
    public void testSafeUpdateNull() {
        SqlSession sqlSession = getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            mapper.updateByCondition(new Country(), null);
        } finally {
            sqlSession.close();
        }
    }

    @Test(expected = PersistenceException.class)
    public void testSafeUpdateNull2() {
        SqlSession sqlSession = getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            mapper.updateByCondition(null, null);
        } finally {
            sqlSession.close();
        }
    }

    @Test(expected = PersistenceException.class)
    public void testSafeUpdateByExample() {
        SqlSession sqlSession = getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            mapper.updateByConditionSelective(new Country(), new Condition(Country.class));
        } finally {
            sqlSession.close();
        }
    }

    @Test(expected = PersistenceException.class)
    public void testSafeUpdateByExampleNull() {
        SqlSession sqlSession = getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            mapper.updateByConditionSelective(new Country(), null);
        } finally {
            sqlSession.close();
        }
    }

}
