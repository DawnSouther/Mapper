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

package cn.lm.mybatis.mapper.test.user;

import cn.lm.mybatis.mapper.mapper.MybatisHelper;
import cn.lm.mybatis.mapper.mapper.UserLoginMapper;
import cn.lm.mybatis.mapper.model.UserLogin;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 通过主键删除
 *
 * @author liuzh
 */
public class TestUserLogin {


    /**
     * 新增
     */
    @Test
    public void testInsert() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            UserLoginMapper mapper = sqlSession.getMapper(UserLoginMapper.class);

            UserLogin userLogin = new UserLogin();
            userLogin.setUsername("abel533");
            userLogin.setLogindate(new Date());
            userLogin.setLoginip("192.168.123.1");

            Assert.assertEquals(1, mapper.insert(userLogin));

            Assert.assertNotNull(userLogin.getLogid());
            Assert.assertTrue(userLogin.getLogid() > 10);
            //这里测了实体类入参的删除
            System.out.println(userLogin.getLogid());
            Assert.assertEquals(1, mapper.deleteById(userLogin));

        } finally {
            sqlSession.close();
        }
    }

    /**
     * 查询
     */
    @Test
    public void testSelect() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            UserLoginMapper mapper = sqlSession.getMapper(UserLoginMapper.class);
            UserLogin userLogin = new UserLogin();
            userLogin.setUsername("test1");
            List<UserLogin> userLogins = mapper.select(userLogin);
            Assert.assertEquals(5, userLogins.size());
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 根据主键全更新
     */
    @Test
    public void testUpdateByPrimaryKey() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            UserLoginMapper mapper = sqlSession.getMapper(UserLoginMapper.class);
            HashMap<String, Object> key = new HashMap<String, Object>();
            key.put("logid", 2);
            key.put("username", "test1");
            UserLogin userLogin = mapper.selectById(key);
            Assert.assertNotNull(userLogin);
            userLogin.setLoginip("1.1.1.1");
            userLogin.setLogindate(null);
            //不会更新username
            Assert.assertEquals(1, mapper.updateById(userLogin));

            userLogin = mapper.selectById(key);
            Assert.assertNull(userLogin.getLogindate());
            Assert.assertEquals("1.1.1.1", userLogin.getLoginip());
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 根据主键更新非null
     */
    @Test
    public void testUpdateByPrimaryKeySelective() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            UserLoginMapper mapper = sqlSession.getMapper(UserLoginMapper.class);

            HashMap<String, Object> key = new HashMap<String, Object>();
            key.put("logid", 1);
            key.put("username", "test1");

            UserLogin userLogin = mapper.selectById(key);
            Assert.assertNotNull(userLogin);
            userLogin.setLogindate(null);
            userLogin.setLoginip("1.1.1.1");
            //不会更新username
            Assert.assertEquals(1, mapper.updateByIdSelective(userLogin));

            userLogin = mapper.selectById(key);
            Assert.assertNotNull(userLogin.getLogindate());
            Assert.assertEquals("1.1.1.1", userLogin.getLoginip());
        } finally {
            sqlSession.close();
        }
    }


}
