package mk.ukim.finki.student_semester_enrollment.infrastructure.kafka

import mk.ukim.finki.student_semester_enrollment.infrastructure.kafka.dto.IncomingSubjectCreatedEventDTO
import mk.ukim.finki.student_semester_enrollment.model.CreateStudentSubjectEnrollmentCommand
import mk.ukim.finki.student_semester_enrollment.model.StudentSemesterEnrollmentId
import mk.ukim.finki.student_semester_enrollment.model.StudentSubjectEnrollmentId
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class IncomingEventTranslator {

    fun toCreateSubjectCommand(
        event: IncomingSubjectCreatedEventDTO,
        enrollmentId: StudentSemesterEnrollmentId
    ): CreateStudentSubjectEnrollmentCommand {

        return CreateStudentSubjectEnrollmentCommand(
            subjectEnrollmentId = StudentSubjectEnrollmentId(UUID.randomUUID().toString()),
            enrollmentId = enrollmentId,
            subjectId = event.subjectId.value
        )
    }
}