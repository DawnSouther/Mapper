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

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;

import cn.lm.mybatis.mapper.mapper.CountryMapper;
import cn.lm.mybatis.mapper.mapper.MybatisHelper;
import cn.lm.mybatis.mapper.model.Country;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 通过主键删除
 *
 * @author liuzh
 */
public class TestDeleteById {

    @Before
    public void setupDB() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            Connection conn = sqlSession.getConnection();
            Reader reader = Resources.getResourceAsReader("CreateDB.sql");
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setLogWriter(null);
            runner.runScript(reader);
            reader.close();
        } catch (IOException e) {}
        finally {
            sqlSession.close();
        }
    }

    /**
     * 主要测试删除
     */
    @Test
    public void testDynamicDelete() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            //查询总数
            Assert.assertEquals(183, mapper.selectCount(new Country()));
            //查询100
            Country country = mapper.selectById(100);
            //根据主键删除
            Assert.assertEquals(1, mapper.deleteById(100));
            //查询总数
            Assert.assertEquals(182, mapper.selectCount(new Country()));
            //插入
            Assert.assertEquals(1, mapper.insert(country));
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 删除不存在的主键
     */
    @Test
    public void testDynamicDeleteZero() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            //根据主键删除
            Assert.assertEquals(0, mapper.deleteById(null));
            Assert.assertEquals(0, mapper.deleteById(-100));
            Assert.assertEquals(0, mapper.deleteById(0));
            Assert.assertEquals(0, mapper.deleteById(1000));
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 对象包含主键即可
     */
    @Test
    public void testDynamicDeleteEntity() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);

            Country country = new Country();
            country.setId(100);
            Assert.assertEquals(1, mapper.deleteById(country));
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
            //根据主键删除
            Assert.assertEquals(1, mapper.deleteById(100));
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    class Key {
    }

}
