package cn.lm.mybatis.mapper.annotation;

import cn.lm.mybatis.mapper.annotation.RegisterMapper;
import org.apache.ibatis.session.Configuration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import cn.lm.mybatis.mapper.code.Style;
import cn.lm.mybatis.mapper.entity.Config;
import cn.lm.mybatis.mapper.mapperhelper.MapperHelper;

/**
 * @author liuzh
 */
public class RegisterMapperTest {

    private Config config;

    private Configuration configuration;

    @Before
    public void beforeTest() {
        config = new Config();
        config.setStyle(Style.normal);

        configuration = new Configuration();
    }

    @RegisterMapper
    interface MapperHashRegisterMapper {

    }

    interface UserMapper extends MapperHashRegisterMapper {

    }

    @Test
    public void testHashRegisterMapper() {
        MapperHelper mapperHelper = new MapperHelper();
        Assert.assertTrue(mapperHelper.isExtendCommonMapper(UserMapper.class));
    }

    interface RoleMapper {

    }

    @Test
    public void testRoleMapper() {
        MapperHelper mapperHelper = new MapperHelper();
        Assert.assertFalse(mapperHelper.isExtendCommonMapper(RoleMapper.class));
    }

    @RegisterMapper
    interface RoleMapper2 {

    }

    @Test
    public void testRoleMapper2() {
        MapperHelper mapperHelper = new MapperHelper();
        Assert.assertFalse(mapperHelper.isExtendCommonMapper(RoleMapper2.class));
    }

}
