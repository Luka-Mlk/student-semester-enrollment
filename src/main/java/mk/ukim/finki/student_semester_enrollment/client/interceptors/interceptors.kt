package mk.ukim.finki.student_semester_enrollment.client.interceptors

import feign.RequestInterceptor
import feign.RequestTemplate
import java.util.UUID

class CorrelationIdInterceptor : RequestInterceptor {

    override fun apply(template: RequestTemplate) {
        if (!template.headers().containsKey("X-Correlation-ID")) {
            val correlationId = UUID.randomUUID().toString()
            template.header("X-Correlation-ID", correlationId)
        }
    }
}