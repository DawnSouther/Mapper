package cn.lm.mybatis.mapper.session;

import cn.lm.mybatis.mapper.mapperhelper.MapperHelper;
import org.apache.ibatis.mapping.MappedStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.lm.mybatis.mapper.entity.Config;

import java.util.Properties;

/**
 * 使用提供的 Configuration 可以在纯 Java 或者 Spring(mybatis-spring-1.3.0+) 模式中使用
 *
 * @author liuzh
 */
public class Configuration extends org.apache.ibatis.session.Configuration {

    private final Logger log = LoggerFactory.getLogger(Configuration.class);

    private MapperHelper mapperHelper;

    /**
     * 直接注入 mapperHelper
     *
     * @param mapperHelper
     */
    public void setMapperHelper(MapperHelper mapperHelper) {
        this.mapperHelper = mapperHelper;
    }

    /**
     * 使用属性方式配置
     *
     * @param properties
     */
    public void setMapperProperties(Properties properties) {
        if (this.mapperHelper == null) {
            this.mapperHelper = new MapperHelper();
        }
        this.mapperHelper.setProperties(properties);
    }

    /**
     * 使用 Config 配置
     *
     * @param config
     */
    public void setConfig(Config config) {
        if (mapperHelper == null) {
            mapperHelper = new MapperHelper();
        }
        mapperHelper.setConfig(config);
    }

    @Override
    public void addMappedStatement(MappedStatement ms) {
        try {
            super.addMappedStatement(ms);
            //没有任何配置时，使用默认配置
            if (this.mapperHelper == null) {
                this.mapperHelper = new MapperHelper();
            }
            this.mapperHelper.processMappedStatement(ms);
        } catch (IllegalArgumentException e) {
            //这里的异常是导致 Spring 启动死循环的关键位置，为了避免后续会吞异常，这里直接输出
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

}
