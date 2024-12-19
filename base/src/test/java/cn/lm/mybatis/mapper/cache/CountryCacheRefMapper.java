package cn.lm.mybatis.mapper.cache;

import cn.lm.mybatis.mapper.base.Country;
import org.apache.ibatis.annotations.CacheNamespaceRef;
import cn.lm.mybatis.mapper.common.AllMapper;

/**
 * 这个例子中，在 XML 配置了缓存，这里使用注解引用 XML 中的缓存配置
 * <p>
 * namespace 有两种配置方法，参考下面两行注解
 */
@CacheNamespaceRef(CountryCacheRefMapper.class)
//@CacheNamespaceRef(name = "cn.lm.mybatis.mapper.cache.CountryCacheRefMapper")
public interface CountryCacheRefMapper extends AllMapper<Country> {

    /**
     * 定义在 XML 中的方法
     *
     * @param id
     * @return
     */
    Country selectById2(Integer id);
}
