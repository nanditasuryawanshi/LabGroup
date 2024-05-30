package admin_user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import admin_user.dto.UserDto;
import admin_user.model.User;
import admin_user.repositories.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService 
{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User save(UserDto userDto) {
		User user = new User(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()) , userDto.getRole(), userDto.getFullname(),userDto.getLastname(), userDto.getCourse(), userDto.getCountry());
		return userRepository.save(user);
	}
	
	public List<User> getAll()
	{
		return (List<User>)userRepository.findAll();
	}

	public void deleteUser(long id) {
		userRepository.deleteById(id);
		
	}

	public User findById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.getById(id);
	}

	
	public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }



@Override
public User updateUser(String email, UserDto userDto) {
	// TODO Auto-generated method stub
	return null;
}


@Transactional
public User updateStudent(User user, Long id) {
	// Check if the password needs to be encoded
    if (!user.getPassword().startsWith("$2a$")) {
        // Password is not encoded, so encode it
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }
	return userRepository.save(user);
}
}











































