package cn.lm.mybatis.mapper.additional.insertlist;

import cn.lm.mybatis.mapper.genid.GenId;

import java.util.UUID;

/**
 * @author liuzh
 */
public class UUIdGenId implements GenId<String> {
    @Override
    public String genId(String table, String column) {
        return UUID.randomUUID().toString();
    }
}
