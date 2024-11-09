package com.vini.mentormentee.controller;


import com.vini.mentormentee.exception.UserException;
import com.vini.mentormentee.modal.Student;
import com.vini.mentormentee.repository.StudentRepository;
import com.vini.mentormentee.service.StudentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/users")
@RequiredArgsConstructor
public class StudentUserController {
    private final StudentUserService studentUserService;
    private final StudentRepository studentRepository;


    @GetMapping("/all")
    public ResponseEntity<List<Student>> getAllUsers(@RequestHeader("Authorization") String jwt) {
        System.out.println("/api/student/users/all");
        List<Student> student = studentUserService.findAllUsers();
        return ResponseEntity.ok(student);
    }

    @GetMapping("/profile")
    public ResponseEntity<Student> getUserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException {
        System.out.println("/api/student/users/profile");
        Student student = studentUserService.findUserProfileByJwt(jwt);
        return new ResponseEntity<Student>(student,HttpStatus.ACCEPTED);
    }

    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) throws UserException {

        Student isEmailExist = studentRepository.findByStudentEmail(student.getStudentEmail());
        if (isEmailExist != null) {
            throw new UserException("Email Is Already Used With Another Account");
        }
        Student createdStudent = studentUserService.saveStudent(student);
        return  ResponseEntity.ok(createdStudent);
    }

    @PutMapping("/update")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student){
        Student updateStudent = studentUserService.saveStudent(student);
        return ResponseEntity.ok(updateStudent);
    }

    @DeleteMapping("/delete/{usn}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("usn") String usn) {
        System.out.println("Deleting user with id: " + usn);
        studentUserService.deleteStudent(usn);
        return ResponseEntity.noContent().build();
    }


}
