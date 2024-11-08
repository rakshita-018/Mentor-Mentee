package com.vini.mentormentee.repository;

import com.vini.mentormentee.modal.Mentor;
import com.vini.mentormentee.modal.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentorRepository extends JpaRepository<Mentor, String> {

    public Mentor findByMentorEmail(String email);

    public List<Mentor> findAllMentorByOrderByUid();

}
