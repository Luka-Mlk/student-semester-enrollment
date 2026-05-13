package mk.ukim.finki.student_semester_enrollment.service.command

import mk.ukim.finki.student_semester_enrollment.client.StudentManagementClient
import mk.ukim.finki.student_semester_enrollment.client.SubjectManagementClient
import mk.ukim.finki.student_semester_enrollment.exception.StudentNotFoundException
import mk.ukim.finki.student_semester_enrollment.exception.SubjectNotFoundException
import mk.ukim.finki.student_semester_enrollment.model.AddSubjectToEnrollmentCommand
import mk.ukim.finki.student_semester_enrollment.model.CreateStudentSemesterEnrollmentCommand
import mk.ukim.finki.student_semester_enrollment.model.SemesterId
import mk.ukim.finki.student_semester_enrollment.model.StudentId
import mk.ukim.finki.student_semester_enrollment.model.StudentSemesterEnrollmentId
import mk.ukim.finki.student_semester_enrollment.model.StudentSubjectEnrollmentId
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.stereotype.Service

@Service
class StudentSemesterEnrollmentCommandService(
    private val commandGateway: CommandGateway,
    private val studentClient: StudentManagementClient,
    private val subjectClient: SubjectManagementClient
) {

    fun createEnrollment(
        enrollmentId: StudentSemesterEnrollmentId,
        studentId: StudentId,
        semesterId: SemesterId
    ) {
        val studentExists = try {
            studentClient.existsStudent(studentId.value.toString())
        } catch (ex: Exception) {
            false
        }

        if (!studentExists) {
            throw StudentNotFoundException(studentId.value.toString())
        }

        commandGateway.sendAndWait<Any>(
            CreateStudentSemesterEnrollmentCommand(
                enrollmentId,
                studentId,
                semesterId
            )
        )
    }

    fun addSubject(
        enrollmentId: StudentSemesterEnrollmentId,
        subjectId: StudentSubjectEnrollmentId,
        ects: Int
    ) {
        val externalSubjectId = subjectId.value.toString()

        val subjectExists = try {
            subjectClient.getSubjectById(externalSubjectId)
            true
        } catch (ex: Exception) {
            false
        }

        if (!subjectExists) {
            throw SubjectNotFoundException(externalSubjectId)
        }

        commandGateway.sendAndWait<Any>(
            AddSubjectToEnrollmentCommand(
                enrollmentId,
                subjectId,
                ects
            )
        )
    }
}