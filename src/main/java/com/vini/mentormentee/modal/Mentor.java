package com.vini.mentormentee.modal;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mentors")
public class Mentor {
    @Id
    private String uid;
    private String mentorName;
    private String mentorDept;
    private String mentorPhone;
    private String mentorEmail;
    private String mentorPassword;

    @OneToMany(mappedBy = "mentor",cascade = CascadeType.ALL)
    private List<Student> students;
}
