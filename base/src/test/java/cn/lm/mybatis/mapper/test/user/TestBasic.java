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
import cn.lm.mybatis.mapper.mapper.UserInfoMapper;
import cn.lm.mybatis.mapper.model.UserInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

/**
 * 测试增删改查
 *
 * @author liuzh
 */
public class TestBasic {


    /**
     * 新增
     */
    @Test
    public void testInsert() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            UserInfoMapper mapper = sqlSession.getMapper(UserInfoMapper.class);
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername("abel533");
            userInfo.setPassword("123456");
            userInfo.setUsertype("2");
            userInfo.setEmail("abel533@gmail.com");
            Collection collection = sqlSession.getConfiguration().getMappedStatements();
            for (Object o : collection) {
                if (o instanceof MappedStatement) {
                    MappedStatement ms = (MappedStatement) o;
                    if (ms.getId().contains("UserInfoMapper.insert")) {
                        System.out.println(ms.getId());
                    }
                }
            }

            Assert.assertEquals(1, mapper.insert(userInfo));

            Assert.assertNotNull(userInfo.getId());
            Assert.assertTrue((int) userInfo.getId() >= 6);

            Assert.assertEquals(1, mapper.deleteById(userInfo));
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }

    /**
     * 主要测试删除
     */
    @Test
    public void testDelete() {
        SqlSession sqlSession = MybatisHelper.getSqlSession();
        try {
            UserInfoMapper mapper = sqlSession.getMapper(UserInfoMapper.class);
            //查询总数
            Assert.assertEquals(5, mapper.selectCount(new UserInfo()));
            //查询100
            UserInfo userInfo = mapper.selectById(1);


            //根据主键删除
            Assert.assertEquals(1, mapper.deleteById(1));


            //查询总数
            Assert.assertEquals(4, mapper.selectCount(new UserInfo()));
            //插入
            Assert.assertEquals(1, mapper.insert(userInfo));
        } finally {
            sqlSession.rollback();
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
            UserInfoMapper mapper = sqlSession.getMapper(UserInfoMapper.class);
            UserInfo userInfo = new UserInfo();
            userInfo.setUsertype("1");
            List<UserInfo> userInfos = mapper.select(userInfo);
            Assert.assertEquals(3, userInfos.size());
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
            UserInfoMapper mapper = sqlSession.getMapper(UserInfoMapper.class);
            UserInfo userInfo = mapper.selectById(2);
            Assert.assertNotNull(userInfo);
            userInfo.setUsertype(null);
            userInfo.setEmail("abel533@gmail.com");
            //不会更新username
            Assert.assertEquals(1, mapper.updateById(userInfo));

            userInfo = mapper.selectById(userInfo);
            Assert.assertNull(userInfo.getUsertype());
            Assert.assertEquals("abel533@gmail.com", userInfo.getEmail());
        } finally {
            sqlSession.rollback();
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
            UserInfoMapper mapper = sqlSession.getMapper(UserInfoMapper.class);
            UserInfo userInfo = mapper.selectById(1);
            Assert.assertNotNull(userInfo);
            userInfo.setUsertype(null);
            userInfo.setEmail("abel533@gmail.com");
            //不会更新username
            Assert.assertEquals(1, mapper.updateByIdSelective(userInfo));

            userInfo = mapper.selectById(1);
            Assert.assertEquals("1", userInfo.getUsertype());
            Assert.assertEquals("abel533@gmail.com", userInfo.getEmail());
        } finally {
            sqlSession.rollback();
            sqlSession.close();
        }
    }


}
