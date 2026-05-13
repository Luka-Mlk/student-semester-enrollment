package mk.ukim.finki.student_semester_enrollment.requests


data class CreateStudentRecordRequest(
    val studentId: String,
    val studentIndex: String
)