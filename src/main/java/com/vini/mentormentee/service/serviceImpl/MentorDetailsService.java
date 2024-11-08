package com.vini.mentormentee.service.serviceImpl;

import com.vini.mentormentee.modal.Mentor;
import com.vini.mentormentee.repository.MentorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MentorDetailsService implements UserDetailsService {

    @Autowired
    private final MentorRepository mentorRepository;

    public MentorDetailsService(MentorRepository mentorRepository) {
        this.mentorRepository = mentorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Mentor mentor = mentorRepository.findByMentorEmail(username);
        if (mentor == null) {
            throw new UsernameNotFoundException("Mentor not found with email " + username);
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(mentor.getMentorEmail(),mentor.getMentorPassword(), authorities);
    }
}
