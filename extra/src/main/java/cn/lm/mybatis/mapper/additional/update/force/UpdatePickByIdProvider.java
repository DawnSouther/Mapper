/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package cn.lm.mybatis.mapper.additional.update.force;

import org.apache.ibatis.mapping.MappedStatement;
import cn.lm.mybatis.mapper.annotation.Version;
import cn.lm.mybatis.mapper.entity.EntityColumn;
import cn.lm.mybatis.mapper.mapperhelper.EntityHelper;
import cn.lm.mybatis.mapper.mapperhelper.MapperHelper;
import cn.lm.mybatis.mapper.mapperhelper.MapperTemplate;
import cn.lm.mybatis.mapper.mapperhelper.SqlHelper;
import cn.lm.mybatis.mapper.util.StringUtil;
import cn.lm.mybatis.mapper.version.VersionException;

import java.util.Set;

/**
 * @author qrqhuangcy
 * @Description: 通用Mapper接口, 更新, 强制，实现
 * @date 2018-06-26
 */
public class UpdatePickByIdProvider extends MapperTemplate {

    public static final String FORCE_UPDATE_PROPERTIES = "forceUpdateProperties";

    public UpdatePickByIdProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }


    public String updatePickById(MappedStatement ms) {
        Class entityClass = getEntityClass(ms);
        StringBuilder sql = new StringBuilder();
        sql.append(SqlHelper.updateTable(entityClass, tableName(entityClass), "record"));
        sql.append(this.updateSetColumnsForce(entityClass, "record", true, isNotEmpty()));
        sql.append(SqlHelper.wherePKColumns(entityClass, "record", true));

        return sql.toString();
    }

    /**
     * update set列
     *
     * @param entityClass
     * @param entityName  实体映射名
     * @param notNull     是否判断!=null
     * @param notEmpty    是否判断String类型!=''
     * @return
     */
    public String updateSetColumnsForce(Class<?> entityClass, String entityName, boolean notNull, boolean notEmpty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<set>");
        //获取全部列
        Set<EntityColumn> columnSet = EntityHelper.getColumns(entityClass);
        //对乐观锁的支持
        EntityColumn versionColumn = null;
        //当某个列有主键策略时，不需要考虑他的属性是否为空，因为如果为空，一定会根据主键策略给他生成一个值
        for (EntityColumn column : columnSet) {
            if (column.getEntityField().isAnnotationPresent(Version.class)) {
                if (versionColumn != null) {
                    throw new VersionException(entityClass.getName() + " 中包含多个带有 @Version 注解的字段，一个类中只能存在一个带有 @Version 注解的字段!");
                }
                versionColumn = column;
            }
            if (!column.isId() && column.isUpdatable()) {
                if (column == versionColumn) {
                    Version version = versionColumn.getEntityField().getAnnotation(Version.class);
                    String versionClass = version.nextVersion().getName();
                    //version = ${@cn.lm.mybatis.mapper.version@nextVersionClass("versionClass", version)}
                    sql.append(column.getColumn())
                            .append(" = ${@cn.lm.mybatis.mapper.version.VersionUtil@nextVersion(")
                            .append("@").append(versionClass).append("@class, ");
                    //虽然从函数调用上来看entityName必为"record"，但还是判断一下
                    if (StringUtil.isNotEmpty(entityName)) {
                        sql.append(entityName).append('.');
                    }
                    sql.append(column.getProperty()).append(")},");
                } else if (notNull) {
                    sql.append(this.getIfNotNull(entityName, column, column.getColumnEqualsHolder(entityName) + ",", notEmpty));
                } else {
                    sql.append(column.getColumnEqualsHolder(entityName)).append(",");
                }
            } else if (column.isId() && column.isUpdatable()) {
                //set id = id,
                sql.append(column.getColumn()).append(" = ").append(column.getColumn()).append(",");
            }
        }
        sql.append("</set>");
        return sql.toString();
    }

    /**
     * 判断自动!=null的条件结构
     *
     * @param entityName
     * @param column
     * @param contents
     * @param empty
     * @return
     */
    public String getIfNotNull(String entityName, EntityColumn column, String contents, boolean empty) {
        StringBuilder sql = new StringBuilder();
        sql.append("<choose>");

        //指定的字段会被强制更新
        sql.append("<when test=\"");
        sql.append(FORCE_UPDATE_PROPERTIES).append(" != null and ").append(FORCE_UPDATE_PROPERTIES).append(".contains('");
        sql.append(column.getProperty());
        sql.append("')\">");
        sql.append(contents);
        sql.append("</when>");

        sql.append("<otherwise></otherwise>");
        sql.append("</choose>");
        return sql.toString();
    }

}
