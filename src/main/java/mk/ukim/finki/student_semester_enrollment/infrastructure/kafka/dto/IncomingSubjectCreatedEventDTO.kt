package mk.ukim.finki.student_semester_enrollment.infrastructure.kafka.dto


import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class IncomingSubjectCreatedEventDTO(
    val subjectId: ExternalIdDTO,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ExternalIdDTO(
    val value: Long
)