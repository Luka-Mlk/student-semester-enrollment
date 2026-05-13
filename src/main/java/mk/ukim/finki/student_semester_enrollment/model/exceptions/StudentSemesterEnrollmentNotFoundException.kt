package mk.ukim.finki.student_semester_enrollment.model.exceptions

import mk.ukim.finki.student_semester_enrollment.model.StudentSemesterEnrollmentId

class StudentSemesterEnrollmentNotFoundException(id: StudentSemesterEnrollmentId) :
    RuntimeException("StudentSemesterEnrollment not found with id: $id")