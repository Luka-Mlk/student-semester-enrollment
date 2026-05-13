package mk.ukim.finki.student_semester_enrollment.web

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mk.ukim.finki.student_semester_enrollment.model.*
import mk.ukim.finki.student_semester_enrollment.model.enums.StudentRecordStatus
import mk.ukim.finki.student_semester_enrollment.requests.CreateStudentRecordRequest
import mk.ukim.finki.student_semester_enrollment.service.StudentRecordViewReadService
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/student-records")
@Tag(name = "Student Records", description = "Operations for managing student records")
class StudentRecordController(
    private val readService: StudentRecordViewReadService,
    private val commandGateway: CommandGateway
) {

    @Operation(summary = "Get all student records", description = "Returns all student records")
    @GetMapping("/all")
    fun findAll(): List<StudentRecordView> =
        readService.findAll()

    @Operation(summary = "Get record by ID", description = "Returns a student record by its ID")
    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): StudentRecordView =
        readService.findById(StudentRecordId(id))

    @Operation(summary = "Get records by status", description = "Returns student records filtered by status")
    @GetMapping("/by-status/{status}")
    fun findByStatus(@PathVariable status: StudentRecordStatus): List<StudentRecordView> =
        readService.findByStatus(status)

    @Operation(summary = "Get records by student ID", description = "Returns records for a specific student")
    @GetMapping("/by-student/{studentId}")
    fun findByStudentId(@PathVariable studentId: String): List<StudentRecordView> =
        readService.findByStudentId(StudentId(studentId))

    @Operation(summary = "Check if student exists", description = "Returns true if student record exists")
    @GetMapping("/exists/{studentId}")
    fun existsByStudentId(@PathVariable studentId: String): Boolean =
        readService.existsByStudentId(StudentId(studentId))

    @Operation(summary = "Graduate student", description = "Marks a student as graduated")
    @PostMapping("/graduate/{id}")
    fun graduate(@PathVariable id: String) {
        val recordId = StudentRecordId(UUID.fromString(id))

        commandGateway.sendAndWait<Any>(
            GraduateStudentCommand(recordId)
        )
    }

    @Operation(summary = "Create student record", description = "Creates a new student record")
    @PostMapping("/create")
    fun create(@RequestBody request: CreateStudentRecordRequest): String {

        val id = StudentRecordId.generate()

        commandGateway.sendAndWait<Any>(
            CreateStudentRecordCommand(
                recordId = id,
                studentId = StudentId(request.studentId),
                studentIndex = request.studentIndex
            )
        )

        return id.value
    }
}