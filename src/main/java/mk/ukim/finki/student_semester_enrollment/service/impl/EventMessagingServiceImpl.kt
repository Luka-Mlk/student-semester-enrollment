package mk.ukim.finki.student_semester_enrollment.service.impl

import mk.ukim.finki.student_semester_enrollment.repository.EventMessagingRepository
import mk.ukim.finki.student_semester_enrollment.service.EventMessagingService
import org.springframework.stereotype.Service

@Service
class EventMessagingServiceImpl(
        val eventMessagingRepository: EventMessagingRepository
) : EventMessagingService {
    override fun send(topic: String, key: String, payload: String) {
        eventMessagingRepository.send(topic, key, payload)
    }
}