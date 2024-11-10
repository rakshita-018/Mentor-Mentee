package com.vini.mentormentee.controller;

import com.vini.mentormentee.config.JwtTokenProvider;
import com.vini.mentormentee.exception.UserException;
import com.vini.mentormentee.modal.Mentor;
import com.vini.mentormentee.modal.Student;
import com.vini.mentormentee.repository.MentorRepository;
import com.vini.mentormentee.req.LoginRequestMentor;
import com.vini.mentormentee.res.AuthResponse;
import com.vini.mentormentee.service.serviceImpl.MentorDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/auth/mentor")
@RequiredArgsConstructor
public class MentorAuthController {
    private final PasswordEncoder passwordEncoder;
    private final MentorRepository mentorRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final MentorDetailsService mentorDetailsService;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createMentorHandler(@Valid @RequestBody Mentor mentor) throws UserException {
        String email = mentor.getMentorEmail();
        String password = mentor.getMentorPassword();
        String name = mentor.getMentorName();
        String phone = mentor.getMentorPhone();
        String dept = mentor.getMentorDept();
        String uid = mentor.getUid();
        List<Student> Studentlist = mentor.getStudents();

        Mentor isEmailExist = mentorRepository.findByMentorEmail(email);

        if (isEmailExist != null) {
            throw new UserException("Email Is Already Used With Another Account");
        }

        mentor.setMentorPassword(passwordEncoder.encode(password));
        mentor.setMentorEmail(email);
        mentor.setMentorName(name);
        mentor.setMentorPhone(phone);
        mentor.setMentorDept(dept);
        mentor.setUid(uid);
        mentor.setStudents(Studentlist);
        Mentor savedMentor = mentorRepository.save(mentor);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse(token, true);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequestMentor loginRequestMentor) {
        String username = loginRequestMentor.getEmail();
        String password = loginRequestMentor.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setStatus(true);
        authResponse.setJwt(token);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = mentorDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
