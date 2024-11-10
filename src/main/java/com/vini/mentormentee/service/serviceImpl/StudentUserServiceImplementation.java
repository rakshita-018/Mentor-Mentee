package com.vini.mentormentee.service.serviceImpl;


import com.vini.mentormentee.config.JwtTokenProvider;
import com.vini.mentormentee.exception.UserException;
import com.vini.mentormentee.modal.Student;
import com.vini.mentormentee.repository.StudentRepository;
import com.vini.mentormentee.service.StudentUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentUserServiceImplementation implements StudentUserService {

    private final StudentRepository studentRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public StudentUserServiceImplementation(StudentRepository studentRepository, JwtTokenProvider jwtTokenProvider) {
        this.studentRepository = studentRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Student findUserById(String userId) throws UserException {
        Optional<Student> student = studentRepository.findById(userId);
        if(student.isPresent()){
            return student.get();
        }
        throw new UserException("user not found with id "+userId);
    }

    @Override
    public Student findUserProfileByJwt(String jwt) throws UserException {
        String email = jwtTokenProvider.getEmailFromJwtToken(jwt);
        Student student = studentRepository.findByStudentEmail(email);
        if(student == null){
            throw new UserException("user not exist with email "+email);
        }
        System.out.println("email user"+student.getStudentEmail());
        return student;
    }

    @Override
    public List<Student> findAllUsers() {
        return studentRepository.findAllStudentByOrderByUsn();
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public boolean existsById(String id){
        return studentRepository.existsById(id);
    }
    @Override
    public void deleteStudent(String id) {
        if(existsById(id)){
            studentRepository.deleteById(id);
        }
        else{
            throw new EntityNotFoundException("Student not found with id " + id);
        }
    }


}
