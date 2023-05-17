package com.mikho.app.rest.Controler;

import com.mikho.app.rest.Models.User;
import com.mikho.app.rest.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
public class ApiControllers {

    @Autowired
    private UserRepo userRepo;

    @GetMapping(value = "/users")
    public List<User> getUsers(){
        return userRepo.findAll();
    }
    @PostMapping(value = "/save")
    public void SaveUser(User user){
        if (isPhoneNumber(user.getPhoneNumber()) && isEmail(user.getEmail())) {
            userRepo.save(user);
        } else
            System.out.println("Invalid Email or Phone number");
    }
    @PutMapping(value = "/update/{id}")
    public void updateUser(@PathVariable long id, @RequestBody User user){
        if (isPhoneNumber(user.getPhoneNumber()) && isEmail(user.getEmail())) {
            User updated = userRepo.findById(id).get();
            updated.setName(user.getName());
            updated.setEmail(user.getEmail());
            updated.setPhoneNumber(user.getPhoneNumber());
        } else
            System.out.println("Invalid Email or Phone number");
    }
    @DeleteMapping(value = "/delete/{id}")
    public void deleteUser(@PathVariable long id){
        User deleted = userRepo.findById(id).get();
        userRepo.delete(deleted);
    }

    public boolean isPhoneNumber(String input) {
        return input.matches("[0-9]+");
    }

    public boolean isEmail(String input) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(input).matches();
    }
}
