package mk.ukim.finki.student_semester_enrollment.repository

import mk.ukim.finki.student_semester_enrollment.model.StudentId
import mk.ukim.finki.student_semester_enrollment.model.StudentRecordId
import mk.ukim.finki.student_semester_enrollment.model.StudentRecordView
import mk.ukim.finki.student_semester_enrollment.model.enums.StudentRecordStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentRecordViewJpaRepository : JpaRepository<StudentRecordView, StudentRecordId> {

    fun findByStatus(status: StudentRecordStatus): List<StudentRecordView>

    fun findByStudentId(studentId: StudentId): List<StudentRecordView>

    fun existsByStudentId(studentId: StudentId): Boolean
}