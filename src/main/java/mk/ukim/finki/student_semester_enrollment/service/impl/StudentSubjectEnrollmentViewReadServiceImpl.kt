package mk.ukim.finki.student_semester_enrollment.service.impl

import mk.ukim.finki.student_semester_enrollment.model.StudentSemesterEnrollmentId
import mk.ukim.finki.student_semester_enrollment.model.StudentSubjectEnrollmentId
import mk.ukim.finki.student_semester_enrollment.repository.StudentSubjectEnrollmentViewJpaRepository
import mk.ukim.finki.student_semester_enrollment.service.StudentSubjectEnrollmentViewReadService
import org.springframework.stereotype.Service

@Service
class StudentSubjectEnrollmentViewReadServiceImpl(
        private val repository: StudentSubjectEnrollmentViewJpaRepository
) : StudentSubjectEnrollmentViewReadService {

    override fun findAll() = repository.findAll()

    override fun findById(id: StudentSubjectEnrollmentId) =
            repository.findById(id).orElseThrow {
                RuntimeException("StudentSubjectEnrollment not found")
            }

    override fun findByEnrollmentId(enrollmentId: StudentSemesterEnrollmentId) =
            repository.findByEnrollmentId(enrollmentId)
}