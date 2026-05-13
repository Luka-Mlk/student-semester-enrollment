package mk.ukim.finki.student_semester_enrollment.config

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import mk.ukim.finki.student_semester_enrollment.model.Semester
import mk.ukim.finki.student_semester_enrollment.model.SemesterId
import mk.ukim.finki.student_semester_enrollment.model.StudentRecord
import mk.ukim.finki.student_semester_enrollment.model.StudentRecordId
import mk.ukim.finki.student_semester_enrollment.model.StudentSemesterEnrollment
import mk.ukim.finki.student_semester_enrollment.model.StudentSemesterEnrollmentId
import mk.ukim.finki.student_semester_enrollment.model.StudentSubjectEnrollment
import mk.ukim.finki.student_semester_enrollment.model.StudentSubjectEnrollmentId
import org.axonframework.common.jpa.EntityManagerProvider
import org.axonframework.common.jpa.SimpleEntityManagerProvider
import org.axonframework.common.transaction.TransactionManager
import org.axonframework.eventhandling.EventBus
import org.axonframework.eventsourcing.eventstore.EventStore
import org.axonframework.messaging.annotation.ParameterResolverFactory
import org.axonframework.modelling.command.GenericJpaRepository
import org.axonframework.modelling.command.Repository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration("studentSemesterEnrollmentRepositoriesConfiguration")
class AxonRepositoriesConfiguration(@PersistenceContext val entityManager: EntityManager) {

    //Student Semester Enrrolment Repository
    @Bean("axonStudentSemesterEnrollmentRepository")
    fun studentSemesterEnrollmentGenericJpaRepository(
        eventBus: EventBus,
        parameterResolverFactory: ParameterResolverFactory
    ): Repository<StudentSemesterEnrollment> {
        return GenericJpaRepository.builder(StudentSemesterEnrollment::class.java)
            .entityManagerProvider(SimpleEntityManagerProvider(entityManager))
            .parameterResolverFactory(parameterResolverFactory)
            .eventBus(eventBus)
            .identifierConverter { StudentSemesterEnrollmentId(it) }
            .build()
    }

    //Student Subject Enrollment Repository
    @Bean("axonStudentSubjectEnrollmentRepository")
    fun studentSubjectEnrollmentRepository(
            eventBus: EventBus,
            parameterResolverFactory: ParameterResolverFactory
    ): Repository<StudentSubjectEnrollment> {
        return GenericJpaRepository.builder(StudentSubjectEnrollment::class.java)
                .entityManagerProvider(SimpleEntityManagerProvider(entityManager))
                .parameterResolverFactory(parameterResolverFactory)
                .eventBus(eventBus)
                .identifierConverter { StudentSubjectEnrollmentId(it) }
                .build()
    }

    //Student Record Repository
    @Bean
    @Qualifier("axonStudentRecordRepository")
    fun axonStudentRecordRepository(
        entityManagerProvider: EntityManagerProvider,
        eventStore: EventStore,
        transactionManager: TransactionManager
    ): Repository<StudentRecord> {
        return GenericJpaRepository.builder(StudentRecord::class.java)
            .entityManagerProvider(entityManagerProvider)
            .eventBus(eventStore)
            .identifierConverter { id -> StudentRecordId(id) }
            .build()
    }

    //Semester Repository

    @Bean("axonSemesterRepository")
    fun axonSemesterRepository(
        eventBus: EventBus,
        parameterResolverFactory: ParameterResolverFactory
    ): Repository<Semester> {
        return GenericJpaRepository.builder(Semester::class.java)
            .entityManagerProvider(SimpleEntityManagerProvider(entityManager))
            .parameterResolverFactory(parameterResolverFactory)
            .eventBus(eventBus)
            .identifierConverter { SemesterId(it) }
            .build()
    }


}
