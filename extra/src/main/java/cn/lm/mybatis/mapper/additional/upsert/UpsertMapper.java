package cn.lm.mybatis.mapper.additional.upsert;

import org.apache.ibatis.annotations.UpdateProvider;
import cn.lm.mybatis.mapper.annotation.RegisterMapper;

@RegisterMapper
public interface UpsertMapper<T> {

    @UpdateProvider(
            type = UpsertProvider.class,
            method = "dynamicSQL"
    )
    void upsert(T record);
}
