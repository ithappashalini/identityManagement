package com.rheal.security.idm.commons.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author Prashanth Errabelli
 * @date Dec 26, 2019 10:01:43 PM 
 *
 */
@Configuration
public class HttpClientConfig {

    @Value("${http.pool.config.max_total}")
    private int httpPoolConfigMaxTotal;

    @Value("${http.pool.config.max_per_route}")
    private int httpPoolConfigMaxPerRoute;

    @Value("${http.pool.config.request_timeout}")
    private int connectionRequestTimeout;

    @Value("${http.pool.config.socket_timeout}")
    private int connectionSocketTimeout;

    @Value("${http.pool.config.connection_timeout}")
    private int connectionTimeout;

    @Bean
    public PoolingHttpClientConnectionManager httpPoolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(httpPoolConfigMaxTotal);
        connectionManager.setDefaultMaxPerRoute(httpPoolConfigMaxPerRoute);
        return connectionManager;
    }

    @Bean
    public RequestConfig httpDefaultRequestConfig() {
        return RequestConfig.custom().setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectionTimeout).setSocketTimeout(connectionSocketTimeout).build();
    }

}
