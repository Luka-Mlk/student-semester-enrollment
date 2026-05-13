package mk.ukim.finki.student_semester_enrollment.model

import mk.ukim.finki.student_semester_enrollment.model.enums.StudentRecordStatus
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class StudentRecord : LabeledEntity {

    @AggregateIdentifier
    lateinit var id: StudentRecordId

    lateinit var studentId: StudentId

    lateinit var studentIndex: String

    lateinit var status: StudentRecordStatus

    var gpa: StudentGpa = StudentGpa(6.0)

    var totalEctsEarned: TotalEctsEarned = TotalEctsEarned(0)

    var completedSemesters: CompletedSemesters = CompletedSemesters(0)

    constructor()

    // CREATE
    @CommandHandler
    constructor(command: CreateStudentRecordCommand) {
        require(command.studentIndex.matches(Regex("^(22|23|24|25)\\d{4}$"))) {
            "Student index must be a 6-digit number starting with 22, 23, 24, or 25"
        }

        AggregateLifecycle.apply(
            StudentRecordCreatedEvent(
                recordId = command.recordId,
                studentId = command.studentId,
                studentIndex = command.studentIndex
            )
        )
    }

    @EventSourcingHandler
    fun on(event: StudentRecordCreatedEvent) {
        this.id = event.recordId
        this.studentId = event.studentId
        this.studentIndex = event.studentIndex
        this.status = StudentRecordStatus.ACTIVE
        this.gpa = StudentGpa(6.0)
        this.totalEctsEarned = TotalEctsEarned(0)
        this.completedSemesters = CompletedSemesters(0)
    }

    // GPA
    @CommandHandler
    fun handle(command: UpdateStudentGpaCommand) {
        check(status == StudentRecordStatus.ACTIVE)
        AggregateLifecycle.apply(StudentGpaUpdatedEvent(command))
    }

    @EventSourcingHandler
    fun on(event: StudentGpaUpdatedEvent) {
        this.gpa = event.gpa
    }

    // ECTS
    @CommandHandler
    fun handle(command: UpdateTotalEctsEarnedCommand) {
        check(status == StudentRecordStatus.ACTIVE)
        AggregateLifecycle.apply(TotalEctsEarnedUpdatedEvent(command))
    }

    @EventSourcingHandler
    fun on(event: TotalEctsEarnedUpdatedEvent) {
        this.totalEctsEarned = event.totalEctsEarned
    }

    // SEMESTERS
    @CommandHandler
    fun handle(command: IncrementCompletedSemestersCommand) {
        check(status == StudentRecordStatus.ACTIVE)

        val newCount = CompletedSemesters(this.completedSemesters.value + 1)

        AggregateLifecycle.apply(
            CompletedSemestersIncrementedEvent(
                recordId = this.id,
                newCount = newCount
            )
        )
    }

    @EventSourcingHandler
    fun on(event: CompletedSemestersIncrementedEvent) {
        this.completedSemesters = event.newCount
    }

    // SUSPEND
    @CommandHandler
    fun handle(command: SuspendStudentRecordCommand) {
        check(status == StudentRecordStatus.ACTIVE)
        AggregateLifecycle.apply(StudentRecordSuspendedEvent(command))
    }

    @EventSourcingHandler
    fun on(event: StudentRecordSuspendedEvent) {
        this.status = StudentRecordStatus.SUSPENDED
    }

    // GRADUATE
    @CommandHandler
    fun handle(command: GraduateStudentCommand) {
        check(status != StudentRecordStatus.EXPELLED)
        check(status != StudentRecordStatus.GRADUATED)

        AggregateLifecycle.apply(StudentGraduatedEvent(command))
    }

    @EventSourcingHandler
    fun on(event: StudentGraduatedEvent) {
        this.status = StudentRecordStatus.GRADUATED
        println("GRADUATION EVENT RECEIVED: " + event.recordId)
    }

    // EXPEL
    @CommandHandler
    fun handle(command: ExpelStudentCommand) {
        check(status != StudentRecordStatus.GRADUATED)
        check(status != StudentRecordStatus.EXPELLED)

        AggregateLifecycle.apply(StudentExpelledEvent(command))
    }

    @EventSourcingHandler
    fun on(event: StudentExpelledEvent) {
        this.status = StudentRecordStatus.EXPELLED
    }

    override fun getId(): Identifier<out Any> = id

    override fun getLabel(): String = "StudentRecord - $studentIndex"
}