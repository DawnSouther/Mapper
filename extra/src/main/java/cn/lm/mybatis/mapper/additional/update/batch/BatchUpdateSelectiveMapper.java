package cn.lm.mybatis.mapper.additional.update.batch;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;
import cn.lm.mybatis.mapper.annotation.RegisterMapper;

import java.util.List;

@RegisterMapper
public interface BatchUpdateSelectiveMapper<T> {

    @UpdateProvider(
            type = BatchUpdateProvider.class,
            method = "dynamicSQL"
    )
    void batchUpdateSelective(@Param("list") List<? extends T> recordList);
}
