package mk.ukim.finki.student_semester_enrollment.infrastructure.kafka

import io.github.springwolf.bindings.kafka.annotations.KafkaAsyncOperationBinding
import io.github.springwolf.core.asyncapi.annotations.AsyncOperation
import io.github.springwolf.core.asyncapi.annotations.AsyncPublisher
import mk.ukim.finki.student_semester_enrollment.model.StudentGraduatedExternalEvent
import mk.ukim.finki.student_semester_enrollment.model.StudentExpelledExternalEvent
import org.springframework.stereotype.Component

@Component
class KafkaEventDocumentation {

    @AsyncPublisher(
        operation = AsyncOperation(
            channelName = "student.graduated",
            description = "Published when a student is successfully graduated",
            payloadType = StudentGraduatedExternalEvent::class
        )
    )
    @KafkaAsyncOperationBinding
    fun studentGraduated(
        payload: StudentGraduatedExternalEvent
    ) {
        // documentation only
    }

    @AsyncPublisher(
        operation = AsyncOperation(
            channelName = "student.expelled",
            description = "Published when a student is expelled",
            payloadType = StudentExpelledExternalEvent::class
        )
    )
    @KafkaAsyncOperationBinding
    fun studentExpelled(
        payload: StudentExpelledExternalEvent
    ) {
        // documentation only
    }
}