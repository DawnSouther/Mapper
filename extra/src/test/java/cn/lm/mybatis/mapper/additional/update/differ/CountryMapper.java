package cn.lm.mybatis.mapper.additional.update.differ;


import org.apache.ibatis.annotations.Select;
import cn.lm.mybatis.mapper.additional.Country;

public interface CountryMapper extends UpdateByDifferMapper<Country> {

    @Select("select * from country where id = #{id}")
    Country selectById(Long id);
}
