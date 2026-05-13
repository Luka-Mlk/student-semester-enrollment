package mk.ukim.finki.student_semester_enrollment.repository

interface EventMessagingRepository {
    fun send(topic: String, key: String, payload: String)
}