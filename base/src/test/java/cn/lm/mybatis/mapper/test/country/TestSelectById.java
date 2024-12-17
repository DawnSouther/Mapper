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

package cn.lm.mybatis.mapper.test.country;

import cn.lm.mybatis.mapper.mapper.CountryMapper;
import cn.lm.mybatis.mapper.mapper.MybatisHelper;
import cn.lm.mybatis.mapper.model.Country;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * 通过主键查询
 *
 * @author liuzh
 */
public class TestSelectById {

    /**
     * 根据PK进行查询
     */
    @Test
    public void testDynamicSelectById2() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            Country country = mapper.selectById(35);

            Assert.assertNotNull(country);
            Assert.assertEquals(true, country.getId() == 35);
            Assert.assertEquals("China", country.getCountryname());
            Assert.assertEquals("CN", country.getCountrycode());
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 包含主键的对象做参数就行
     */
    @Test
    public void testDynamicSelectById() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            Country country = new Country();
            country.setId(35);
            country = mapper.selectById(country);
            Assert.assertNotNull(country);
            Assert.assertEquals(true, country.getId() == 35);
            Assert.assertEquals("China", country.getCountryname());
            Assert.assertEquals("CN", country.getCountrycode());
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 查询不存在的结果
     */
    @Test
    public void testDynamicSelectByIdZero() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            Assert.assertNull(mapper.selectById(new Country()));
            Assert.assertNull(mapper.selectById(new HashMap<String, Object>()));
            Assert.assertNull(mapper.selectById(-10));
            Assert.assertNull(mapper.selectById(0));
            Assert.assertNull(mapper.selectById(1000));
            Assert.assertNull(mapper.selectById(null));
        } finally {
            sqlSession.close();
        }
    }


    /**
     * 主键格式错误
     */
    @Test
    public void testDynamicDeleteException() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            mapper.selectById(100);
        } finally {
            sqlSession.close();
        }
    }

    class Key {
    }
}
