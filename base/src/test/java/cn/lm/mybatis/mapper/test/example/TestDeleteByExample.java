/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 abel533@gmail.com
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
 */

package cn.lm.mybatis.mapper.test.example;

import cn.lm.mybatis.mapper.mapper.CountryMapper;
import cn.lm.mybatis.mapper.mapper.MybatisHelper;
import cn.lm.mybatis.mapper.model.Country;
import cn.lm.mybatis.mapper.model.Entity;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import cn.lm.mybatis.mapper.entity.Condition;

/**
 * @author liuzh
 */
public class TestDeleteByExample {

    @Test
    public void testDeleteByExample() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            Condition example = new Condition(Country.class);
            example.createCriteria().andGreaterThan("id", 100);
            int count = mapper.deleteByCondition(example);
            //查询总数
            Assert.assertEquals(83, count);
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testDeleteByExample11() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            Condition<Country> example = new Condition<>(Country.class);
            example.createCriteria().andGreaterThan(Entity::getId, 100);
            int count = mapper.deleteByCondition(example);
            //查询总数
            Assert.assertEquals(83, count);
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    @Test
    public void testDeleteByExample2() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            Condition example = new Condition(Country.class);
            example.createCriteria().andLike("countryname", "A%");
            example.or().andGreaterThan("id", 100);
            example.setDistinct(true);
            int count = mapper.deleteByCondition(example);
            //查询总数
            Assert.assertEquals(true, count > 83);
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

}
