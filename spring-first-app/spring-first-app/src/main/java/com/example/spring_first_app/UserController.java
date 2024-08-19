package com.example.spring_first_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@org.springframework.stereotype.Controller
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Mapping for the index page
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Mapping for the registration page
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    // Mapping for the login page
    @GetMapping("/login")
    public String logIn() {
        return "logIn";
    }

    // Mapping for the profile page
    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    // Mapping for the dashboard page
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    // Mapping for handling user registration
    @PostMapping("/register")
    public String registerUser(@RequestParam("email") String email,
                               @RequestParam("firstName") String firstName,
                               @RequestParam("lastName") String lastName,
                               @RequestParam("password") String password,
                               @RequestParam("confirmPassword") String confirmPassword) {

        // Check if email is already registered
        Users existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
            // Redirect back to the registration page with error message
            return "redirect:/register?error=emailExists";
        }

        // Adjust to organize in the database
        email = email.toLowerCase();
        firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
        lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();

        // Save the user to the database
        Users newUser = new Users();
        newUser.setEmail(email);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setPassword(password);
        newUser.setConfirmPassword(confirmPassword);

        userRepository.save(newUser);

        // Redirect to login page after registration
        return "redirect:/login";
    }

    // Mapping for handling user login
    @PostMapping("/login")
    public ModelAndView loginUser(@RequestParam("email") String email,
                                  @RequestParam("password") String password) {

        // Find user by email and password
        Users user = userRepository.findByEmailAndPassword(email, password);

        // Check if user exists and password matches
        if (user == null || !user.getPassword().equals(password)) {
            return new ModelAndView("redirect:/login?error=emailNotExistent");
        }

        // Check if it's the user's first login
        if (user.isFirstLogin()) {
            // Update the firstLogin flag
            user.setFirstLogin(false);
            userRepository.save(user);

            ModelAndView modelAndView = new ModelAndView("redirect:/profile");
            modelAndView.addObject("firstName", user.getFirstName()); // Pass firstName to profile page
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
            modelAndView.addObject("firstName", user.getFirstName()); // Pass firstName to dashboard
            return modelAndView;
        }
    }

    // Mapping for saving first login information
    @PostMapping("/profile")
    public String saveFirstLoginInfo(@RequestParam("email") String email,
                                     @RequestParam("dob") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob,
                                     @RequestParam("sex") String sex,
                                     @RequestParam("height") Double height,
                                     @RequestParam("weight") Double weight,
                                     @RequestParam("hip") Double hip,
                                     @RequestParam("waist") Double waist,
                                     @RequestParam("goal") String goal,
                                     @RequestParam("pal") String pal) {
        // Find the user by email
        Users user = userRepository.findById(email).orElse(null);

        // Update the user's additional information if user exists
        if (user != null) {
            user.setDob(dob);
            user.setSex(sex);
            user.setHeight(height);
            user.setWeight(weight);
            user.setHip(hip);
            user.setWaist(waist);
            user.setGoal(goal);
            user.setPal(pal);

            // Save the changes
            userRepository.save(user);
        }

        // Redirect to the dashboard
        return "redirect:/dashboard";
    }
}