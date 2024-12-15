/*
 * Copyright (c) 2011-2024, baomidou (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tk.mybatis.mapper.util.support;

import java.io.Serializable;

/**
 * @author miemie
 * @since 2018-12-30
 */
public class ColumnCache implements Serializable {

    private static final long serialVersionUID = -4586291538088403456L;

    /**
     * 使用 column
     */
    private String column;
    /**
     * 查询 column
     */
    private String columnSelect;
    /**
     * mapping
     */
    private String mapping;

    public ColumnCache(String column, String columnSelect) {
        this.column = column;
        this.columnSelect = columnSelect;
    }

    public ColumnCache(String column, String columnSelect, String mapping) {
        this.column = column;
        this.columnSelect = columnSelect;
        this.mapping = mapping;
    }

    public String getColumn() {
        return this.column;
    }

    public String getColumnSelect() {
        return this.columnSelect;
    }

    public String getMapping() {
        return this.mapping;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public void setColumnSelect(String columnSelect) {
        this.columnSelect = columnSelect;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ColumnCache)) return false;
        final ColumnCache other = (ColumnCache) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$column = this.getColumn();
        final Object other$column = other.getColumn();
        if (this$column == null ? other$column != null : !this$column.equals(other$column)) return false;
        final Object this$columnSelect = this.getColumnSelect();
        final Object other$columnSelect = other.getColumnSelect();
        if (this$columnSelect == null ? other$columnSelect != null : !this$columnSelect.equals(other$columnSelect))
            return false;
        final Object this$mapping = this.getMapping();
        final Object other$mapping = other.getMapping();
        if (this$mapping == null ? other$mapping != null : !this$mapping.equals(other$mapping)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ColumnCache;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $column = this.getColumn();
        result = result * PRIME + ($column == null ? 43 : $column.hashCode());
        final Object $columnSelect = this.getColumnSelect();
        result = result * PRIME + ($columnSelect == null ? 43 : $columnSelect.hashCode());
        final Object $mapping = this.getMapping();
        result = result * PRIME + ($mapping == null ? 43 : $mapping.hashCode());
        return result;
    }

    public String toString() {
        return "ColumnCache(column=" + this.getColumn() + ", columnSelect=" + this.getColumnSelect() + ", mapping=" + this.getMapping() + ")";
    }
}
