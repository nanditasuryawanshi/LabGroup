package admin_user.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import admin_user.dto.UserDto;
import admin_user.model.User;
import admin_user.service.UserService;
import admin_user.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;

@Controller
@CrossOrigin("http://localhost:3000")
public class UserController {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	UserServiceImpl service;
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/registration")
	public String getRegistrationPage(@ModelAttribute("user") UserDto userDto) {
		return "register";
	}
	
	@PostMapping("/registration")
	public String saveUser(@ModelAttribute("user") UserDto userDto, Model model) {
		userService.save(userDto);
		model.addAttribute("message", "Registered Successfuly!");
		return "login";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("user-page")
	public String userPage (Model model, Principal principal) {
		List<User> allUsers = service.getAll(); // Retrieve all users from the database
	    model.addAttribute("users", allUsers); // Pass the list of users to the model
	    UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
	    model.addAttribute("user", userDetails);
	    return "user";
	}
	
	
	@GetMapping("/delete/{id}")
	public String deleteEmp(@PathVariable("id") long id,HttpSession session)
	{
		service.deleteUser(id);
		session.setAttribute("msg", "Data Deleted!!!");
		return "redirect:/admin-page";
	}
	
	
	@GetMapping("/admin-page")
	public String adminPage(Model model, Principal principal) {
	    List<User> allUsers = service.getAll(); // Retrieve all users from the database
	    model.addAttribute("users", allUsers); // Pass the list of users to the model
	    UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
	    model.addAttribute("user", userDetails);
	    return "admin";
	}
   
	  @GetMapping("/edit/{id}")
	    public String showEditForm(@PathVariable("id") Long id, Model model) {
	        User user = service.getUserById(id);
	        model.addAttribute("user", user);
	        model.addAttribute("_method", "PUT"); // Set _method to PUT for the form
	        
	        return "edit";
	    }

	
		@RequestMapping(path="/update/{id}",method = RequestMethod.POST)
		public String updateHandler(@ModelAttribute User user,@RequestParam("id") Long id) {
			service.updateStudent(user, id);
			return "redirect:/login";
		}
}
