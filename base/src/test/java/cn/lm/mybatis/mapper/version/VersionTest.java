package cn.lm.mybatis.mapper.version;

import cn.lm.mybatis.mapper.base.BaseTest;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.sql.Timestamp;

import static org.junit.Assert.*;

/**
 * @author liuzh
 */
public class VersionTest extends BaseTest {

    @Override
    protected Reader getConfigFileAsReader() throws IOException {
        return toReader(VersionTest.class.getResource("mybatis-config-version.xml"));
    }

    @Override
    protected Reader getSqlFileAsReader() throws IOException {
        return toReader(VersionTest.class.getResource("CreateDB.sql"));
    }

    @Test
    public void testInsert() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserTimestampMapper mapper = sqlSession.getMapper(UserTimestampMapper.class);
            UserTimestamp user = new UserTimestamp();
            user.setId(1);
            user.setJoinDate(new Timestamp(System.currentTimeMillis()));
            int count = mapper.insert(user);
            assertEquals(1, count);
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdate() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserTimestampMapper mapper = sqlSession.getMapper(UserTimestampMapper.class);
            UserTimestamp user = mapper.selectById(999);
            assertNotNull(user);
            Timestamp joinDate = user.getJoinDate();
            int count = mapper.updateById(user);
            assertEquals(1, count);

            user = mapper.selectById(999);
            assertFalse(joinDate.equals(user.getJoinDate()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateByPrimaryKeySelective() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserTimestampMapper mapper = sqlSession.getMapper(UserTimestampMapper.class);
            UserTimestamp user = mapper.selectById(999);
            assertNotNull(user);
            Timestamp joinDate = user.getJoinDate();
            int count = mapper.updateByIdSelective(user);
            assertEquals(1, count);

            user = mapper.selectById(999);
            assertFalse(joinDate.equals(user.getJoinDate()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateInt() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserIntMapper mapper = sqlSession.getMapper(UserIntMapper.class);
            UserInt user = mapper.selectById(999);
            assertNotNull(user);
            Integer age = user.getAge();
            int count = mapper.updateById(user);
            assertEquals(1, count);

            user = mapper.selectById(999);
            assertFalse(age.equals(user.getAge()));
        } finally {
            sqlSession.close();
        }
    }

    @Test
    public void testUpdateIntByPrimaryKeySelective() {
        SqlSession sqlSession = getSqlSession();
        try {
            UserIntMapper mapper = sqlSession.getMapper(UserIntMapper.class);
            UserInt user = mapper.selectById(999);
            assertNotNull(user);
            Integer age = user.getAge();
            int count = mapper.updateByIdSelective(user);
            assertEquals(1, count);

            user = mapper.selectById(999);
            assertFalse(age.equals(user.getAge()));
        } finally {
            sqlSession.close();
        }
    }


}
