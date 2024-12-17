package cn.lm.mybatis.mapper.helper;

import cn.lm.mybatis.mapper.mapper.CountryMultipleMapper;
import cn.lm.mybatis.mapper.mapper.MybatisHelper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import cn.lm.mybatis.mapper.model.Country;

import java.util.List;

/**
 * @author yuanhao
 */
public class MultipleMapperProviderTest {
    @Test
    public void test() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CountryMultipleMapper mapper = sqlSession.getMapper(CountryMultipleMapper.class);
            Country country = new Country();
            country.setId(200);
            country.setCountrycode("AB");
            mapper.insert(country);
            List<Country> countryList = mapper.select(country);
            Assert.assertEquals("AB", countryList.get(0).getCountrycode());
        } finally {
            sqlSession.close();
        }
    }
}
