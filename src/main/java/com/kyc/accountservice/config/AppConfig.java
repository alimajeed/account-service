package com.kyc.accountservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

import static com.kyc.accountservice.config.PropertyConfig.*;

@Configuration
@EnableAsync
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class AppConfig {


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean(name = "verifyUserExecutor")
    public Executor verifyUserExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(VERIFY_USER_EXECUTOR_CORE_POOL_SIZE);
        executor.setMaxPoolSize(VERIFY_USER_EXECUTOR_MAX_POOL_SIZE);
        executor.setQueueCapacity(VERIFY_USER_EXECUTOR_QUEUE_CAPACITY);
        executor.setThreadNamePrefix("verifyUserThread-");
        executor.initialize();
        return executor;
    }

    @Bean(name = "fileStoreExecutor")
    public Executor fileStoreExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(FILE_STORE_EXECUTOR_CORE_POOL_SIZE);
        executor.setMaxPoolSize(FILE_STORE_EXECUTOR_MAX_POOL_SIZE);
        executor.setQueueCapacity(FILE_STORE_EXECUTOR_QUEUE_CAPACITY);
        executor.setThreadNamePrefix("fileStoreThread-");
        executor.initialize();
        return executor;
    }
}
