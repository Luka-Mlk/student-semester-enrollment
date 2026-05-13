package mk.ukim.finki.student_semester_enrollment.model

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.UUID

//Student Semester Enrollment Commands
data class CreateStudentSemesterEnrollmentCommand(
    val enrollmentId: StudentSemesterEnrollmentId = StudentSemesterEnrollmentId(UUID.randomUUID()),
    val studentId: StudentId,
    val semesterId: SemesterId
)

data class OpenEnrollmentWindowCommand(
    @TargetAggregateIdentifier val enrollmentId: StudentSemesterEnrollmentId,
    val semesterId: SemesterId
)


data class AddSubjectToEnrollmentCommand(
    @TargetAggregateIdentifier val enrollmentId: StudentSemesterEnrollmentId,
    val subjectId: StudentSubjectEnrollmentId,
    val ects: Int
)

data class RemoveSubjectFromEnrollmentCommand(
    @TargetAggregateIdentifier val enrollmentId: StudentSemesterEnrollmentId,
    val subjectId: StudentSubjectEnrollmentId,
    val ects: Int
)

data class UpdateEnrollmentEctsCommand(
    @TargetAggregateIdentifier val enrollmentId: StudentSemesterEnrollmentId,
    val ects: EnrollmentEcts
)

data class ConfirmEnrollmentCommand(
    @TargetAggregateIdentifier val enrollmentId: StudentSemesterEnrollmentId
)

data class RejectEnrollmentCommand(
    @TargetAggregateIdentifier val enrollmentId: StudentSemesterEnrollmentId,
    val reason: String
)

//Semester Commands



data class CreateSemesterCommand(
    val semesterId: SemesterId = SemesterId.generate(),
    val semesterCode: SemesterCode,
    val period: SemesterPeriod,
    val capacity: SemesterCapacity = SemesterCapacity(30)
)

data class UpdateSemesterCodeCommand(
    @TargetAggregateIdentifier val semesterId: SemesterId,
    val semesterCode: SemesterCode
)

data class UpdateSemesterPeriodCommand(
    @TargetAggregateIdentifier val semesterId: SemesterId,
    val period: SemesterPeriod
)

data class UpdateSemesterCapacityCommand(
    @TargetAggregateIdentifier val semesterId: SemesterId,
    val capacity: SemesterCapacity
)

data class ApproveSemesterCommand(
    @TargetAggregateIdentifier val semesterId: SemesterId
)

data class RejectSemesterCommand(
    @TargetAggregateIdentifier val semesterId: SemesterId,
    val reason: String
)

//Student Record Commands

data class CreateStudentRecordCommand(
    val recordId: StudentRecordId = StudentRecordId(UUID.randomUUID()),
    val studentId: StudentId,
    val studentIndex: String
)

data class UpdateStudentGpaCommand(
    @TargetAggregateIdentifier val recordId: StudentRecordId,
    val gpa: StudentGpa
)

data class UpdateTotalEctsEarnedCommand(
    @TargetAggregateIdentifier val recordId: StudentRecordId,
    val totalEctsEarned: TotalEctsEarned
)

data class IncrementCompletedSemestersCommand(
    @TargetAggregateIdentifier val recordId: StudentRecordId
)

data class SuspendStudentRecordCommand(
    @TargetAggregateIdentifier val recordId: StudentRecordId,
    val reason: String
)

data class GraduateStudentCommand(
    @TargetAggregateIdentifier val recordId: StudentRecordId
)

data class ExpelStudentCommand(
    @TargetAggregateIdentifier val recordId: StudentRecordId,
    val reason: String
)

//Student Subject Enrollment Commands

data class CreateStudentSubjectEnrollmentCommand(
    val subjectEnrollmentId: StudentSubjectEnrollmentId,
    val enrollmentId: StudentSemesterEnrollmentId,
    val subjectId: Long
)

data class AddMandatorySubjectCommand(
    @TargetAggregateIdentifier val subjectEnrollmentId: StudentSubjectEnrollmentId
)

data class AddElectiveSubjectCommand(
    @TargetAggregateIdentifier val subjectEnrollmentId: StudentSubjectEnrollmentId
)

data class ValidateSubjectDependenciesCommand(
    @TargetAggregateIdentifier val subjectEnrollmentId: StudentSubjectEnrollmentId
)