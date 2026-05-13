package mk.ukim.finki.student_semester_enrollment.handlers.eventHandlers

import mk.ukim.finki.student_semester_enrollment.model.StudentSubjectEnrollmentCreatedEvent
import mk.ukim.finki.student_semester_enrollment.model.StudentSubjectEnrollmentView
import mk.ukim.finki.student_semester_enrollment.model.SubjectDependenciesValidatedEvent
import mk.ukim.finki.student_semester_enrollment.repository.StudentSubjectEnrollmentViewJpaRepository
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component
@Component
class StudentSubjectEnrollmentEventHandler(
    private val repository: StudentSubjectEnrollmentViewJpaRepository
) {

    @EventHandler
    fun on(event: StudentSubjectEnrollmentCreatedEvent) {
        val view = StudentSubjectEnrollmentView(
            id = event.subjectEnrollmentId,
            enrollmentId = event.enrollmentId,
            subjectId = event.subjectId, // ✅ FIXED
            dependenciesValidated = false
        )

        repository.save(view)
    }

    @EventHandler
    fun on(event: SubjectDependenciesValidatedEvent) {
        val existing = repository.findById(event.subjectEnrollmentId).orElseThrow()

        existing.dependenciesValidated = true

        repository.save(existing)
    }
}