package mk.ukim.finki.student_semester_enrollment.web

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mk.ukim.finki.student_semester_enrollment.model.*
import mk.ukim.finki.student_semester_enrollment.model.enums.EnrollmentStatus
import mk.ukim.finki.student_semester_enrollment.requests.CreateStudentSemesterEnrollmentRequest
import mk.ukim.finki.student_semester_enrollment.service.StudentSemesterEnrollmentViewReadService
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/student-semester-enrollments")
@Tag(name = "Student Semester Enrollments", description = "Manage semester enrollments")
class StudentSemesterEnrollmentController(
    private val readService: StudentSemesterEnrollmentViewReadService,
    private val commandGateway: CommandGateway
) {

    @Operation(summary = "Get all enrollments", description = "Returns all student semester enrollments")
    @GetMapping("/all")
    fun findAll() = readService.findAll()

    @Operation(summary = "Get enrollment by ID", description = "Returns enrollment by ID")
    @GetMapping("/{id}")
    fun findById(@PathVariable id: String) =
        readService.findById(StudentSemesterEnrollmentId(id))

    @Operation(summary = "Get enrollments by status", description = "Returns enrollments filtered by status")
    @GetMapping("/by-status/{status}")
    fun findByStatus(@PathVariable status: EnrollmentStatus) =
        readService.findByStatus(status)

    @Operation(summary = "Get enrollments by student", description = "Returns enrollments for a student")
    @GetMapping("/by-student/{studentId}")
    fun findByStudentId(@PathVariable studentId: String) =
        readService.findByStudentId(StudentId(studentId))

    @Operation(summary = "Get enrollments by semester", description = "Returns enrollments for a semester")
    @GetMapping("/by-semester/{semesterId}")
    fun findBySemesterId(@PathVariable semesterId: String) =
        readService.findBySemesterId(SemesterId(semesterId))

    @Operation(summary = "Create enrollment", description = "Creates a new student semester enrollment")
    @PostMapping("/create")
    fun create(@RequestBody request: CreateStudentSemesterEnrollmentRequest): String {
        commandGateway.sendAndWait<Any>(
            CreateStudentSemesterEnrollmentCommand(
                enrollmentId = StudentSemesterEnrollmentId(request.id),
                studentId = StudentId(request.studentId),
                semesterId = SemesterId(request.semesterId)
            )
        )
        return request.id
    }
}