package id.ac.ui.cs.advprog.minjemin.auth.service;

import id.ac.ui.cs.advprog.minjemin.auth.security.MinjeminUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MinjeminUserDetailsService userDetailsService;

    @Override
    public MinjeminUserDetails findLoggedInUserDetails() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails instanceof MinjeminUserDetails) {
            return (MinjeminUserDetails) userDetails;
        }
        return null;
    }

    @Override
    public void autoLogin(String username, String password) {
        var userDetails = userDetailsService.loadUserByUsername(username);
        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }
}
