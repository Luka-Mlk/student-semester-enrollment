package mk.ukim.finki.student_semester_enrollment.model

// External contract events: only fields consumers need.
data class StudentGraduatedExternalEvent(
    val recordId: String
)

data class StudentExpelledExternalEvent(
    val recordId: String,
    val reason: String
)
