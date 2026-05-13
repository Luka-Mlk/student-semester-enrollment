package mk.ukim.finki.student_semester_enrollment.handlers.eventHandlers

import mk.ukim.finki.student_semester_enrollment.model.*
import mk.ukim.finki.student_semester_enrollment.model.enums.StudentRecordStatus
import mk.ukim.finki.student_semester_enrollment.service.StudentRecordViewReadService
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class StudentRecordIncomingEventHandler(
        private val commandGateway: CommandGateway,
        private val studentRecordViewReadService: StudentRecordViewReadService
) {


    @EventHandler
    fun handle(event: EnrollmentConfirmedEvent) {
        val records = studentRecordViewReadService
                .findByStudentId(event.studentId)
                .filter { it.status == StudentRecordStatus.ACTIVE }

        records.forEach { record ->
            commandGateway.sendAndWait<Void>(
                    IncrementCompletedSemestersCommand(recordId = record.id)
            )

            val newTotalEcts = TotalEctsEarned(
                    (record.totalEctsEarned.value + record.totalEctsEarned.value)
                            .coerceAtMost(300)
            )
            commandGateway.sendAndWait<Void>(
                    UpdateTotalEctsEarnedCommand(
                            recordId = record.id,
                            totalEctsEarned = newTotalEcts
                    )
            )
        }
    }
}