package mk.ukim.finki.student_semester_enrollment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class StudentSemesterEnrollmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentSemesterEnrollmentApplication.class, args);
    }

}