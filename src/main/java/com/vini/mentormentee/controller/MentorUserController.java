package com.vini.mentormentee.controller;

import com.vini.mentormentee.exception.UserException;
import com.vini.mentormentee.modal.Mentor;
import com.vini.mentormentee.modal.Student;
import com.vini.mentormentee.repository.MentorRepository;
import com.vini.mentormentee.service.MentorUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mentor/users")
@RequiredArgsConstructor
public class MentorUserController {

    private final MentorUserService mentorUserService;
    private final MentorRepository mentorRepository;


    @GetMapping("/all")
    public ResponseEntity<List<Mentor>> getAllUsers(@RequestHeader("Authorization") String jwt) {
        List<Mentor> mentor = mentorUserService.findAllUsers();
        return ResponseEntity.ok(mentor);
    }

    @GetMapping("/profile")
    public ResponseEntity<Mentor> getUserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException {
        Mentor mentor = mentorUserService.findUserProfileByJwt(jwt);
        return new ResponseEntity<Mentor>(mentor , HttpStatus.ACCEPTED);
    }

    @PostMapping("/create")
    public ResponseEntity<Mentor> createStudent(@RequestBody Mentor mentor) throws UserException {

        Mentor isEmailExist = mentorRepository.findByMentorEmail(mentor.getMentorEmail());
        if (isEmailExist != null) {
            throw new UserException("Email Is Already Used With Another Account");
        }
        Mentor createdMentor = mentorUserService.saveMentor(mentor);
        return  ResponseEntity.ok(createdMentor);
    }

    @PutMapping("/update")
    public ResponseEntity<Mentor> updateStudent(@RequestBody Mentor mentor){
        Mentor updateMentor = mentorUserService.saveMentor(mentor);
        return ResponseEntity.ok(updateMentor);
    }

    @DeleteMapping("/delete/{uid}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("uid") String uid) {
        System.out.println("Deleting user with id: " + uid);
        mentorUserService.deleteMentor(uid);
        return ResponseEntity.noContent().build();
    }

    //list students
    @GetMapping("{mentorId}/students")
    public List<Student> getStudentsByMentor(@PathVariable String mentorId){
        return mentorUserService.getStudentByMentor(mentorId);
    }


}
