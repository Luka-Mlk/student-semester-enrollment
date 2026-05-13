package mk.ukim.finki.student_semester_enrollment.config

import feign.RequestInterceptor
import mk.ukim.finki.student_semester_enrollment.client.interceptors.CorrelationIdInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignConfig {

    @Bean
    fun correlationIdInterceptor(): RequestInterceptor {
        return CorrelationIdInterceptor()
    }
}