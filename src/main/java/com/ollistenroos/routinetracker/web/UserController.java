package com.ollistenroos.routinetracker.web;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ollistenroos.routinetracker.domain.NewUserForm;
import com.ollistenroos.routinetracker.domain.User;
import com.ollistenroos.routinetracker.domain.UserRepository;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "adduser")
	public String addUser(Model model) {
		model.addAttribute("newUserForm", new NewUserForm());
		return "adduser";
	}
	
	@RequestMapping(value = "editpass")
	public String editPass(Model model, Principal principal){
		User user = userRepo.findByUsername(principal.getName());
		model.addAttribute("userForm", new NewUserForm(user));
		return "editpass";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "saveuser", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("newUserForn") NewUserForm newUserForm, BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) {
			if (newUserForm.getPassword().equals(newUserForm.getPasswordCheck())) {
				String password = newUserForm.getPassword();
				BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
				String hashedPass = bc.encode(password);
				
				User user = new User();
				user.setPasswordHash(hashedPass);
				user.setUsername(newUserForm.getUsername());
				user.setRole("USER");
				if (userRepo.findByUsername(newUserForm.getUsername()) == null) {
					userRepo.save(user);
				} else {
					bindingResult.rejectValue("username", "err.username", "Username already exists");
					return "adduser";
				}
			} else {
				bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords does not match");
				return "adduser";
			}
		} else {
			return "adduser";
		}
		return "redirect:/";
	}
	
	@RequestMapping(value = "savepass", method = RequestMethod.POST)
	public String savePass(@Valid @ModelAttribute("newUserForn") NewUserForm newUserForm, BindingResult bindingResult, Principal principal) {
		if (!bindingResult.hasErrors()) {
			if (newUserForm.getPassword().equals(newUserForm.getPasswordCheck())) {
				User user = userRepo.findByUsername(principal.getName());
				String password = newUserForm.getPassword();
				BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
				String hashedPass = bc.encode(password);
				
				user.setPasswordHash(hashedPass);
				user.setUsername(newUserForm.getUsername());
				userRepo.save(user);
				
			} else {
				bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords does not match");
				return "editpass";
			}
		} else {
			return "editpass";
		}
		return "redirect:/";
	}

}
