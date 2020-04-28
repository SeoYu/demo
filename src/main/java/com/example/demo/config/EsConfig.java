package com.example.demo.config;

import com.example.demo.config.entity.EsEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vincent
 * @description Es配置
 * @Date 2019-11-01 16:53
 */
@Configuration
public class EsConfig {
//
//    @Value("${elasticsearch.nodes}")
//    private List<String> nodes;
//
//    @Value("${elasticsearch.schema}")
//    private String schema;
//
//    @Value("${elasticsearch.max-connect-total}")
//    private Integer maxConnectTotal;
//
//    @Value("${elasticsearch.max-connect-per-route}")
//    private Integer maxConnectPerRoute;
//
//    @Value("${elasticsearch.connection-request-timeout-millis}")
//    private Integer connectionRequestTimeoutMillis;
//
//    @Value("${elasticsearch.socket-timeout-millis}")
//    private Integer socketTimeoutMillis;
//
//    @Value("${elasticsearch.connect-timeout-millis}")
//    private Integer connectTimeoutMillis;

    @Autowired
    EsEntity esEntity;

    @Bean
    public RestHighLevelClient client() {

        List<HttpHost> httpHosts = new ArrayList<>();

        for (String node : esEntity.getNodes()) {
            try {
                String[] parts = StringUtils.split(node, ":");
                Assert.notNull(parts,"Must defined");
                Assert.state(parts.length == 2, "Must be defined as 'host:port'");
                httpHosts.add(new HttpHost(parts[0], Integer.parseInt(parts[1]), esEntity.getSchema()));
            } catch (RuntimeException ex) {
                throw new IllegalStateException(
                        "Invalid ES nodes " + "property '" + node + "'", ex);
            }
        }

        return EsClientBuilder.build(httpHosts)
                .setConnectionRequestTimeoutMillis(esEntity.getConnectionRequestTimeoutMillis())
                .setConnectTimeoutMillis(esEntity.getConnectTimeoutMillis())
                .setSocketTimeoutMillis(esEntity.getSocketTimeoutMillis())
                .setMaxConnectTotal(esEntity.getMaxConnectTotal())
                .setMaxConnectPerRoute(esEntity.getMaxConnectPerRoute())
                .create();
    }

    @Bean
    public RestClient restClient(){
        List<HttpHost> httpHosts = new ArrayList<>();

        for (String node : esEntity.getNodes()) {
            try {
                String[] parts = StringUtils.split(node, ":");
                Assert.notNull(parts,"Must defined");
                Assert.state(parts.length == 2, "Must be defined as 'host:port'");
                httpHosts.add(new HttpHost(parts[0], Integer.parseInt(parts[1]), esEntity.getSchema()));
            } catch (RuntimeException ex) {
                throw new IllegalStateException(
                        "Invalid ES nodes " + "property '" + node + "'", ex);
            }
        }

        HttpHost[] array = new HttpHost[httpHosts.size()];
        httpHosts.toArray(array);

        RestClient restClient = RestClient.builder(array).build();

        return restClient;
    }
}
