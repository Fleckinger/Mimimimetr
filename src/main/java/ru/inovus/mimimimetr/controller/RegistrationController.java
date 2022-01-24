package ru.inovus.mimimimetr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.inovus.mimimimetr.dto.UserDto;
import ru.inovus.mimimimetr.entity.User;
import ru.inovus.mimimimetr.security.UserRole;
import ru.inovus.mimimimetr.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model, Principal principal) {
        model.addAttribute("userDto", new UserDto());
        return principal == null ? "registration" : "redirect:voting";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute @Valid UserDto userDto, BindingResult bindingResult) {
        if (!userDto.getEmail().equals(userDto.getConfirmedEmail())) {
            bindingResult.rejectValue("confirmedEmail", "Emails don't match",
                    "Введенные адреса электронной почты не совпадают");
        } else if (userService.isEmailExist(userDto.getEmail())) {
            bindingResult.rejectValue("email", "Email already exists",
                    "Адрес электронной почты уже используется");
        }

        if (!userDto.getPassword().equals(userDto.getConfirmedPassword())) {
            bindingResult.rejectValue("confirmedPassword", "Passwords don't match",
                    "Введенные пароли не совпадают");
        }

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setVotes(new HashSet<>());
        user.setRole(UserRole.ROLE_USER);
        userService.registerNewUser(user);

        return "login";
    }
}
