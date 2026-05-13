package mk.ukim.finki.student_semester_enrollment.model

abstract class StudentRecordEvent(
    open val recordId: StudentRecordId
) : AbstractEvent(recordId)

