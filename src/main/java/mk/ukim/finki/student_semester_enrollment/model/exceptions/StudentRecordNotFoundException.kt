package mk.ukim.finki.student_semester_enrollment.model.exceptions

import mk.ukim.finki.student_semester_enrollment.model.StudentId
import mk.ukim.finki.student_semester_enrollment.model.StudentRecordId


class StudentRecordNotFoundException(id: StudentRecordId) :
    RuntimeException("StudentRecord with id ${id.value} was not found")
