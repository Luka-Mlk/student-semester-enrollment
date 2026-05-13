package mk.ukim.finki.student_semester_enrollment.client.fallback

import mk.ukim.finki.student_semester_enrollment.client.StudentManagementClient
import org.springframework.stereotype.Component

@Component
class StudentManagementClientFallback : StudentManagementClient {

    override fun existsStudent(id: String): Boolean {
        return false
    }
}