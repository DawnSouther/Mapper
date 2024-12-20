package cn.lm.mybatis.mapper.annotation;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import cn.lm.mybatis.mapper.common.AllMapper;
import cn.lm.mybatis.mapper.entity.Config;
import cn.lm.mybatis.mapper.mapperhelper.MapperHelper;
import cn.lm.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuzh
 */

public class SpringAnnotationTest {

    private AnnotationConfigApplicationContext applicationContext;

    @Before
    public void setupContext() {
        applicationContext = new AnnotationConfigApplicationContext();
    }

    private void startContext() {
        applicationContext.refresh();
        applicationContext.start();
        // this will throw an exception if the beans cannot be found
        applicationContext.getBean("sqlSessionFactory");
    }

    @Test
    public void testMyBatisConfigRef() {
        applicationContext.register(MyBatisConfigRef.class);
        startContext();
        CountryMapper countryMapper = applicationContext.getBean(CountryMapper.class);
        List<Country> countries = countryMapper.selectAll();
        Assert.assertNotNull(countries);
        Assert.assertEquals(183, countries.size());
    }

    @Test
    public void testMyBatisConfigProperties() {
        applicationContext.register(MyBatisConfigProperties.class);
        startContext();
        CountryMapper countryMapper = applicationContext.getBean(CountryMapper.class);
        List<Country> countries = countryMapper.selectAll();
        Assert.assertNotNull(countries);
        Assert.assertEquals(183, countries.size());
    }

    @Test
    public void testMyBatisConfiguration() {
        applicationContext.register(MyBatisConfiguration.class);
        startContext();
        CountryMapper countryMapper = applicationContext.getBean(CountryMapper.class);
        List<Country> countries = countryMapper.selectAll();
        Assert.assertNotNull(countries);
        Assert.assertEquals(183, countries.size());
    }

    @Test(expected = Exception.class)
    public void testMyBatisConfigPropertiesError() {
        applicationContext.register(MyBatisConfigPropertiesError.class);
        startContext();
        CountryMapper countryMapper = applicationContext.getBean(CountryMapper.class);
        List<Country> countries = countryMapper.selectAll();
        Assert.assertNotNull(countries);
        Assert.assertEquals(183, countries.size());
    }

    @Configuration
    @MapperScan(value = "cn.lm.mybatis.mapper.annotation", mapperHelperRef = "mapperHelper")
    public static class MyBatisConfigRef {
        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .addScript("cn/lm/mybatis/mapper/xml/CreateDB.sql")
                    .build();
        }

        @Bean
        public DataSourceTransactionManager transactionManager() {
            return new DataSourceTransactionManager(dataSource());
        }

        @Bean
        public SqlSessionFactory sqlSessionFactory() throws Exception {
            SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
            sessionFactory.setDataSource(dataSource());
            return sessionFactory.getObject();
        }

        @Bean
        public MapperHelper mapperHelper() {
            Config config = new Config();
            List<Class> mappers = new ArrayList<Class>();
            mappers.add(AllMapper.class);
            config.setMappers(mappers);

            MapperHelper mapperHelper = new MapperHelper();
            mapperHelper.setConfig(config);
            return mapperHelper;
        }
    }

    @Configuration
    @MapperScan(value = "cn.lm.mybatis.mapper.annotation",
            properties = {
                    "mappers=cn.lm.mybatis.mapper.common.AllMapper",
                    "notEmpty=true"
            }
    )
    public static class MyBatisConfigProperties {
        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .addScript("cn/lm/mybatis/mapper/xml/CreateDB.sql")
                    .build();
        }

        @Bean
        public DataSourceTransactionManager transactionManager() {
            return new DataSourceTransactionManager(dataSource());
        }

        @Bean
        public SqlSessionFactory sqlSessionFactory() throws Exception {
            SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
            sessionFactory.setDataSource(dataSource());
            return sessionFactory.getObject();
        }
    }

    @Configuration
    @MapperScan(value = "cn.lm.mybatis.mapper.annotation",
            properties = {
                    //参数配置错误
                    "mapperscn.lm.mybatis.mapper.common.AllMapper",
                    "notEmpty=true"
            }
    )
    public static class MyBatisConfigPropertiesError {
        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .addScript("cn/lm/mybatis/mapper/xml/CreateDB.sql")
                    .build();
        }

        @Bean
        public DataSourceTransactionManager transactionManager() {
            return new DataSourceTransactionManager(dataSource());
        }

        @Bean
        public SqlSessionFactory sqlSessionFactory() throws Exception {
            SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
            sessionFactory.setDataSource(dataSource());
            return sessionFactory.getObject();
        }
    }

    @Configuration
    @MapperScan(value = "cn.lm.mybatis.mapper.annotation")
    public static class MyBatisConfiguration {
        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .addScript("cn/lm/mybatis/mapper/xml/CreateDB.sql")
                    .build();
        }

        @Bean
        public DataSourceTransactionManager transactionManager() {
            return new DataSourceTransactionManager(dataSource());
        }

        @Bean
        public SqlSessionFactory sqlSessionFactory() throws Exception {
            SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
            sessionFactory.setDataSource(dataSource());
            cn.lm.mybatis.mapper.session.Configuration configuration = new cn.lm.mybatis.mapper.session.Configuration();
            configuration.setMapperHelper(new MapperHelper());
            sessionFactory.setConfiguration(configuration);
            return sessionFactory.getObject();
        }

    }


}
