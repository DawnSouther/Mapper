/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 the original author or authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package cn.lm.mybatis.mapper.weekend;

import cn.lm.mybatis.mapper.weekend.entity.Country;
import cn.lm.mybatis.mapper.weekend.mapper.CountryMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import cn.lm.mybatis.mapper.entity.Condition;
import cn.lm.mybatis.mapper.util.Sqls;

import java.util.List;

import static cn.lm.mybatis.mapper.weekend.WeekendSqlsUtils.andLike;

/**
 * @author linweichao
 * @date 2019/5/20
 */
public class WeekendSqlsUtilsTest {

    @Test
    public void testWeekend() {
        try (SqlSession sqlSession = MybatisHelper.getSqlSession()) {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            List<Country> selectByWeekendSql = mapper.selectByCondition(new Condition.Builder<>(Country.class)
                    .where(andLike(Country::getCountryname, "China")).build());

            List<Country> selectByExample = mapper.selectByCondition(
                    new Condition.Builder<>(Country.class).where(Sqls.custom().andLike("countryname", "China")).build());

            //判断两个结果数组内容是否相同
            Assert.assertArrayEquals(selectByExample.toArray(), selectByWeekendSql.toArray());
        }
    }

    @Test
    public void testWeekendComplex() {
        try (SqlSession sqlSession = MybatisHelper.getSqlSession()) {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);

            List<Country> selectByWeekendSql = mapper.selectByCondition(new Condition.Builder<>(Country.class)
                    .where(andLike(Country::getCountryname, "%a%")
                            .andGreaterThan(Country::getCountrycode, "123"))
                    .build());


            List<Country> selectByExample = mapper.selectByCondition(new Condition.Builder<>(Country.class)
                    .where(Sqls.custom().andLike("countryname", "%a%").andGreaterThan("countrycode", "123")).build());

            // 判断两个结果数组内容是否相同
            Assert.assertArrayEquals(selectByExample.toArray(), selectByWeekendSql.toArray());
        }
    }

}
