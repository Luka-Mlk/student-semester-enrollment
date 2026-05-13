package mk.ukim.finki.student_semester_enrollment.service

import mk.ukim.finki.student_semester_enrollment.client.SubjectManagementClient
import mk.ukim.finki.student_semester_enrollment.model.Subject
import mk.ukim.finki.student_semester_enrollment.repository.SubjectRepository
import org.springframework.stereotype.Service
import kotlin.math.abs

@Service
class SubjectSyncService(
    private val subjectManagementClient: SubjectManagementClient,
    private val subjectRepository: SubjectRepository
) {

    fun syncSubjects(): List<Subject> {
        val externalSubjects = subjectManagementClient.getAllSubjects()

        val subjects = externalSubjects
            .filter { !it.code.isNullOrBlank() }
            .map { externalSubject ->
                Subject(
                    id = generateSubjectId(externalSubject.code!!),
                    name = externalSubject.name ?: externalSubject.code,
                    ects = 6,
                    mandatory = externalSubject.mandatoryStatus ?: false
                )
            }

        return subjectRepository.saveAll(subjects)
    }

    private fun generateSubjectId(code: String): Long {
        return abs(code.hashCode().toLong())
    }
}