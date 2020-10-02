package com.zrzhen.admin.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * 集成MYBATIS的数据源配置支持多数据源配置(描述：使用配置多个数据源多个事务对应方式)
 *
 * @author chenanlian
 */
@Configuration
@MapperScan(basePackages = {MybatisMysqlConfig.PACKAGE}, sqlSessionTemplateRef = "mysqlSqlSessionTemplate")
public class MybatisMysqlConfig implements EnvironmentAware {

    protected static final String RESOURCES = "classpath*:mapper/mysql/*.xml";
    protected static final String PACKAGE = "com.zrzhen.admin.module.*.dao";

    private org.springframework.core.env.Environment environment;

    @Override
    public void setEnvironment(final org.springframework.core.env.Environment environment) {
        this.environment = environment;
    }

    @Bean(name = "mysqlDataSource")
    @Primary
    public javax.sql.DataSource mysqlDataSource() throws Exception {
        java.util.Properties props = new java.util.Properties();
        /*驱动*/
        props.put(DruidDataSourceFactory.PROP_DRIVERCLASSNAME, environment.getProperty("admin.driver"));
        props.put(DruidDataSourceFactory.PROP_URL, environment.getProperty("admin.url"));
        props.put(DruidDataSourceFactory.PROP_USERNAME, environment.getProperty("admin.username"));
        props.put(DruidDataSourceFactory.PROP_PASSWORD, environment.getProperty("admin.password"));
        /*SQL查询,用来验证从连接池取出的连接*/
        props.put(DruidDataSourceFactory.PROP_VALIDATIONQUERY, environment.getProperty("admin.validationQuery"));
        /*指明连接是否被空闲连接回收器(如果有)进行检验，如果检测失败，则连接将被从池中去除*/
        props.put(DruidDataSourceFactory.PROP_TESTWHILEIDLE, "true");
        /*连接池中连接，在时间段内一直空闲，被逐出连接池的时间(1000*60*60)，以毫秒为单位*/
        props.put(DruidDataSourceFactory.PROP_MINEVICTABLEIDLETIMEMILLIS, "3600000");
        /*在空闲连接回收器线程运行期间休眠的时间值,以毫秒为单位，一般比minEvictableIdleTimeMillis小*/
        props.put(DruidDataSourceFactory.PROP_TIMEBETWEENEVICTIONRUNSMILLIS, "300000");
        /*在每次空闲连接回收器线程(如果有)运行时检查的连接数量，最好和maxActive一致*/
        props.put(DruidDataSourceFactory.PROP_NUMTESTSPEREVICTIONRUN, environment.getProperty("admin.poolMaximumActiveConnections"));
        props.put(DruidDataSourceFactory.PROP_MAXACTIVE, environment.getProperty("admin.poolMaximumActiveConnections"));
        props.put(DruidDataSourceFactory.PROP_INITIALSIZE, environment.getProperty("admin.initialSize"));
        props.put(DruidDataSourceFactory.PROP_FILTERS, "stat");
        props.put(DruidDataSourceFactory.PROP_CONNECTIONPROPERTIES, "druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000");
        props.put(DruidDataSourceFactory.PROP_DEFAULTAUTOCOMMIT, "true");
        return DruidDataSourceFactory.createDataSource(props);
    }

    @Bean(name = "mysqlTransactionManager")
    public DataSourceTransactionManager mysqlTransactionManager(@Qualifier("mysqlDataSource") javax.sql.DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "mysqlSqlSessionFactory")
    public SqlSessionFactory mysqlSqlSessionFactory(@Qualifier("mysqlDataSource") javax.sql.DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(RESOURCES));
        return sessionFactory.getObject();
    }

    @Bean(name = "mysqlSqlSessionTemplate")
    public SqlSessionTemplate mysqlSqlSessionTemplate(@Qualifier("mysqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
