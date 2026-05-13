package mk.ukim.finki.student_semester_enrollment.model

abstract class StudentSemesterEnrollmentEvent(
    open val enrollmentId: StudentSemesterEnrollmentId
) : AbstractEvent(enrollmentId)

