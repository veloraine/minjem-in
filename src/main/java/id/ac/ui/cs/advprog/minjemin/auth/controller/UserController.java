package id.ac.ui.cs.advprog.minjemin.auth.controller;

import id.ac.ui.cs.advprog.minjemin.auth.exception.*;
import id.ac.ui.cs.advprog.minjemin.auth.model.User;
import id.ac.ui.cs.advprog.minjemin.auth.model.UserDTO;
import id.ac.ui.cs.advprog.minjemin.auth.security.MinjeminUserDetails;
import id.ac.ui.cs.advprog.minjemin.auth.service.SecurityService;
import id.ac.ui.cs.advprog.minjemin.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/userDetail")
    public ResponseEntity<MinjeminUserDetails> userDetails() {
        return ResponseEntity.ok(securityService.findLoggedInUserDetails());
    }

    @GetMapping("/register")
    public String register(Model model) {
        var userDTO = new User();
        model.addAttribute("user", userDTO);
        model.addAttribute("message", "");
        return "auth/register";
    }

    @GetMapping("/users")
    @ResponseBody
    public ResponseEntity<Iterable<User>> getListUser() {
        return ResponseEntity.ok(userService.getListUser());
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute("user") UserDTO userDto) {
        User user;
        try{
            user = userService.validate(userDto);
        } catch (UserAlreadyExistException | WhitespaceValueException | UnmatchPasswordException e){
            var mav = new ModelAndView("auth/register");
            mav.addObject("user", userDto);
            mav.addObject("message", e.getMessage());
            return mav;
        }

        userService.register(user);
        securityService.autoLogin(userDto.getUsername(), userDto.getPasswordConfirm());

        return new ModelAndView("redirect:/", "user", userDto);
    }

    @GetMapping("/login")
    public String login(Model model) {
        var userDTO = new User();
        model.addAttribute("user", userDTO);
        model.addAttribute("message", "");
        return "auth/login";
    }

}
