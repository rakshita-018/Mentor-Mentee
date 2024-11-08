package com.vini.mentormentee.service;


import com.vini.mentormentee.exception.UserException;
import com.vini.mentormentee.modal.Mentor;
import com.vini.mentormentee.modal.Student;

import java.util.List;

public interface MentorUserService {

    public List<Mentor> findAllUsers();

    public Mentor findUserProfileByJwt(String jwt) throws UserException;

    public Mentor saveMentor(Mentor mentor);

    public void deleteMentor(String uid);

    public Mentor findUserById(String userId) throws UserException;

    public List<Student> getStudentByMentor(String mentorId);
}
