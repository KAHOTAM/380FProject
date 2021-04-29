/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ouhk.comps380f.controller;

import java.io.IOException;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import ouhk.comps380f.model.NewUser;
import ouhk.comps380f.dao.UserRepository;

/**
 *
 * @author arman
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    UserRepository UserRepo;

    @GetMapping({"", "/list"})
    public String list(ModelMap model) {
        model.addAttribute("Users", UserRepo.findAll());
        return "listUser";
    }

    public static class Form {

        private String username;
        private String password;
        private String[] roles;
        private String fullName;
        private String phone;
        private String address;

// ... getters and setters for each of the properties
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
        

    }

    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("addUser", "User", new Form());
    }

    @PostMapping("/create")
    public View create(Form form) throws IOException {
        NewUser user = new NewUser(form.getUsername(),
                form.getPassword(), form.getRoles(), form.getFullName(),
                form.getPhone(), form.getAddress()
        );
        UserRepo.save(user);
        return new RedirectView("/user/list", true);
    }

    @GetMapping("/delete/{username}")
    public View delete(@PathVariable("username") String username) {
        UserRepo.delete(UserRepo.findById(username).orElse(null));
        return new RedirectView("/user/list", true);
    }
}
