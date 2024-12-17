package cn.lm.mybatis.mapper.cache;

import cn.lm.mybatis.mapper.base.Country;
import org.apache.ibatis.annotations.CacheNamespace;
import cn.lm.mybatis.mapper.common.Mapper;

/**
 * 这个例子中，接口定义了缓存，对应的 XML 中引用这里的缓存
 * <p>
 * TODO MyBatis 有 Bug，这种方式目前行不通
 */
@CacheNamespace
public interface CountryCacheWithXmlMapper extends Mapper<Country> {

    /**
     * 定义在 XML 中的方法
     *
     * @param id
     * @return
     */
    Country selectById(Integer id);
}
