package mk.ukim.finki.student_semester_enrollment.model

import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import mk.ukim.finki.student_semester_enrollment.model.enums.SemesterStatus
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Entity
@Table(name = "semester")
@Aggregate(repository = "axonSemesterRepository")
class Semester : LabeledEntity {

    @AggregateIdentifier
    @EmbeddedId
    @AttributeOverride(name = "value", column = Column(name = "id"))
    lateinit var id: SemesterId

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "semester_code"))
    lateinit var semesterCode: SemesterCode

    @Embedded
    var period: SemesterPeriod = SemesterPeriod()

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "capacity"))
    var capacity: SemesterCapacity = SemesterCapacity(30)

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    lateinit var status: SemesterStatus

    constructor()

    @CommandHandler
    constructor(command: CreateSemesterCommand) {
        AggregateLifecycle.apply(SemesterCreatedEvent(command))
    }

    @EventSourcingHandler
    fun on(event: SemesterCreatedEvent) {
        this.id = event.semesterId
        this.semesterCode = event.semesterCode
        this.period = event.period
        this.capacity = event.capacity
        this.status = SemesterStatus.CREATED
    }

    @CommandHandler
    fun handle(command: UpdateSemesterCodeCommand) {
        check(status != SemesterStatus.APPROVED) {
            "Cannot update the code of an approved semester"
        }
        AggregateLifecycle.apply(SemesterCodeUpdatedEvent(command))
    }

    @EventSourcingHandler
    fun on(event: SemesterCodeUpdatedEvent) {
        this.semesterCode = event.semesterCode
    }

    @CommandHandler
    fun handle(command: UpdateSemesterPeriodCommand) {
        check(status != SemesterStatus.APPROVED) {
            "Cannot update the period of an approved semester"
        }
        AggregateLifecycle.apply(SemesterPeriodUpdatedEvent(command))
    }

    @EventSourcingHandler
    fun on(event: SemesterPeriodUpdatedEvent) {
        this.period = event.period
    }

    @CommandHandler
    fun handle(command: UpdateSemesterCapacityCommand) {
        check(status != SemesterStatus.APPROVED) {
            "Cannot update the capacity of an approved semester"
        }
        AggregateLifecycle.apply(SemesterCapacityUpdatedEvent(command))
    }

    @EventSourcingHandler
    fun on(event: SemesterCapacityUpdatedEvent) {
        this.capacity = event.capacity
    }

    @CommandHandler
    fun handle(command: ApproveSemesterCommand) {
        check(status != SemesterStatus.REJECTED) {
            "Cannot approve a rejected semester"
        }
        check(status != SemesterStatus.APPROVED) {
            "Semester is already approved"
        }
        AggregateLifecycle.apply(SemesterApprovedEvent(command))
    }

    @EventSourcingHandler
    fun on(event: SemesterApprovedEvent) {
        this.status = SemesterStatus.APPROVED
    }

    @CommandHandler
    fun handle(command: RejectSemesterCommand) {
        check(status != SemesterStatus.APPROVED) {
            "Cannot reject an approved semester"
        }
        check(status != SemesterStatus.REJECTED) {
            "Semester is already rejected"
        }
        AggregateLifecycle.apply(SemesterRejectedEvent(command))
    }

    @EventSourcingHandler
    fun on(event: SemesterRejectedEvent) {
        this.status = SemesterStatus.REJECTED
    }

    override fun getId(): Identifier<out Any> = id

    override fun getLabel(): String = "${semesterCode.value} (${status.name})"
}