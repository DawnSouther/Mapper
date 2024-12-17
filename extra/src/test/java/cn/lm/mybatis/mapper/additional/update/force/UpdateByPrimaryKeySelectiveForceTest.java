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

package cn.lm.mybatis.mapper.additional.update.force;

import cn.lm.mybatis.mapper.additional.BaseTest;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.Arrays;

/**
 * @author qrqhuangcy
 * @Description: 验证数值空值强制更新
 * @date 2018-06-25
 */
public class UpdateByPrimaryKeySelectiveForceTest extends BaseTest {

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

    @Test
    public void testUpdateByPrimaryKeySelectiveForceByNull() {
        SqlSession sqlSession = getSqlSession();
        try {
            CountryIntMapperPick mapper = sqlSession.getMapper(CountryIntMapperPick.class);
            CountryInt country = new CountryInt();
            country.setId(174);
            country.setCountryname("英国");
            mapper.updatePickById(country, null);

            country = mapper.selectById(174);
            Assert.assertNotNull(country.getCountrycode());
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateByPrimaryKeySelectiveForce() {
        SqlSession sqlSession = getSqlSession();
        try {
            CountryIntMapperPick mapper = sqlSession.getMapper(CountryIntMapperPick.class);
            CountryInt country = new CountryInt();
            country.setId(174);
            mapper.updatePickById(country, Arrays.asList("countrycode", "countryname"));

            country = mapper.selectById(174);
            Assert.assertNull(country.getCountrycode());
            Assert.assertNull(country.getCountryname());
        } finally {
            sqlSession.close();
        }
    }
}
