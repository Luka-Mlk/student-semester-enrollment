package mk.ukim.finki.student_semester_enrollment.service

interface EventMessagingService {
    fun send(topic: String, key: String, payload: String)
}