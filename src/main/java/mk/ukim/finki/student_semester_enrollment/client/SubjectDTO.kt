package mk.ukim.finki.student_semester_enrollment.client

data class SubjectDTO(
    val code: String? = null,
    val name: String? = null,
    val semester: String? = null,
    val studyGroup: String? = null,
    val mandatoryStatus: Boolean? = null,
    val studyProgramId: String? = null,
    val status: String? = null,
    val label: String? = null
)

/*
package mk.ukim.finki.student_semester_enrollment.client


data class SubjectDTO(
    val id: Long,
    val name: String,
    val ects: Int,
    val mandatory: Boolean
)*/
