package com.gl.ManyToManyDemo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
public class ManyToManyDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManyToManyDemoApplication.class, args);
    }

}

@RestController
class ManyToManyMappingController {
    StudentRepo studentRepo;
    CourseRepo courseRepo;

    public ManyToManyMappingController(StudentRepo studentRepo, CourseRepo courseRepo) {
        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
    }

    @GetMapping("/courses")
    public List<Course> allCourses() {
        return courseRepo.findAll();
    }

    @PostMapping("/courses")
    public Course createCourse(@RequestBody Course course) {
        return courseRepo.save(course);
    }

    @GetMapping("/students")
    public List<Student> allStudents() {
        List<Student> all = studentRepo.findAll();
        System.out.println("all = " + all);
        return studentRepo.findAll();
    }

    @PostMapping("/students")
    public Student createStudent(@RequestBody Student student) {
        return studentRepo.save(student);
    }

    @PutMapping("/mappings-student-with-course")
    public Student mapping(@RequestParam Long studentId, @RequestParam Long courseId) {
        Student student = studentRepo.findById(studentId).orElseThrow(() -> new RuntimeException("studentId (" + studentId + ") is not found: "));
        Course course = courseRepo.findById(courseId).orElseThrow(() -> new RuntimeException("courseId (" + courseId + ") is not found: "));

        student.courses.add(course);

        return studentRepo.save(student);
    }
}

interface StudentRepo extends JpaRepository<Student, Long> {
}

interface CourseRepo extends JpaRepository<Course, Long> {
}

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
class Student {
    @Id
    @GeneratedValue
    Long id;
    String name;

    //@JsonManagedReference
    @JsonIgnoreProperties(value = "students")
    @ManyToMany
    List<Course> courses;
}

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "name")
class Course {
    @Id
    @GeneratedValue
    Long id;
    String name;

    //@JsonIgnore
    //@JsonBackReference
    @ToString.Exclude
    @JsonIgnoreProperties(value = "courses")
    @ManyToMany(mappedBy = "courses")
    List<Student> students;
}
