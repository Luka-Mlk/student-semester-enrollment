package mk.ukim.finki.student_semester_enrollment.infrastructure.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import mk.ukim.finki.student_semester_enrollment.model.StudentGraduatedExternalEvent
import mk.ukim.finki.student_semester_enrollment.model.StudentExpelledExternalEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) {

    private val logger = LoggerFactory.getLogger(KafkaEventProducer::class.java)

    fun publishStudentGraduated(event: StudentGraduatedExternalEvent) {
        val payload = objectMapper.writeValueAsString(event)
        kafkaTemplate.send("student.graduated", payload)
        logger.info("Published student.graduated: {}", payload)
    }

    fun publishStudentExpelled(event: StudentExpelledExternalEvent) {
        val payload = objectMapper.writeValueAsString(event)
        kafkaTemplate.send("student.expelled", payload)
        logger.info("Published student.expelled: {}", payload)
    }
}