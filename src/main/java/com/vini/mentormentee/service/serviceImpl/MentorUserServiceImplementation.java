package com.vini.mentormentee.service.serviceImpl;

import com.vini.mentormentee.config.JwtTokenProvider;
import com.vini.mentormentee.exception.UserException;
import com.vini.mentormentee.modal.Mentor;
import com.vini.mentormentee.modal.Student;
import com.vini.mentormentee.repository.MentorRepository;
import com.vini.mentormentee.service.MentorUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MentorUserServiceImplementation implements MentorUserService {

    private final MentorRepository mentorRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public MentorUserServiceImplementation(MentorRepository mentorRepository, JwtTokenProvider jwtTokenProvider) {
        this.mentorRepository = mentorRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public List<Mentor> findAllUsers() {
        return mentorRepository.findAllMentorByOrderByUid();
    }

    @Override
    public Mentor findUserProfileByJwt(String jwt) throws UserException {
        String email = jwtTokenProvider.getEmailFromJwtToken(jwt);
        Mentor mentor = mentorRepository.findByMentorEmail(email);
        if(mentor == null){
            throw new UserException("mentor not exist with email "+ email);
        }
        System.out.println("email user"+mentor.getMentorEmail());
        return mentor;
    }

    @Override
    public Mentor saveMentor(Mentor mentor) {
        return mentorRepository.save(mentor);
    }

    public boolean existsById(String id){
        return mentorRepository.existsById(id);
    }

    @Override
    public void deleteMentor(String uid) {
        if(existsById(uid)){
            mentorRepository.deleteById(uid);
        }
        else{
            throw new EntityNotFoundException("Mentor not found with id " + uid);
        }
    }

    @Override
    public Mentor findUserById(String userId) throws UserException {
        Optional<Mentor> mentor = mentorRepository.findById(userId);
        if(mentor.isPresent()){
            return mentor.get();
        }
        throw new UserException("mentor not found with id "+userId);
    }

    @Override
    public List<Student> getStudentByMentor(String mentorId) {
        Mentor mentor = mentorRepository.findById(mentorId).orElseThrow(() -> new RuntimeException("mentor not found"));
        return mentor.getStudents();
    }
}
