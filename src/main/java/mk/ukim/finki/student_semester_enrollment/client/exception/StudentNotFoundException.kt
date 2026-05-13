package mk.ukim.finki.student_semester_enrollment.exception

class StudentNotFoundException(id: String) :
    RuntimeException("Student with id $id not found")