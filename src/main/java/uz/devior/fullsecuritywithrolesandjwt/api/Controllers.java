package uz.devior.fullsecuritywithrolesandjwt.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class Controllers {

    @PostMapping("/admin")
    public String postAdmin(){
        return "Admin::post";
    }

    @GetMapping("/admin")
    public String getAdmin(){
        return "Admin::get";
    }

    @PutMapping("/admin")
    public String putAdmin(){
        return "Admin::put";
    }

    @DeleteMapping("/admin")
    public String deleteAdmin(){
        return "Admin::delete";
    }

    @PostMapping("/user")
    public String postUser(){
        return "User::post";
    }

    @GetMapping("/user")
    public String getUser(){
        return "User::get";
    }

    @PutMapping("/user")
    public String putUser(){
        return "User::put";
    }

    @DeleteMapping("/user")
    public String deleteUser(){
        return "User::delete";
    }



}
