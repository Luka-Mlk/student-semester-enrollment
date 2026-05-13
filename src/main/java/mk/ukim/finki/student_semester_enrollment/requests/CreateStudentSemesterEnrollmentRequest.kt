package mk.ukim.finki.student_semester_enrollment.requests

data class CreateStudentSemesterEnrollmentRequest(
    val id: String,
    val studentId: String,
    val semesterId: String
)