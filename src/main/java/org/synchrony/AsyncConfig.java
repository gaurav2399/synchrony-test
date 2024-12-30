package org.synchrony;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "synchronyTaskExecutor") // Give it a specific name
    public Executor taskExecutor() {
        System.out.println("Take custom executor");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3); // Minimum number of threads
        executor.setMaxPoolSize(5); // Maximum number of threads
        executor.setThreadNamePrefix("SynchronyThread-");
        executor.initialize();
        return executor;
    }

}
