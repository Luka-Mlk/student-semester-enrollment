package mk.ukim.finki.student_semester_enrollment.client

import mk.ukim.finki.student_semester_enrollment.client.fallback.SubjectManagementClientFallback
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(
    name = "study-program-accreditation",
    contextId = "subjectManagementClient",
    fallback = SubjectManagementClientFallback::class
)
interface SubjectManagementClient {

    @GetMapping("/subjects/all")
    fun getAllSubjects(): List<SubjectDTO>

    @GetMapping("/subjects/{id}")
    fun getSubjectById(@PathVariable id: String): SubjectDTO
}