package mk.ukim.finki.student_semester_enrollment.service.impl

import mk.ukim.finki.student_semester_enrollment.model.SemesterCode
import mk.ukim.finki.student_semester_enrollment.model.SemesterId
import mk.ukim.finki.student_semester_enrollment.model.SemesterView
import mk.ukim.finki.student_semester_enrollment.model.enums.SemesterStatus
import mk.ukim.finki.student_semester_enrollment.model.exceptions.SemesterNotFoundException
import mk.ukim.finki.student_semester_enrollment.repository.SemesterViewJpaRepository
import mk.ukim.finki.student_semester_enrollment.service.SemesterViewReadService
import org.springframework.stereotype.Service

@Service
class SemesterViewReadServiceImpl(
    private val repository: SemesterViewJpaRepository
) : SemesterViewReadService {

    override fun findAll(): List<SemesterView> =
        repository.findAll()

    override fun findById(id: SemesterId): SemesterView =
        repository.findById(id).orElseThrow { SemesterNotFoundException(id) }

    override fun findByStatus(status: SemesterStatus): List<SemesterView> =
        repository.findByStatus(status)

    override fun findBySemesterCode(semesterCode: SemesterCode): List<SemesterView> =
        repository.findBySemesterCode(semesterCode)
}