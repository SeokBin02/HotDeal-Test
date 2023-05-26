package challenge18.hotdeal.common.config.rds;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;
@RequiredArgsConstructor
@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class DataSourceConfig {
    private final DataSourceKey dataSourceKey;

    // mater database DataSource
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.master.hikari")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    // slave database DataSource
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.slave.hikari")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public DataSource routingDataSource(DataSource masterDataSource, DataSource slaveDataSource){
        RoutingDatasource routingDataSource = new RoutingDatasource(dataSourceKey);

        Map<Object, Object> source = Map.of(
                dataSourceKey.getMasterKey(), masterDataSource,
                dataSourceKey.getDefaultSlaveKey(), slaveDataSource
        );

        routingDataSource.setTargetDataSources(source);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);

        return routingDataSource;
    }

    // setting lazy connection
    @Bean
    @Primary
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(routingDataSource(masterDataSource(), slaveDataSource()));
    }
}
