package mk.ukim.finki.student_semester_enrollment.infrastructure.kafka

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class IncomingSubjectCreatedEventDTO(
    val subjectId: ExternalIdDTO,
    val name: String,
    val ects: Int,
    val isMandatory: Boolean
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ExternalIdDTO(
    val value: String
)