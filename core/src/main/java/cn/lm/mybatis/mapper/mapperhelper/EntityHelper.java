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

package cn.lm.mybatis.mapper.mapperhelper;

import cn.lm.mybatis.mapper.mapperhelper.resolve.DefaultEntityResolve;
import cn.lm.mybatis.mapper.mapperhelper.resolve.EntityResolve;
import org.apache.ibatis.mapping.MappedStatement;
import cn.lm.mybatis.mapper.MapperException;
import cn.lm.mybatis.mapper.entity.Config;
import cn.lm.mybatis.mapper.entity.EntityColumn;
import cn.lm.mybatis.mapper.entity.EntityTable;
import cn.lm.mybatis.mapper.util.MetaObjectUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实体类工具类 - 处理实体和数据库表以及字段关键的一个类
 * <p/>
 * <p>项目地址 : <a href="https://github.com/abel533/Mapper" target="_blank">https://github.com/abel533/Mapper</a></p>
 *
 * @author liuzh
 */
public class EntityHelper {

    /**
     * 实体类 => 表对象
     */
    private static final Map<Class<?>, EntityTable> entityTableMap = new ConcurrentHashMap<Class<?>, EntityTable>();

    private static final EntityResolve DEFAULT = new DefaultEntityResolve();

    /**
     * 实体类解析器
     */
    private static EntityResolve resolve = DEFAULT;

    /**
     * 获取表对象
     *
     * @param entityClass
     * @return
     */
    public static EntityTable getEntityTable(Class<?> entityClass) {
        EntityTable entityTable = entityTableMap.get(entityClass);
        if (entityTable == null) {
            throw new MapperException("无法获取实体类" + entityClass.getName() + "对应的表名!");
        }
        return entityTable;
    }

    /**
     * 获取表对象，如果没有则返回Null
     *
     * @param entityClass
     * @return
     */
    public static EntityTable getEntityTableOrNull(Class<?> entityClass) {
        return entityTableMap.get(entityClass);
    }

    /**
     * 获取默认的orderby语句
     *
     * @param entityClass
     * @return
     */
    public static String getOrderByClause(Class<?> entityClass) {
        EntityTable table = getEntityTable(entityClass);
        if (table.getOrderByClause() != null) {
            return table.getOrderByClause();
        }

        List<EntityColumn> orderEntityColumns = new ArrayList<EntityColumn>();
        for (EntityColumn column : table.getEntityClassColumns()) {
            if (column.getOrderBy() != null) {
                orderEntityColumns.add(column);
            }
        }

        Collections.sort(orderEntityColumns, new Comparator<EntityColumn>() {
            @Override
            public int compare(EntityColumn o1, EntityColumn o2) {
                return o1.getOrderPriority() - o2.getOrderPriority();
            }
        });

        StringBuilder orderBy = new StringBuilder();
        for (EntityColumn column : orderEntityColumns) {
            if (orderBy.length() != 0) {
                orderBy.append(",");
            }
            orderBy.append(column.getColumn()).append(" ").append(column.getOrderBy());
        }
        table.setOrderByClause(orderBy.toString());
        return table.getOrderByClause();
    }

    /**
     * 获取全部列
     *
     * @param entityClass
     * @return
     */
    public static Set<EntityColumn> getColumns(Class<?> entityClass) {
        return getEntityTable(entityClass).getEntityClassColumns();
    }

    /**
     * 获取主键信息
     *
     * @param entityClass
     * @return
     */
    public static Set<EntityColumn> getPKColumns(Class<?> entityClass) {
        return getEntityTable(entityClass).getEntityClassPKColumns();
    }

    /**
     * 获取查询的Select
     *
     * @param entityClass
     * @return
     */
    public static String getSelectColumns(Class<?> entityClass) {
        EntityTable entityTable = getEntityTable(entityClass);
        if (entityTable.getBaseSelect() != null) {
            return entityTable.getBaseSelect();
        }
        Set<EntityColumn> columnList = getColumns(entityClass);
        StringBuilder selectBuilder = new StringBuilder();
        boolean skipAlias = Map.class.isAssignableFrom(entityClass);
        for (EntityColumn entityColumn : columnList) {
            selectBuilder.append(entityColumn.getColumn());
            if (!skipAlias && !entityColumn.getColumn().equalsIgnoreCase(entityColumn.getProperty())) {
                //不等的时候分几种情况，例如`DESC`
                if (entityColumn.getColumn().substring(1, entityColumn.getColumn().length() - 1).equalsIgnoreCase(entityColumn.getProperty())) {
                    selectBuilder.append(",");
                } else {
                    selectBuilder.append(" AS ").append(entityColumn.getProperty()).append(",");
                }
            } else {
                selectBuilder.append(",");
            }
        }
        entityTable.setBaseSelect(selectBuilder.substring(0, selectBuilder.length() - 1));
        return entityTable.getBaseSelect();
    }

    /**
     * 初始化实体属性
     *
     * @param entityClass
     * @param config
     */
    public static synchronized void initEntityNameMap(Class<?> entityClass, Config config) {
        if (entityTableMap.get(entityClass) != null) {
            return;
        }
        //创建并缓存EntityTable
        EntityTable entityTable = resolve.resolveEntity(entityClass, config);
        entityTableMap.put(entityClass, entityTable);
    }

    /**
     * 设置实体类解析器
     *
     * @param resolve
     */
    static void setResolve(EntityResolve resolve) {
        EntityHelper.resolve = resolve;
    }

    /**
     * 通过反射设置MappedStatement的keyProperties字段值
     *
     * @param pkColumns 所有的主键字段
     * @param ms        MappedStatement
     */
    public static void setKeyProperties(Set<EntityColumn> pkColumns, MappedStatement ms) {
        if (pkColumns == null || pkColumns.isEmpty()) {
            return;
        }

        List<String> keyProperties = new ArrayList<String>(pkColumns.size());
        for (EntityColumn column : pkColumns) {
            keyProperties.add(column.getProperty());
        }

        MetaObjectUtil.forObject(ms).setValue("keyProperties", keyProperties.toArray(new String[]{}));
    }
}