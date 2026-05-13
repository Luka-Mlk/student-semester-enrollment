package mk.ukim.finki.student_semester_enrollment.model

abstract class SemesterEvent(
    open val semesterId: SemesterId
) : AbstractEvent(semesterId)