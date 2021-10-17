package com.kyc.accountservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertyConfig {

    public static final String FILE_DOWNLOAD_CONTROLLER_PATH = "/download/";

    public static int VERIFY_USER_EXECUTOR_CORE_POOL_SIZE = 5;
    public static int VERIFY_USER_EXECUTOR_MAX_POOL_SIZE = 10;
    public static int VERIFY_USER_EXECUTOR_QUEUE_CAPACITY = 100;

    public static int FILE_STORE_EXECUTOR_CORE_POOL_SIZE = 5;
    public static int FILE_STORE_EXECUTOR_MAX_POOL_SIZE = 10;
    public static int FILE_STORE_EXECUTOR_QUEUE_CAPACITY = 100;

    public static int VERIFICATION_TIMEOUT_SECS = 3;
    public static String VERIFICATION_URL = "https://jsonplaceholder.typicode.com/users";

    @Value("${verifyUserExecutor.core-pool-size}")
    public void setVerifyUserCorePoolSize(int corePoolSize){
        VERIFY_USER_EXECUTOR_CORE_POOL_SIZE = corePoolSize;
    }

    @Value("${verifyUserExecutor.max-pool-size}")
    public void setVerifyUserMaxPoolSize(int maxPoolSize){
        VERIFY_USER_EXECUTOR_MAX_POOL_SIZE = maxPoolSize;
    }

    @Value("${verifyUserExecutor.queue-capacity}")
    public void setVerifyUserQueueCapacity(int queueCapacity){
        VERIFY_USER_EXECUTOR_QUEUE_CAPACITY = queueCapacity;
    }

    @Value("${fileStoreExecutor.core-pool-size}")
    public void setFileStoreCorePoolSize(int corePoolSize){
        FILE_STORE_EXECUTOR_CORE_POOL_SIZE = corePoolSize;
    }

    @Value("${fileStoreExecutor.max-pool-size}")
    public void setFileStoreMaxPoolSize(int maxPoolSize){
        FILE_STORE_EXECUTOR_MAX_POOL_SIZE = maxPoolSize;
    }

    @Value("${fileStoreExecutor.queue-capacity}")
    public void setFileStoreQueueCapacity(int queueCapacity){
        FILE_STORE_EXECUTOR_QUEUE_CAPACITY = queueCapacity;
    }

    @Value("${thirdparty.verification.timeout-seconds}")
    public void setVerificationTimeoutSecs(int verificationTimeoutSecs){
        VERIFICATION_TIMEOUT_SECS = verificationTimeoutSecs;
    }

    @Value("${thirdparty.verification.url}")
    public void setVerificationUrl(String verificationUrl){
        VERIFICATION_URL = verificationUrl;
    }
}
