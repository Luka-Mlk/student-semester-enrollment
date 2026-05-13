package mk.ukim.finki.student_semester_enrollment.model

import jakarta.persistence.*
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Entity
@Table(name = "student_subject_enrollment")
@Aggregate(repository = "axonStudentSubjectEnrollmentRepository")
class StudentSubjectEnrollment {

    @AggregateIdentifier
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    lateinit var id: StudentSubjectEnrollmentId

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "enrollment_id"))
    lateinit var enrollmentId: StudentSemesterEnrollmentId

    // ✅ FIX: reference Subject instead of duplicating data
    @Column(name = "subject_id", nullable = false)
    var subjectId: Long = 0

    @Column(name = "dependencies_validated", nullable = false)
    var dependenciesValidated: Boolean = false

    constructor()

    // ✅ CREATE
    @CommandHandler
    constructor(command: CreateStudentSubjectEnrollmentCommand) {
        val event = StudentSubjectEnrollmentCreatedEvent(command)
        AggregateLifecycle.apply(event)
    }

    @EventSourcingHandler
    fun on(event: StudentSubjectEnrollmentCreatedEvent) {
        this.id = event.subjectEnrollmentId
        this.enrollmentId = event.enrollmentId
        this.subjectId = event.subjectId
        this.dependenciesValidated = false
    }

    // ✅ VALIDATE dependencies (optional business logic)
    @CommandHandler
    fun handle(command: ValidateSubjectDependenciesCommand) {
        val event = SubjectDependenciesValidatedEvent(command)
        AggregateLifecycle.apply(event)
    }

    @EventSourcingHandler
    fun on(event: SubjectDependenciesValidatedEvent) {
        this.dependenciesValidated = true
    }
}