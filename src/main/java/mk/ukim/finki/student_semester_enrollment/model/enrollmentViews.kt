package mk.ukim.finki.student_semester_enrollment.model

import jakarta.persistence.*
import mk.ukim.finki.student_semester_enrollment.model.enums.*
import org.hibernate.annotations.Immutable

// ======================================================
// STUDENT SEMESTER ENROLLMENT VIEW
// ======================================================

@Entity
@Table(name = "student_semester_enrollment")
@Immutable
data class StudentSemesterEnrollmentView(

    @Id
    @AttributeOverride(name = "value", column = Column(name = "id"))
    val id: StudentSemesterEnrollmentId = StudentSemesterEnrollmentId(),

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "student_id"))
    val studentId: StudentId = StudentId(),

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "semester_id"))
    val semesterId: SemesterId = SemesterId(),

    @Enumerated(EnumType.STRING)
    val status: EnrollmentStatus = EnrollmentStatus.DRAFT,

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "total_ects"))
    val totalEcts: EnrollmentEcts = EnrollmentEcts(0)
) : LabeledEntity {

    override fun getId(): Identifier<out Any> = id
    override fun getLabel(): String = "$studentId - $semesterId"
}

// ======================================================
// SEMESTER VIEW
// ======================================================

@Entity
@Table(name = "semester")
@Immutable
data class SemesterView(

    @Id
    @AttributeOverride(name = "value", column = Column(name = "id"))
    val id: SemesterId = SemesterId(),

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "semester_code"))
    val semesterCode: SemesterCode = SemesterCode(),

    @Embedded
    val period: SemesterPeriod = SemesterPeriod(),

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "capacity"))
    val capacity: SemesterCapacity = SemesterCapacity(0),

    @Enumerated(EnumType.STRING)
    val status: SemesterStatus = SemesterStatus.CREATED
) : LabeledEntity {

    override fun getId(): Identifier<out Any> = id
    override fun getLabel(): String = "${semesterCode.value} (${status.name})"
}

// ======================================================
// STUDENT RECORD VIEW
// ======================================================

@Entity
@Table(name = "student_record")
class StudentRecordView() {

    @Id
    @AttributeOverride(name = "value", column = Column(name = "id"))
    lateinit var id: StudentRecordId

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "student_id"))
    lateinit var studentId: StudentId

    @Enumerated(EnumType.STRING)
    lateinit var status: StudentRecordStatus

    @Column(name = "student_index")
    var studentIndex: String = "220000"

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "gpa"))
    lateinit var gpa: StudentGpa

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "total_ects_earned"))
    lateinit var totalEctsEarned: TotalEctsEarned

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "completed_semesters"))
    lateinit var completedSemesters: CompletedSemesters

    constructor(
        id: StudentRecordId,
        studentId: StudentId,
        status: StudentRecordStatus,
        studentIndex: String,
        gpa: StudentGpa,
        totalEctsEarned: TotalEctsEarned,
        completedSemesters: CompletedSemesters
    ) : this() {
        this.id = id
        this.studentId = studentId
        this.status = status
        this.studentIndex = studentIndex
        this.gpa = gpa
        this.totalEctsEarned = totalEctsEarned
        this.completedSemesters = completedSemesters
    }

    fun getId(): Identifier<out Any> = id

    fun getLabel(): String = "StudentRecord - $studentIndex"
}

// ======================================================
// STUDENT SUBJECT ENROLLMENT VIEW (FIXED)
// ======================================================
@Entity
@Table(name = "student_subject_enrollment_view")
class StudentSubjectEnrollmentView(

    @Id
    @AttributeOverride(name = "value", column = Column(name = "id"))
    var id: StudentSubjectEnrollmentId = StudentSubjectEnrollmentId(),

    @Embedded
    @AttributeOverride(name = "value", column = Column(name = "enrollment_id"))
    var enrollmentId: StudentSemesterEnrollmentId = StudentSemesterEnrollmentId(),

    @Column(name = "subject_id", nullable = false)
    var subjectId: Long = 0,   // ✅ NEW (core change)

    @Column(name = "dependencies_validated")
    var dependenciesValidated: Boolean = false
)