package mk.ukim.finki.student_semester_enrollment.client

import mk.ukim.finki.student_semester_enrollment.client.fallback.StudentManagementClientFallback
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(
    name = "study-program-accreditation",
    contextId = "studentManagementClient",
    fallback = StudentManagementClientFallback::class
)
interface StudentManagementClient {

    @GetMapping("/students/exists/{id}")
    fun existsStudent(@PathVariable id: String): Boolean
}