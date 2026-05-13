package mk.ukim.finki.student_semester_enrollment.handlers.eventHandlers


import mk.ukim.finki.student_semester_enrollment.model.OpenEnrollmentWindowCommand
import mk.ukim.finki.student_semester_enrollment.model.SemesterCreatedEvent
import mk.ukim.finki.student_semester_enrollment.model.SemesterId
import mk.ukim.finki.student_semester_enrollment.model.StudentSemesterEnrollmentId
import mk.ukim.finki.student_semester_enrollment.service.StudentSemesterEnrollmentViewReadService
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class IncomingEventHandler(
    val commandGateway: CommandGateway,
    val readService: StudentSemesterEnrollmentViewReadService,
) {
    @EventHandler
    fun handle(event: SemesterCreatedEvent) {
        val enrollments = readService.findBySemesterId(event.semesterId)

        enrollments.forEach { enrollment ->
            // sendAndWait is used here because each command must complete
            // before the next one starts — ordering matters to avoid
            // partial state where some enrollments are opened and others are not
            commandGateway.sendAndWait<Void>(
                OpenEnrollmentWindowCommand(
                    enrollmentId = enrollment.id,
                    semesterId = event.semesterId
                )
            )
        }
    }
}