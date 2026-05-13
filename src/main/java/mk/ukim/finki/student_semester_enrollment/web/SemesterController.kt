package mk.ukim.finki.student_semester_enrollment.web

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mk.ukim.finki.student_semester_enrollment.model.SemesterCode
import mk.ukim.finki.student_semester_enrollment.model.SemesterId
import mk.ukim.finki.student_semester_enrollment.model.SemesterView
import mk.ukim.finki.student_semester_enrollment.model.enums.SemesterStatus
import mk.ukim.finki.student_semester_enrollment.service.SemesterViewReadService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/semesters")
@Tag(name = "Semesters", description = "Operations for managing semesters")
class SemesterController(
    private val readService: SemesterViewReadService
) {

    @Operation(summary = "Get all semesters", description = "Returns a list of all semesters")
    @GetMapping("/all")
    fun findAll(): List<SemesterView> =
        readService.findAll()

    @Operation(summary = "Get semester by ID", description = "Returns a specific semester by its ID")
    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): SemesterView =
        readService.findById(SemesterId(id))

    @Operation(summary = "Get semesters by status", description = "Returns semesters filtered by status")
    @GetMapping("/by-status/{status}")
    fun findByStatus(@PathVariable status: SemesterStatus): List<SemesterView> =
        readService.findByStatus(status)

    @Operation(summary = "Get semesters by code", description = "Returns semesters filtered by semester code")
    @GetMapping("/by-code/{code}")
    fun findBySemesterCode(@PathVariable code: String): List<SemesterView> =
        readService.findBySemesterCode(SemesterCode(code))
}