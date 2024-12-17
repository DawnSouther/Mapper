package cn.lm.mybatis.mapper.additional.upsert;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;
import cn.lm.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;

@RegisterMapper
public interface BatchUpsertMapper<T> {

    @UpdateProvider(
            type = BatchUpsertProvider.class,
            method = "dynamicSQL"
    )
    void batchUpsert(@Param("list") List<? extends T> recordList);
}
