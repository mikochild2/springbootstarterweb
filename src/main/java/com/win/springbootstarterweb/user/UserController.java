package com.win.springbootstarterweb.user;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    
  @GetMapping("/signup")
  public String showSignUpForm(User user) {
      return "add-user";
  }


  @Autowired
  private UserRepository userRepository;

  @PostMapping("/addUser")
  public String addUser(@Valid User user, BindingResult result, Model model) {
      if (result.hasErrors()) {
          return "add-user";
      }

      userRepository.save(user);
      model.addAttribute("users", userRepository.findAll());
      return "redirect:/index";
  }

  //method to fetch the User entity that matches supplied id from database.
  //if entity exists it will be passed on as a model attribute to update form view
  @GetMapping("/edit/{id}")
  public String showUpdateForm(@PathVariable("id") long id, Model model) {
      User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
      model.addAttribute("user", user);
      return "update-user";
  }

  //method to persist the entity in the database
  @PostMapping("/Update/{id}")
  public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
      if (result.hasErrors()) {
          user.setId(id);
          return "update=user";
      }

      userRepository.save(user);
      model.addAttribute("users", userRepository.findAll());
      return "redirect/index";
  }

  //method to remove the given entity
  @GetMapping("/delete/{id}")
  public String deleteUser(@PathVariable("id") long id, Model model) {
      User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
      userRepository.delete(user);
      model.addAttribute("users", userRepository.findAll());
      return "index";
  }
}