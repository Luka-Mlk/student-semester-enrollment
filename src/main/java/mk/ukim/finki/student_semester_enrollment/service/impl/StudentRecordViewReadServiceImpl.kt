package mk.ukim.finki.student_semester_enrollment.service.impl

import mk.ukim.finki.student_semester_enrollment.model.StudentId
import mk.ukim.finki.student_semester_enrollment.model.StudentRecordId
import mk.ukim.finki.student_semester_enrollment.model.StudentRecordView
import mk.ukim.finki.student_semester_enrollment.model.enums.StudentRecordStatus
import mk.ukim.finki.student_semester_enrollment.model.exceptions.StudentRecordNotFoundException
import mk.ukim.finki.student_semester_enrollment.repository.StudentRecordViewJpaRepository
import mk.ukim.finki.student_semester_enrollment.service.StudentRecordViewReadService
import org.springframework.stereotype.Service

@Service
class StudentRecordViewReadServiceImpl(
    private val repository: StudentRecordViewJpaRepository
) : StudentRecordViewReadService {

    override fun findAll(): List<StudentRecordView> =
        repository.findAll()

    override fun findById(id: StudentRecordId): StudentRecordView =
        repository.findById(id).orElseThrow { StudentRecordNotFoundException(id) }

    override fun findByStatus(status: StudentRecordStatus): List<StudentRecordView> =
        repository.findByStatus(status)

    override fun findByStudentId(studentId: StudentId): List<StudentRecordView> =
        repository.findByStudentId(studentId)

    override fun existsByStudentId(studentId: StudentId): Boolean =
        repository.existsByStudentId(studentId)
}
