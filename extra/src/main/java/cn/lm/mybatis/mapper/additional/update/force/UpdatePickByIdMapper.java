package cn.lm.mybatis.mapper.additional.update.force;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;
import cn.lm.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;

/**
 * @author qrqhuangcy
 * @Description: 通用Mapper接口, 非空字段强制更新
 * @date 2018-06-26
 */
@RegisterMapper
public interface UpdatePickByIdMapper<T> {

    /**
     * 根据主键更新传入字段的值
     *
     * @param record
     * @param forceUpdateProperties
     * @return
     */
    @UpdateProvider(type = UpdatePickByIdProvider.class, method = "dynamicSQL")
    int updatePickById(@Param("record") T record, @Param("forceUpdateProperties") List<String> forceUpdateProperties);
}
