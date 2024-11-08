package com.vini.mentormentee.modal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "students")
public class Student {
    @Id
    private String usn;
    private String studentName;
    private String studentPhone;
    private String studentEmail;
    private Long studentBatch;
    private String studentPassword;
    private String department;
    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;

}
