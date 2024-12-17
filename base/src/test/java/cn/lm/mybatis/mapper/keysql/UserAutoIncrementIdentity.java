package cn.lm.mybatis.mapper.keysql;

import cn.lm.mybatis.mapper.annotation.KeySql;
import cn.lm.mybatis.mapper.code.IdentityDialect;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author liuzh
 */
@Table(name = "user_auto_increment")
public class UserAutoIncrementIdentity {
    @Id
    @KeySql(dialect = IdentityDialect.MYSQL)
    @Column(insertable = false)
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
