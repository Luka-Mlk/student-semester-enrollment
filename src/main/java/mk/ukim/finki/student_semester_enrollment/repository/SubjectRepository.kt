package mk.ukim.finki.student_semester_enrollment.repository

import mk.ukim.finki.student_semester_enrollment.model.Subject
import org.springframework.data.jpa.repository.JpaRepository

interface SubjectRepository : JpaRepository<Subject, Long>