package tk.mybatis.mapper.base.delete;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import tk.mybatis.mapper.base.BaseTest;
import tk.mybatis.mapper.base.CountryMapper;

public class DeleteByIdMapperTest extends BaseTest {

    @Test
    public void testDeleteById() {
        SqlSession sqlSession = getSqlSession();
        try {
            CountryMapper mapper = sqlSession.getMapper(CountryMapper.class);
            Assert.assertEquals(183, mapper.selectAll().size());
            Assert.assertEquals(1, mapper.deleteById(1L));
            Assert.assertEquals(182, mapper.selectAll().size());

            Assert.assertEquals(1, mapper.deleteById(2));
            Assert.assertEquals(181, mapper.selectAll().size());

            Assert.assertEquals(1, mapper.deleteById("3"));
            Assert.assertEquals(180, mapper.selectAll().size());

            Assert.assertEquals(0, mapper.deleteById(1));
            Assert.assertEquals(180, mapper.selectAll().size());
        } finally {
            sqlSession.close();
        }
    }

}
