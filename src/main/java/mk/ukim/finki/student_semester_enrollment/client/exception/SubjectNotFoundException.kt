package mk.ukim.finki.student_semester_enrollment.exception

class SubjectNotFoundException(id: String) :
    RuntimeException("Subject with id $id not found")