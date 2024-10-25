package com.vini.mentormentee.modal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    private String usn;
    private String studentName;
    private String studentPhone;
    private String studentEmail;
    private Long studentBatch;
    private String studentPassword;
    private String department;
}
