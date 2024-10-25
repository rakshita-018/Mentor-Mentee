package com.vini.mentormentee.controller;


import com.vini.mentormentee.exception.UserException;
import com.vini.mentormentee.modal.Student;
import com.vini.mentormentee.service.StudentUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/student/users")
public class StudentUserController {
    private StudentUserService studentUserService;

    public StudentUserController(StudentUserService studentUserService) {
        this.studentUserService = studentUserService;
    }

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


}
