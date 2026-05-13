package mk.ukim.finki.student_semester_enrollment.model.exceptions

import mk.ukim.finki.student_semester_enrollment.model.SemesterId

class SemesterNotFoundException(id: SemesterId) :
    RuntimeException("Semester not found with id: ${id.value}")