package mk.ukim.finki.student_semester_enrollment.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.Transient
import java.io.Serializable
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.UUID

//Student Semester Enrollment Common Value Objects and Interfaces
@MappedSuperclass
abstract class Identifier<T>(providedValue: String, @Transient val entityClass: Class<T>) : Serializable {

    open val value = "${entityClass.simpleName}:${providedValue.replace(".*:".toRegex(), "")}"

    override fun hashCode(): Int {
        return this.entityClass.hashCode() + this.value.hashCode()
    }

    @Suppress("unused")
    fun baseValue() = value.split(":")[1]

    @Suppress("unused")
    fun prefixedValue() = value

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Identifier<*>

        if (entityClass != other.entityClass) return false
        if (value != other.value) return false

        return true
    }

    override fun toString(): String = value
}


@Suppress("unused")
interface LabeledEntity {

    @JsonProperty("id")
    fun getId(): Identifier<out Any>

    @JsonProperty("label")
    fun getLabel(): String

    @JsonProperty("entityType")
    fun getEntityType(): String = this.javaClass.simpleName

    @JsonProperty("dateCreated")
    fun dateCreated(): ZonedDateTime? = null

    @JsonProperty("archived")
    fun isArchived(): Boolean = false
}


@Embeddable
data class StudentSemesterEnrollmentId(override val value: String = "") : Identifier<StudentSemesterEnrollmentId>(
    value, StudentSemesterEnrollmentId::class.java
) {
    constructor(uuid: UUID) : this("StudentSemesterEnrollment:$uuid")

    companion object {
        fun generate(): StudentSemesterEnrollmentId = StudentSemesterEnrollmentId(UUID.randomUUID())
    }
}

@Embeddable
data class EnrollmentEcts(val value: Int = 0) {
    init {
        require(value >= 0) { "ECTS cannot be negative" }
        require(value <= 30) { "Enrollment cannot exceed 30 ECTS" }
    }
}

@Embeddable
data class MandatorySubjectCount(val value: Int = 0) {
    init {
        require(value >= 0) { "Mandatory subject count cannot be negative" }
    }
}

@Embeddable
data class StudentId(override val value: String = "") : Identifier<StudentId>(
    value, StudentId::class.java
) {
    constructor(uuid: UUID) : this("Student:$uuid")

    companion object {
        fun generate(): StudentId = StudentId(UUID.randomUUID())
    }
}

@Embeddable
data class SemesterId(override val value: String = "") : Identifier<SemesterId>(
    value, SemesterId::class.java
) {
    constructor(uuid: UUID) : this("Semester:$uuid")

    companion object {
        fun generate(): SemesterId = SemesterId(UUID.randomUUID())
    }
}

@Embeddable
data class StudentSubjectEnrollmentId(override val value: String = "") : Identifier<StudentSubjectEnrollmentId>(
    value, StudentSubjectEnrollmentId::class.java
) {
    constructor(uuid: UUID) : this("StudentSubjectEnrollment:$uuid")

    companion object {
        fun generate(): StudentSubjectEnrollmentId = StudentSubjectEnrollmentId(UUID.randomUUID())
    }
}


//Semester Common Value Objects and Interfaces

@Embeddable
data class SemesterCode(val value: String = "") : Serializable {
    init {
        require(
            value.isBlank() || value.matches(Regex("^(WS|SS)-\\d{4}/\\d{4}$"))
        ) {
            "Semester code must follow the format WS-2025/2026 or SS-2025/2026"
        }
    }

    override fun toString(): String = value
}

@Embeddable
data class SemesterPeriod(
    @Column(name = "start_date")
    val startDate: LocalDate = LocalDate.of(1970, 1, 1),

    @Column(name = "end_date")
    val endDate: LocalDate = LocalDate.of(1970, 1, 1)
) : Serializable {
    init {
        require(!endDate.isBefore(startDate)) {
            "Semester end date cannot be before start date"
        }
    }
}

@Embeddable
data class SemesterCapacity(val value: Int = 30) : Serializable {
    init {
        require(value >= 21) {
            "Semester capacity in ECTS must be at least 21"
        }
        require(value <= 35) {
            "Semester capacity in ECTS cannot exceed 35"
        }
    }

}

//Student Record Common Value Objects and Interfaces


@Embeddable
data class StudentRecordId(override val value: String = "") : Identifier<StudentRecordId>(
    value, StudentRecordId::class.java
) {
    constructor(uuid: UUID) : this("StudentRecord:$uuid")

    companion object {
        fun generate(): StudentRecordId = StudentRecordId(UUID.randomUUID())
    }
}

@Embeddable
data class StudentGpa(val value: Double = 6.0) {
    init {
        require(value >= 5.0) { "GPA cannot be below 5.0" }
        require(value <= 10.0) { "GPA cannot exceed 10.0" }
    }
}

@Embeddable
data class TotalEctsEarned(val value: Int = 0) {
    init {
        require(value >= 0) { "Total ECTS earned cannot be negative" }
        require(value <= 300) { "Total ECTS earned cannot exceed 300" }
    }
}


@Embeddable
data class CompletedSemesters(val value: Int = 0) {
    init {
        require(value >= 0) { "Completed semesters cannot be negative" }
    }
}
