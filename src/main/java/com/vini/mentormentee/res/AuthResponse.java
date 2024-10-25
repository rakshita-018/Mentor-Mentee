package com.vini.mentormentee.res;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
	private String jwt;
	private boolean status;
}
