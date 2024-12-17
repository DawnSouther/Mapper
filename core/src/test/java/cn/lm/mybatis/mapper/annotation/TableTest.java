package cn.lm.mybatis.mapper.annotation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import cn.lm.mybatis.mapper.code.Style;
import cn.lm.mybatis.mapper.entity.Config;
import cn.lm.mybatis.mapper.entity.EntityTable;
import cn.lm.mybatis.mapper.mapperhelper.EntityHelper;

import jakarta.persistence.Table;

/**
 * @author liuzh
 */
public class TableTest {

    private Config config;

    @Before
    public void beforeTest() {
        config = new Config();
        config.setStyle(Style.normal);
    }

    @Table(name = "sys_user")
    class User {
        private String name;
    }

    @Test
    public void testColumn() {
        EntityHelper.initEntityNameMap(User.class, config);
        EntityTable entityTable = EntityHelper.getEntityTable(User.class);
        Assert.assertNotNull(entityTable);
        Assert.assertEquals("sys_user", entityTable.getName());
    }

}
