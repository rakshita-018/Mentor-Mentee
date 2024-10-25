package com.vini.mentormentee.controller;



import com.vini.mentormentee.config.JwtTokenProvider;
import com.vini.mentormentee.exception.UserException;
import com.vini.mentormentee.modal.Student;
import com.vini.mentormentee.repository.StudentRepository;
import com.vini.mentormentee.req.LoginRequestStudent;
import com.vini.mentormentee.res.AuthResponse;
import com.vini.mentormentee.service.serviceImpl.StudentDetailsService;
import jakarta.validation.Valid;
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

@RestController
@RequestMapping("/auth/student")
public class StudentAuthController {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final StudentDetailsService studentDetailsService;

    public StudentAuthController(StudentRepository studentRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, StudentDetailsService studentDetailsService) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.studentDetailsService = studentDetailsService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createStudentHandler(@Valid @RequestBody Student student) throws UserException {
        String email = student.getStudentEmail();
        String password = student.getStudentPassword();
        String usn = student.getUsn();
        String studentName = student.getStudentName();
        String studentPhone = student.getStudentPhone();
        Long studentBatch = student.getStudentBatch();


        Student isEmailExist = studentRepository.findByStudentEmail(email);

        if (isEmailExist != null) {
            throw new UserException("Email Is Already Used With Another Account");
        }

        student.setStudentPassword(passwordEncoder.encode(password));
        student.setStudentEmail(email);
        student.setUsn(usn);
        student.setStudentName(studentName);
        student.setStudentPhone(studentPhone);
        student.setStudentBatch(studentBatch);
        Student savedStudent = studentRepository.save(student);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse(token, true);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequestStudent loginRequestStudent) {
        String username = loginRequestStudent.getEmail();
        String password = loginRequestStudent.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setStatus(true);
        authResponse.setJwt(token);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = studentDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}

