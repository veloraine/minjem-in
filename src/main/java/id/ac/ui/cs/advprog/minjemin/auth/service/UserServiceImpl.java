package id.ac.ui.cs.advprog.minjemin.auth.service;


import id.ac.ui.cs.advprog.minjemin.auth.exception.UnmatchPasswordException;
import id.ac.ui.cs.advprog.minjemin.auth.exception.UserAlreadyExistException;
import id.ac.ui.cs.advprog.minjemin.auth.exception.WhitespaceValueException;
import id.ac.ui.cs.advprog.minjemin.auth.model.User;
import id.ac.ui.cs.advprog.minjemin.auth.model.UserDTO;
import id.ac.ui.cs.advprog.minjemin.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User dengan username + " + username + "tidak ditemukan!");
        }
        return user.get();
    }

    @Override
    public User register(User user){
        return userRepository.save(user);
    }

    @Override
    public Iterable<User> getListUser() {
        return userRepository.findAll();
    }

    @Override
    public User validate(UserDTO userDto)
            throws UserAlreadyExistException, WhitespaceValueException, UnmatchPasswordException {
        try{
            userRepository.findByUsername(userDto.getUsername());
        }catch (Exception e) {
            throw new UserAlreadyExistException();
        }

        if(userDto.getUsername().isBlank() || userDto.getPassword().isBlank()){
            throw new WhitespaceValueException();
        }

        if(!userDto.getPassword().equals(userDto.getPasswordConfirm())){
            throw new UnmatchPasswordException();
        }

        String encryptedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
        return new User(userDto.getUsername(), encryptedPassword, userDto.getRoles());
    }

}
