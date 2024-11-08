package com.vini.mentormentee.service;


import com.vini.mentormentee.exception.UserException;
import com.vini.mentormentee.modal.Student;

import java.util.List;

public interface StudentUserService {

    public Student findUserById(String userId) throws UserException;

    public Student findUserProfileByJwt(String jwt) throws UserException;

    public List<Student> findAllUsers();

    public Student saveStudent(Student student);

    public void deleteStudent(String id);
}

