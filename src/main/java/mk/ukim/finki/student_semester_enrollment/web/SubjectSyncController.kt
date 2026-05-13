package mk.ukim.finki.student_semester_enrollment.web

import mk.ukim.finki.student_semester_enrollment.service.SubjectSyncService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/subjects")
class SubjectSyncController(
    private val service: SubjectSyncService
) {

    @PostMapping("/sync")
    fun sync() {
        service.syncSubjects()
    }
}