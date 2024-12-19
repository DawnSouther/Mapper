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
import cn.lm.mybatis.mapper.mapper.CountryVersionMapper;
import cn.lm.mybatis.mapper.mapper.MybatisHelper;
import cn.lm.mybatis.mapper.model.Country;
import cn.lm.mybatis.mapper.model.CountryVersion;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

/**
 * 通过PK更新实体类全部属性
 *
 * @author liuzh
 */
public class TestUpdateByPrimaryKey {

    /**
     * 更新0个
     */
    @Test
    public void testDynamicUpdateByPrimaryKeyAll() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            Assert.assertEquals(0, mapper.updateById(new Country()));
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 入参为null时更新全部
     */
    @Test
    public void testDynamicUpdateByPrimaryKeyAllByNull() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            mapper.updateById(null);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 根据查询条件进行查询
     */
    @Test
    public void testDynamicUpdateByPrimaryKey() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            Country country = new Country();
            country.setId(174);
            country.setCountryname(null);
            country.setCountryname("美国");
            Assert.assertEquals(1, mapper.updateById(country));

            country = mapper.selectById(174);
            Assert.assertNotNull(country);
            Assert.assertEquals(174, (int) country.getId());
            Assert.assertEquals("美国", country.getCountryname());
            Assert.assertNull(country.getCountrycode());
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 继承类可以使用,但多出来的属性无效
     */
    @Test
    public void testDynamicUpdateByPrimaryKeyNotFoundKeyProperties() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);

            Assert.assertEquals(0, mapper.updateById(new Key()));

            Key key = new Key();
            key.setId(174);
            key.setCountrycode("CN");
            key.setCountrytel("+86");
            Assert.assertEquals(1, mapper.updateById(key));
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 根据查询条件进行查询
     */
    @Test
    public void testUpdateByPrimaryKeyAndVersion() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CountryVersionMapper mapper = sqlSession.getMapper(CountryVersionMapper.class);
            CountryVersion country = mapper.selectById(174);
            Assert.assertNotNull(country);
            Assert.assertEquals(Integer.valueOf(1), country.getVersion());
            country.setCountryname("美国2");
            Assert.assertEquals(1, mapper.updateById(country));

            country = mapper.selectById(174);
            Assert.assertNotNull(country);
            Assert.assertEquals(Integer.valueOf(2), country.getVersion());

            country.setCountryname("美国3");
            Assert.assertEquals(1, mapper.updateById(country));

            country = mapper.selectById(174);
            Assert.assertNotNull(country);
            Assert.assertEquals(Integer.valueOf(3), country.getVersion());

            country.setCountryname("美国4");
            Assert.assertEquals(1, mapper.updateById(country));

            country = mapper.selectById(174);
            Assert.assertNotNull(country);
            Assert.assertEquals(Integer.valueOf(4), country.getVersion());
        } finally {
            sqlSession.close();
        }
    }

    class Key extends Country {
        private String countrytel;

        public String getCountrytel() {
            return countrytel;
        }

        public void setCountrytel(String countrytel) {
            this.countrytel = countrytel;
        }
    }
}
