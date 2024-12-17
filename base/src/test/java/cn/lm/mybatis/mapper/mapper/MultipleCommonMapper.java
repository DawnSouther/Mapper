package cn.lm.mybatis.mapper.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import cn.lm.mybatis.mapper.annotation.RegisterMapper;
import cn.lm.mybatis.mapper.provider.base.BaseInsertProvider;
import cn.lm.mybatis.mapper.provider.base.BaseSelectProvider;

import java.util.List;

@RegisterMapper
public interface MultipleCommonMapper<T> {
    @SelectProvider(type = BaseSelectProvider.class, method = "dynamicSQL")
    List<T> select(T record);
    @InsertProvider(type = BaseInsertProvider.class, method = "dynamicSQL")
    int insert(T record);
}
