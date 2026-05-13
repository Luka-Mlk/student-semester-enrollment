package mk.ukim.finki.student_semester_enrollment.model

//Student Semester Enrollment Events
data class StudentSemesterEnrollmentCreatedEvent(
    override val enrollmentId: StudentSemesterEnrollmentId,
    val studentId: StudentId,
    val semesterId: SemesterId
) : StudentSemesterEnrollmentEvent(enrollmentId) {
    constructor(command: CreateStudentSemesterEnrollmentCommand) : this(
        enrollmentId = command.enrollmentId,
        studentId = command.studentId,
        semesterId = command.semesterId
    )
}

data class EnrollmentWindowOpenedEvent(
    override val enrollmentId: StudentSemesterEnrollmentId,
    val semesterId: SemesterId
) : StudentSemesterEnrollmentEvent(enrollmentId) {
    constructor(command: OpenEnrollmentWindowCommand) : this(
        enrollmentId = command.enrollmentId,
        semesterId = command.semesterId
    )
}

data class SubjectAddedToEnrollmentEvent(
    override val enrollmentId: StudentSemesterEnrollmentId,
    val subjectId: StudentSubjectEnrollmentId,
    val ects: Int
) : StudentSemesterEnrollmentEvent(enrollmentId) {
    constructor(command: AddSubjectToEnrollmentCommand) : this(
        enrollmentId = command.enrollmentId,
        subjectId = command.subjectId,
        ects = command.ects
    )
}

data class SubjectRemovedFromEnrollmentEvent(
    override val enrollmentId: StudentSemesterEnrollmentId,
    val subjectId: StudentSubjectEnrollmentId,
    val ects: Int
) : StudentSemesterEnrollmentEvent(enrollmentId) {
    constructor(command: RemoveSubjectFromEnrollmentCommand) : this(
        enrollmentId = command.enrollmentId,
        subjectId = command.subjectId,
        ects = command.ects
    )
}

data class EnrollmentEctsUpdatedEvent(
    override val enrollmentId: StudentSemesterEnrollmentId,
    val ects: EnrollmentEcts
) : StudentSemesterEnrollmentEvent(enrollmentId) {
    constructor(command: UpdateEnrollmentEctsCommand) : this(
        enrollmentId = command.enrollmentId,
        ects = command.ects
    )
}

data class EnrollmentConfirmedEvent(
    override val enrollmentId: StudentSemesterEnrollmentId,
    val studentId: StudentId,
    val semesterId: SemesterId
) : StudentSemesterEnrollmentEvent(enrollmentId) {
    constructor(
        command: ConfirmEnrollmentCommand,
        studentId: StudentId,
        semesterId: SemesterId
    ) : this(
        enrollmentId = command.enrollmentId,
        studentId = studentId,
        semesterId = semesterId
    )
}

data class EnrollmentRejectedEvent(
    override val enrollmentId: StudentSemesterEnrollmentId,
    val reason: String
) : StudentSemesterEnrollmentEvent(enrollmentId) {
    constructor(command: RejectEnrollmentCommand) : this(
        enrollmentId = command.enrollmentId,
        reason = command.reason
    )
}

//Semester Events
data class SemesterCreatedEvent(
    override val semesterId: SemesterId,
    val semesterCode: SemesterCode,
    val period: SemesterPeriod,
    val capacity: SemesterCapacity
) : SemesterEvent(semesterId) {
    constructor(command: CreateSemesterCommand) : this(
        semesterId = command.semesterId,
        semesterCode = command.semesterCode,
        period = command.period,
        capacity = command.capacity
    )
}

data class SemesterCodeUpdatedEvent(
    override val semesterId: SemesterId,
    val semesterCode: SemesterCode
) : SemesterEvent(semesterId) {
    constructor(command: UpdateSemesterCodeCommand) : this(
        semesterId = command.semesterId,
        semesterCode = command.semesterCode
    )
}

data class SemesterPeriodUpdatedEvent(
    override val semesterId: SemesterId,
    val period: SemesterPeriod
) : SemesterEvent(semesterId) {
    constructor(command: UpdateSemesterPeriodCommand) : this(
        semesterId = command.semesterId,
        period = command.period
    )
}

data class SemesterCapacityUpdatedEvent(
    override val semesterId: SemesterId,
    val capacity: SemesterCapacity
) : SemesterEvent(semesterId) {
    constructor(command: UpdateSemesterCapacityCommand) : this(
        semesterId = command.semesterId,
        capacity = command.capacity
    )
}

data class SemesterApprovedEvent(
    override val semesterId: SemesterId
) : SemesterEvent(semesterId) {
    constructor(command: ApproveSemesterCommand) : this(
        semesterId = command.semesterId
    )
}

data class SemesterRejectedEvent(
    override val semesterId: SemesterId,
    val reason: String
) : SemesterEvent(semesterId) {
    constructor(command: RejectSemesterCommand) : this(
        semesterId = command.semesterId,
        reason = command.reason
    )
}

//Student Record Events
data class StudentRecordCreatedEvent(
    override val recordId: StudentRecordId,
    val studentId: StudentId,
    val studentIndex: String
) : StudentRecordEvent(recordId)

data class StudentGpaUpdatedEvent(
    override val recordId: StudentRecordId,
    val gpa: StudentGpa
) : StudentRecordEvent(recordId) {
    constructor(command: UpdateStudentGpaCommand) : this(
        recordId = command.recordId,
        gpa = command.gpa
    )
}

data class TotalEctsEarnedUpdatedEvent(
    override val recordId: StudentRecordId,
    val totalEctsEarned: TotalEctsEarned
) : StudentRecordEvent(recordId) {
    constructor(command: UpdateTotalEctsEarnedCommand) : this(
        recordId = command.recordId,
        totalEctsEarned = command.totalEctsEarned
    )
}

data class CompletedSemestersIncrementedEvent(
    override val recordId: StudentRecordId,
    val newCount: CompletedSemesters
) : StudentRecordEvent(recordId)

data class StudentRecordSuspendedEvent(
    override val recordId: StudentRecordId,
    val reason: String
) : StudentRecordEvent(recordId) {
    constructor(command: SuspendStudentRecordCommand) : this(
        recordId = command.recordId,
        reason = command.reason
    )
}

data class StudentGraduatedEvent(
    override val recordId: StudentRecordId
) : StudentRecordEvent(recordId) {
    constructor(command: GraduateStudentCommand) : this(
        recordId = command.recordId
    )

    override fun toExternalEvent(): StudentGraduatedExternalEvent {
        return StudentGraduatedExternalEvent(
            recordId = this.recordId.value
        )
    }
}

data class StudentExpelledEvent(
    override val recordId: StudentRecordId,
    val reason: String
) : StudentRecordEvent(recordId) {
    constructor(command: ExpelStudentCommand) : this(
        recordId = command.recordId,
        reason = command.reason
    )

    override fun toExternalEvent(): StudentExpelledExternalEvent {
        return StudentExpelledExternalEvent(
            recordId = this.recordId.value,
            reason = this.reason
        )
    }
}

//Student Subject Enrollment Events
data class StudentSubjectEnrollmentCreatedEvent(
    val subjectEnrollmentId: StudentSubjectEnrollmentId,
    val enrollmentId: StudentSemesterEnrollmentId,
    val subjectId: Long
) {
    constructor(command: CreateStudentSubjectEnrollmentCommand) : this(
        command.subjectEnrollmentId,
        command.enrollmentId,
        command.subjectId
    )
}

data class MandatorySubjectAddedEvent(
    override val subjectEnrollmentId: StudentSubjectEnrollmentId
) : StudentSubjectEnrollmentEvent(subjectEnrollmentId) {
    constructor(command: AddMandatorySubjectCommand) : this(
        subjectEnrollmentId = command.subjectEnrollmentId
    )
}

data class ElectiveSubjectAddedEvent(
    override val subjectEnrollmentId: StudentSubjectEnrollmentId
) : StudentSubjectEnrollmentEvent(subjectEnrollmentId) {
    constructor(command: AddElectiveSubjectCommand) : this(
        subjectEnrollmentId = command.subjectEnrollmentId
    )
}

data class SubjectDependenciesValidatedEvent(
    override val subjectEnrollmentId: StudentSubjectEnrollmentId
) : StudentSubjectEnrollmentEvent(subjectEnrollmentId) {
    constructor(command: ValidateSubjectDependenciesCommand) : this(
        subjectEnrollmentId = command.subjectEnrollmentId
    )
}