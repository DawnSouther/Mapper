package cn.lm.mybatis.mapper.keysql;

import cn.lm.mybatis.mapper.annotation.KeySql;
import cn.lm.mybatis.mapper.code.ORDER;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author liuzh
 */
@Table(name = "user")
public class UserSqlBefore {
    @Id
    @KeySql(sql = "select 12345", order = ORDER.BEFORE)
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
