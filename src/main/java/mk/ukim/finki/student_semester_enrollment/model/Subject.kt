package mk.ukim.finki.student_semester_enrollment.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Subject(
    @Id
    val id: Long,
    val name: String,
    val ects: Int,
    val mandatory: Boolean
)