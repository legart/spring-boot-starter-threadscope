package devbury.threadscope;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ThreadScopeSchedulerConfiguration {
    @Bean
    @ConditionalOnMissingBean(AsyncUncaughtExceptionHandler.class)
    public AsyncUncaughtExceptionHandler defaultAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(ThreadScopePropagatingScheduler.class)
    public ThreadScopePropagatingScheduler defaultThreadScopePropagatingScheduler(final ThreadScopeManager threadScopeManager, final ThreadScopeProperties threadScopeProperties) {
        ThreadScopePropagatingScheduler threadScopePropagatingScheduler = new ThreadScopePropagatingScheduler
                (threadScopeManager);
        threadScopePropagatingScheduler.setPoolSize(threadScopeProperties.getPoolSize());
        threadScopePropagatingScheduler.setThreadNamePrefix(threadScopeProperties.getThreadNamePrefix());
        return threadScopePropagatingScheduler;
    }
}
