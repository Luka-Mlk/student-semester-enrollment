package mk.ukim.finki.student_semester_enrollment.service.impl

import mk.ukim.finki.student_semester_enrollment.model.SemesterId
import mk.ukim.finki.student_semester_enrollment.model.StudentId
import mk.ukim.finki.student_semester_enrollment.model.StudentSemesterEnrollmentId
import mk.ukim.finki.student_semester_enrollment.model.enums.EnrollmentStatus
import mk.ukim.finki.student_semester_enrollment.model.exceptions.StudentSemesterEnrollmentNotFoundException
import mk.ukim.finki.student_semester_enrollment.repository.StudentSemesterEnrollmentViewJpaRepository
import mk.ukim.finki.student_semester_enrollment.service.StudentSemesterEnrollmentViewReadService
//import mk.ukim.finki.student_semester_enrollment.service.enrollmentServices
import org.springframework.stereotype.Service

@Service
class StudentSemesterEnrollmentViewReadServiceImpl(
    private val repository: StudentSemesterEnrollmentViewJpaRepository
) : StudentSemesterEnrollmentViewReadService {

    override fun findAll() = repository.findAll()

    override fun findById(id: StudentSemesterEnrollmentId) =
        repository.findById(id).orElseThrow {
            StudentSemesterEnrollmentNotFoundException(id)
        }

    override fun findByStatus(status: EnrollmentStatus) =
        repository.findByStatus(status)

    override fun findByStudentId(studentId: StudentId) =
        repository.findByStudentId(studentId)

    override fun findBySemesterId(semesterId: SemesterId) =
        repository.findBySemesterId(semesterId)
}