package cn.lm.mybatis.mapper.cache;

import cn.lm.mybatis.mapper.base.Country;
import org.apache.ibatis.annotations.CacheNamespace;
import cn.lm.mybatis.mapper.common.Mapper;

/**
 * 只有接口时，加下面的注解即可
 */
@CacheNamespace
public interface CountryCacheMapper extends Mapper<Country> {

}
