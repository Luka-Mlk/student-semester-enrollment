package mk.ukim.finki.student_semester_enrollment.infrastructure.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.github.springwolf.core.asyncapi.annotations.AsyncListener
import io.github.springwolf.core.asyncapi.annotations.AsyncOperation
import mk.ukim.finki.student_semester_enrollment.infrastructure.kafka.dto.IncomingGraduateStudentDTO
import mk.ukim.finki.student_semester_enrollment.model.GraduateStudentCommand
import mk.ukim.finki.student_semester_enrollment.model.StudentRecordId
import mk.ukim.finki.student_semester_enrollment.service.StudentSemesterEnrollmentViewReadService
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.axonframework.commandhandling.gateway.CommandGateway
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class KafkaEventConsumer(
    private val translator: IncomingEventTranslator,
    private val commandGateway: CommandGateway,
    private val enrollmentReadService: StudentSemesterEnrollmentViewReadService
) {

    private val logger = LoggerFactory.getLogger(KafkaEventConsumer::class.java)

    private val objectMapper = ObjectMapper()
        .registerModules(KotlinModule.Builder().build())
        .registerModule(JavaTimeModule())

    @AsyncListener(
        operation = AsyncOperation(
            channelName = "student.graduate.command",
            description = "Consumes student graduate commands from Kafka"
        )
    )
    @KafkaListener(topics = ["student.graduate.command"])
    fun listenGraduate(record: ConsumerRecord<String, String>) {
        try {
            val dto = objectMapper.readValue(
                record.value(),
                IncomingGraduateStudentDTO::class.java
            )

            val command = GraduateStudentCommand(
                StudentRecordId(UUID.fromString(dto.recordId))
            )

            commandGateway.sendAndWait<Any>(command)

            logger.info("Processed graduate command from Kafka: {}", command)

        } catch (ex: Exception) {
            logger.error("Failed to process graduate command: {}", record.value(), ex)
        }
    }
}