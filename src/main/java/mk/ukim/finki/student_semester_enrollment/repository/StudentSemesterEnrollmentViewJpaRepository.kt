package mk.ukim.finki.student_semester_enrollment.repository

import mk.ukim.finki.student_semester_enrollment.model.enums.EnrollmentStatus
import mk.ukim.finki.student_semester_enrollment.model.SemesterId
import mk.ukim.finki.student_semester_enrollment.model.StudentId
import mk.ukim.finki.student_semester_enrollment.model.StudentSemesterEnrollmentId
import mk.ukim.finki.student_semester_enrollment.model.StudentSemesterEnrollmentView
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentSemesterEnrollmentViewJpaRepository
    : JpaRepository<StudentSemesterEnrollmentView, StudentSemesterEnrollmentId> {

    fun findByStatus(status: EnrollmentStatus): List<StudentSemesterEnrollmentView>
    fun findByStudentId(studentId: StudentId): List<StudentSemesterEnrollmentView>
    fun findBySemesterId(semesterId: SemesterId): List<StudentSemesterEnrollmentView>
}