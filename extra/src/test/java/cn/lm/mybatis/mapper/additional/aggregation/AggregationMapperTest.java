package cn.lm.mybatis.mapper.additional.aggregation;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import cn.lm.mybatis.mapper.additional.BaseTest;
import cn.lm.mybatis.mapper.entity.Condition;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.List;

public class AggregationMapperTest extends BaseTest {

    /**
     * 获取 mybatis 配置
     *
     * @return
     */
    protected Reader getConfigFileAsReader() throws IOException {
        URL url = getClass().getResource("mybatis-config.xml");
        return toReader(url);
    }

    ;

    /**
     * 获取初始化 sql
     *
     * @return
     */
    protected Reader getSqlFileAsReader() throws IOException {
        URL url = getClass().getResource("CreateDB.sql");
        return toReader(url);
    }

    ;

    @Test
    public void testCount() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            AggregateCondition aggregateCondition = AggregateCondition.builder().
                    aggregateBy("id").aliasName("total").aggregateType(AggregateType.COUNT).groupBy("role");
            Condition example = new Condition(User.class);
            List<User> m = mapper.selectAggregationByExample(example, aggregateCondition);
            Assert.assertEquals(2, m.size());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testAvg() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            AggregateCondition aggregateCondition = AggregateCondition.builder().
                    aggregateBy("id").aggregateType(AggregateType.AVG);
            Condition example = new Condition(User.class);
            List<User> m = mapper.selectAggregationByExample(example, aggregateCondition);
            Assert.assertEquals(1, m.size());
            Assert.assertEquals(Long.valueOf(3), m.get(0).getId());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testSum() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            AggregateCondition aggregateCondition = AggregateCondition.builder().
                    aggregateBy("id").aliasName("aggregation").aggregateType(AggregateType.SUM);
            Condition example = new Condition(User.class);
            List<User> m = mapper.selectAggregationByExample(example, aggregateCondition);
            Assert.assertEquals(1, m.size());
            Assert.assertEquals(Long.valueOf(21), m.get(0).getAggregation());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testMax() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            AggregateCondition aggregateCondition = AggregateCondition.builder().
                    aggregateBy("id").aliasName("aggregation").aggregateType(AggregateType.MAX).groupBy("role");
            Condition example = new Condition(User.class);
            example.setOrderByClause("role desc");
            List<User> m = mapper.selectAggregationByExample(example, aggregateCondition);
            Assert.assertEquals(2, m.size());
            Assert.assertEquals(Long.valueOf(6), m.get(0).getAggregation());
            Assert.assertEquals(Long.valueOf(3), m.get(1).getAggregation());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testMin() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            AggregateCondition aggregateCondition = AggregateCondition.builder().
                    aggregateBy("id").aliasName("aggregation").aggregateType(AggregateType.MIN);
            Condition example = new Condition(User.class);
            List<User> m = mapper.selectAggregationByExample(example, aggregateCondition);
            Assert.assertEquals(1, m.size());
            Assert.assertEquals(Long.valueOf(1), m.get(0).getAggregation());
        } finally {
            sqlSession.close();
        }
    }


}
