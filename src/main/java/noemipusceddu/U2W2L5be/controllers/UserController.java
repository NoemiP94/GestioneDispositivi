package noemipusceddu.U2W2L5be.controllers;

import noemipusceddu.U2W2L5be.entities.User;
import noemipusceddu.U2W2L5be.exceptions.BadRequestException;
import noemipusceddu.U2W2L5be.payloads.user.UserDTO;
import noemipusceddu.U2W2L5be.payloads.user.UserResponseDTO;
import noemipusceddu.U2W2L5be.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO saveUser(@RequestBody @Validated UserDTO userBody, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestException("Errori nel payload! Ricontrollare!");
        }else{
            User user = userService.save(userBody);
            return new UserResponseDTO(user.getIdUser());
        }
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable UUID id){
        return userService.findById(id);
    }

    @GetMapping
    public Page<User> findAllUsers(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "name") String orderBy){
        return userService.findAllUsers(page, size, orderBy);
    }

    @PutMapping("/{id}")
    public User findUserAndUpdate(@PathVariable UUID id, @RequestBody UserDTO modifiedUserPayload){
        return userService.findByIdAndUpdate(id, modifiedUserPayload);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findUserAndDelete(@PathVariable UUID id){
        userService.findByIdAndDelete(id);
    }

    @PostMapping("/{id}/upload")
    public String uploadAvatar(@RequestParam("avatar") MultipartFile file, @PathVariable UUID id) throws IOException {
        return userService.uploadImage(file);
    }
}
