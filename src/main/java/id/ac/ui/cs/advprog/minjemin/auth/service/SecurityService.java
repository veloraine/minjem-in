package id.ac.ui.cs.advprog.minjemin.auth.service;

import id.ac.ui.cs.advprog.minjemin.auth.security.MinjeminUserDetails;

public interface SecurityService {
    MinjeminUserDetails findLoggedInUserDetails();

    void autoLogin(String username, String password);
}

