package mk.ukim.finki.student_semester_enrollment.projection

import mk.ukim.finki.student_semester_enrollment.model.CompletedSemesters
import mk.ukim.finki.student_semester_enrollment.model.StudentExpelledEvent
import mk.ukim.finki.student_semester_enrollment.model.StudentGpa
import mk.ukim.finki.student_semester_enrollment.model.StudentGraduatedEvent
import mk.ukim.finki.student_semester_enrollment.model.StudentRecordCreatedEvent
import mk.ukim.finki.student_semester_enrollment.model.StudentRecordView
import mk.ukim.finki.student_semester_enrollment.model.TotalEctsEarned
import mk.ukim.finki.student_semester_enrollment.model.enums.StudentRecordStatus
import mk.ukim.finki.student_semester_enrollment.repository.StudentRecordViewJpaRepository
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class StudentRecordProjection(
    private val repository: StudentRecordViewJpaRepository
) {

    @EventHandler
    fun on(event: StudentRecordCreatedEvent) {
        val view = StudentRecordView().apply {
            id = event.recordId
            studentId = event.studentId
            studentIndex = event.studentIndex
            status = StudentRecordStatus.ACTIVE
            gpa = StudentGpa(6.0)
            totalEctsEarned = TotalEctsEarned(0)
            completedSemesters = CompletedSemesters(0)
        }

        repository.save(view)
    }

    @EventHandler
    fun on(event: StudentGraduatedEvent) {
        val record = repository.findById(event.recordId)
            .orElseThrow { RuntimeException("Record not found with id: ${event.recordId}") }

        record.status = StudentRecordStatus.GRADUATED
        repository.save(record)
    }

    @EventHandler
    fun on(event: StudentExpelledEvent) {
        val record = repository.findById(event.recordId)
            .orElseThrow { RuntimeException("Record not found with id: ${event.recordId}") }

        record.status = StudentRecordStatus.EXPELLED
        repository.save(record)
    }
}