package cn.lm.mybatis.mapper.rawresultmap;

import org.apache.ibatis.annotations.Select;
import cn.lm.mybatis.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author liuzh
 */
public interface UserMapper extends BaseMapper<User> {


    @Select("SELECT * FROM user")
    List<User> selectRawAnnotation();

    List<User> fetchRawResultType();

    List<User> fetchRawResultMap();

    Map<String, Object> getMapUser();

    Integer selectCount2();
}
