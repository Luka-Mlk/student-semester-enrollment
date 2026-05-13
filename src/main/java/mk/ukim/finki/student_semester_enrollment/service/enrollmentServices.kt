package mk.ukim.finki.student_semester_enrollment.service



import mk.ukim.finki.student_semester_enrollment.model.SemesterCode
import mk.ukim.finki.student_semester_enrollment.model.SemesterId
import mk.ukim.finki.student_semester_enrollment.model.SemesterView
import mk.ukim.finki.student_semester_enrollment.model.StudentId
import mk.ukim.finki.student_semester_enrollment.model.StudentRecordId
import mk.ukim.finki.student_semester_enrollment.model.StudentRecordView
import mk.ukim.finki.student_semester_enrollment.model.StudentSemesterEnrollmentId
import mk.ukim.finki.student_semester_enrollment.model.StudentSemesterEnrollmentView
import mk.ukim.finki.student_semester_enrollment.model.StudentSubjectEnrollmentId
import mk.ukim.finki.student_semester_enrollment.model.StudentSubjectEnrollmentView
import mk.ukim.finki.student_semester_enrollment.model.enums.EnrollmentStatus
import mk.ukim.finki.student_semester_enrollment.model.enums.SemesterStatus
import mk.ukim.finki.student_semester_enrollment.model.enums.StudentRecordStatus

//StudentSemsesterEnrollment interface
interface StudentSemesterEnrollmentViewReadService {
    fun findAll(): List<StudentSemesterEnrollmentView>
    fun findById(id: StudentSemesterEnrollmentId): StudentSemesterEnrollmentView
    fun findByStatus(status: EnrollmentStatus): List<StudentSemesterEnrollmentView>
    fun findByStudentId(studentId: StudentId): List<StudentSemesterEnrollmentView>
    fun findBySemesterId(semesterId: SemesterId): List<StudentSemesterEnrollmentView>
}
//SemesterView interface
interface SemesterViewReadService {
    fun findAll(): List<SemesterView>
    fun findById(id: SemesterId): SemesterView
    fun findByStatus(status: SemesterStatus): List<SemesterView>
    fun findBySemesterCode(semesterCode: SemesterCode): List<SemesterView>
}
//StudentRecordView interface
interface StudentRecordViewReadService {
    fun findAll(): List<StudentRecordView>
    fun findById(id: StudentRecordId): StudentRecordView
    fun findByStatus(status: StudentRecordStatus): List<StudentRecordView>
    fun findByStudentId(studentId: StudentId): List<StudentRecordView>
    fun existsByStudentId(studentId: StudentId): Boolean
}
//StudentSubjectEnrollmentView interface
interface StudentSubjectEnrollmentViewReadService {
    fun findAll(): List<StudentSubjectEnrollmentView>
    fun findById(id: StudentSubjectEnrollmentId): StudentSubjectEnrollmentView
    fun findByEnrollmentId(enrollmentId: StudentSemesterEnrollmentId): List<StudentSubjectEnrollmentView>
}
