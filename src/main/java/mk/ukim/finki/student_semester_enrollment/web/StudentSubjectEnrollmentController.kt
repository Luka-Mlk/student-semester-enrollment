package mk.ukim.finki.student_semester_enrollment.web

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mk.ukim.finki.student_semester_enrollment.model.StudentSemesterEnrollmentId
import mk.ukim.finki.student_semester_enrollment.model.StudentSubjectEnrollmentId
import mk.ukim.finki.student_semester_enrollment.service.StudentSubjectEnrollmentViewReadService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/student-subject-enrollments")
@Tag(name = "Student Subject Enrollments", description = "Manage subject enrollments")
class StudentSubjectEnrollmentController(
    private val readService: StudentSubjectEnrollmentViewReadService
) {

    @Operation(summary = "Get all subject enrollments", description = "Returns all subject enrollments")
    @GetMapping("/all")
    fun findAll() =
        readService.findAll()

    @Operation(summary = "Get subject enrollment by ID", description = "Returns subject enrollment by ID")
    @GetMapping("/{id}")
    fun findById(@PathVariable id: String) =
        readService.findById(StudentSubjectEnrollmentId(id))

    @Operation(summary = "Get enrollments by enrollment ID", description = "Returns subject enrollments for a semester enrollment")
    @GetMapping("/by-enrollment/{enrollmentId}")
    fun findByEnrollmentId(@PathVariable enrollmentId: String) =
        readService.findByEnrollmentId(StudentSemesterEnrollmentId(enrollmentId))
}