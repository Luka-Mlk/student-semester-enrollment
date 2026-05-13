package mk.ukim.finki.student_semester_enrollment.model

import mk.ukim.finki.student_semester_enrollment.model.enums.EnrollmentStatus
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class StudentSemesterEnrollment : LabeledEntity {

    @AggregateIdentifier
    lateinit var id: StudentSemesterEnrollmentId

    lateinit var studentId: StudentId
    lateinit var semesterId: SemesterId
    lateinit var status: EnrollmentStatus

    var totalEcts: EnrollmentEcts = EnrollmentEcts(0)
    var subjectIds: MutableList<StudentSubjectEnrollmentId> = mutableListOf()

    constructor()

    // ✅ CREATE
    @CommandHandler
    constructor(command: CreateStudentSemesterEnrollmentCommand) {
        AggregateLifecycle.apply(
            StudentSemesterEnrollmentCreatedEvent(command)
        )
    }

    @EventSourcingHandler
    fun on(event: StudentSemesterEnrollmentCreatedEvent) {
        this.id = event.enrollmentId
        this.studentId = event.studentId
        this.semesterId = event.semesterId
        this.status = EnrollmentStatus.DRAFT
    }

    // ✅ ADD SUBJECT
    @CommandHandler
    fun handle(command: AddSubjectToEnrollmentCommand) {
        AggregateLifecycle.apply(
            SubjectAddedToEnrollmentEvent(command)
        )
    }

    @EventSourcingHandler
    fun on(event: SubjectAddedToEnrollmentEvent) {
        this.subjectIds.add(event.subjectId)
        this.totalEcts = EnrollmentEcts(this.totalEcts.value + event.ects)
    }

    // REMOVE
    @CommandHandler
    fun handle(command: RemoveSubjectFromEnrollmentCommand) {
        AggregateLifecycle.apply(
            SubjectRemovedFromEnrollmentEvent(command)
        )
    }

    @EventSourcingHandler
    fun on(event: SubjectRemovedFromEnrollmentEvent) {
        this.subjectIds.remove(event.subjectId)
        this.totalEcts = EnrollmentEcts(this.totalEcts.value - event.ects)
    }

    // CONFIRM
    @CommandHandler
    fun handle(command: ConfirmEnrollmentCommand) {
        check(status != EnrollmentStatus.REJECTED)
        check(totalEcts.value >= 21)

        AggregateLifecycle.apply(
            EnrollmentConfirmedEvent(command, studentId, semesterId)
        )
    }

    @EventSourcingHandler
    fun on(event: EnrollmentConfirmedEvent) {
        this.status = EnrollmentStatus.CONFIRMED
    }

    override fun getId(): Identifier<out Any> = id
    override fun getLabel(): String = "$studentId - $semesterId"
}