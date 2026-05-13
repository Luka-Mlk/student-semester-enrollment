package mk.ukim.finki.student_semester_enrollment.handlers.eventHandlers

import mk.ukim.finki.student_semester_enrollment.infrastructure.kafka.KafkaEventProducer
import mk.ukim.finki.student_semester_enrollment.model.StudentGraduatedEvent
import mk.ukim.finki.student_semester_enrollment.model.StudentGraduatedExternalEvent
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class StudentRecordEventHandler(
    private val kafkaProducer: KafkaEventProducer
) {

    @EventHandler
    fun on(event: StudentGraduatedEvent) {

        val externalEvent = StudentGraduatedExternalEvent(
            recordId = event.recordId.toString()
        )

        kafkaProducer.publishStudentGraduated(externalEvent)
    }
}