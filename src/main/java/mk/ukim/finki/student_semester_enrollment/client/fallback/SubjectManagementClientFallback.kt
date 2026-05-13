package mk.ukim.finki.student_semester_enrollment.client.fallback

import mk.ukim.finki.student_semester_enrollment.client.SubjectDTO
import mk.ukim.finki.student_semester_enrollment.client.SubjectManagementClient
import org.springframework.stereotype.Component

@Component
class SubjectManagementClientFallback : SubjectManagementClient {

    override fun getAllSubjects(): List<SubjectDTO> {
        return emptyList()
    }

    override fun getSubjectById(id: String): SubjectDTO {
        return SubjectDTO(
            code = id,
            name = "Unavailable subject",
            semester = null,
            studyGroup = null,
            mandatoryStatus = null,
            studyProgramId = null,
            status = "UNKNOWN",
            label = "Unavailable subject"
        )
    }
}