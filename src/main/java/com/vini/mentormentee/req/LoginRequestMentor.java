package com.vini.mentormentee.req;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestMentor {
    private String email;
    private String password;
}
