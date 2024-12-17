package cn.lm.mybatis.mapper.mapperhelper.resolve;

import cn.lm.mybatis.mapper.entity.Config;
import cn.lm.mybatis.mapper.entity.EntityTable;

/**
 * 解析实体类接口
 *
 * @author liuzh
 */
public interface EntityResolve {

    /**
     * 解析类为 EntityTable
     *
     * @param entityClass
     * @param config
     * @return
     */
    EntityTable resolveEntity(Class<?> entityClass, Config config);

}
