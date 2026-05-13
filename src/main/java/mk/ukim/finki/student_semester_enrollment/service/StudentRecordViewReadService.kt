//package mk.ukim.finki.student_semester_enrollment.service
//
//import mk.ukim.finki.student_semester_enrollment.model.StudentId
//import mk.ukim.finki.student_semester_enrollment.model.StudentRecordId
//import mk.ukim.finki.student_semester_enrollment.model.StudentRecordView
//import mk.ukim.finki.student_semester_enrollment.model.enums.StudentRecordStatus
//import mk.ukim.finki.student_semester_enrollment.repository.StudentRecordViewJpaRepository
//import org.springframework.stereotype.Service
//
//
//class StudentRecordNotFoundException(id: StudentRecordId) :
//        RuntimeException("StudentRecord with id ${id.value} was not found")
//
//
//interface StudentRecordViewReadService {
//    fun findAll(): List<StudentRecordView>
//    fun findById(id: StudentRecordId): StudentRecordView
//    fun findByStatus(status: StudentRecordStatus): List<StudentRecordView>
//    fun findByStudentId(studentId: StudentId): List<StudentRecordView>
//}
//
//
//@Service
//class StudentRecordViewReadServiceImpl(
//        private val repository: StudentRecordViewJpaRepository
//) : StudentRecordViewReadService {
//
//    override fun findAll(): List<StudentRecordView> =
//            repository.findAll()
//
//    override fun findById(id: StudentRecordId): StudentRecordView =
//            repository.findById(id).orElseThrow { StudentRecordNotFoundException(id) }
//
//    override fun findByStatus(status: StudentRecordStatus): List<StudentRecordView> =
//            repository.findByStatus(status)
//
//    override fun findByStudentId(studentId: StudentId): List<StudentRecordView> =
//            repository.findByStudentId(studentId)
//}