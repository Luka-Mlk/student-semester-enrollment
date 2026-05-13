package mk.ukim.finki.student_semester_enrollment.repository

import mk.ukim.finki.student_semester_enrollment.model.SemesterCode
import mk.ukim.finki.student_semester_enrollment.model.SemesterId
import mk.ukim.finki.student_semester_enrollment.model.SemesterView
import mk.ukim.finki.student_semester_enrollment.model.enums.SemesterStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SemesterViewJpaRepository : JpaRepository<SemesterView, SemesterId> {
    fun findByStatus(status: SemesterStatus): List<SemesterView>
    fun findBySemesterCode(semesterCode: SemesterCode): List<SemesterView>
}