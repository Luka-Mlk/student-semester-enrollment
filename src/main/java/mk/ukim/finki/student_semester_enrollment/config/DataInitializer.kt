package mk.ukim.finki.student_semester_enrollment.config

import mk.ukim.finki.student_semester_enrollment.model.CreateStudentRecordCommand
import mk.ukim.finki.student_semester_enrollment.model.StudentId
import mk.ukim.finki.student_semester_enrollment.model.StudentRecordId
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.UUID
import kotlin.random.Random

@Configuration
class DataInitializer {

    @Bean
    fun init(commandGateway: CommandGateway) = CommandLineRunner {

        try {
            val studentId = StudentId(UUID.randomUUID())
            val recordId = StudentRecordId(UUID.randomUUID())
            val studentIndex = generateStudentIndex()

            commandGateway.sendAndWait<Any>(
                CreateStudentRecordCommand(
                    recordId = recordId,
                    studentId = studentId,
                    studentIndex = studentIndex
                )
            )

            println("StudentRecord created:")
            println("Student ID: $studentId")
            println("Student index: $studentIndex")

        } catch (ex: Exception) {
            println("Init failed: ${ex.message}")
        }
    }

    private fun generateStudentIndex(): String {
        val yearPrefix = Random.nextInt(22, 26)
        val randomPart = Random.nextInt(0, 10000).toString().padStart(4, '0')

        return "$yearPrefix$randomPart"
    }
}