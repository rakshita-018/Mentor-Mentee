// src/main/java/com/vini/backend/repositories/StudentRepository.java
package com.vini.mentormentee.repository;

import com.vini.mentormentee.modal.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    public Student findByStudentEmail(String email);

    public List<Student> findAllStudentByOrderByUsn();

    public  Student findByUsn(String usn);

}
