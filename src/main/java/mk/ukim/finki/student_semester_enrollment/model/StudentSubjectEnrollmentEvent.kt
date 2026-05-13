package mk.ukim.finki.student_semester_enrollment.model

abstract class StudentSubjectEnrollmentEvent(
    open val subjectEnrollmentId: StudentSubjectEnrollmentId
) : AbstractEvent(subjectEnrollmentId)

