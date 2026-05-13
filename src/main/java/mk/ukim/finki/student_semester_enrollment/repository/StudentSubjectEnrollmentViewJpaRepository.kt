package mk.ukim.finki.student_semester_enrollment.repository

import mk.ukim.finki.student_semester_enrollment.model.StudentSemesterEnrollmentId
import mk.ukim.finki.student_semester_enrollment.model.StudentSubjectEnrollmentId
import mk.ukim.finki.student_semester_enrollment.model.StudentSubjectEnrollmentView
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentSubjectEnrollmentViewJpaRepository :
        JpaRepository<StudentSubjectEnrollmentView, StudentSubjectEnrollmentId> {

    fun findByEnrollmentId(enrollmentId: StudentSemesterEnrollmentId): List<StudentSubjectEnrollmentView>
}