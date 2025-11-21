package com.user.controller;

import com.user.Dto.ProductDTO;
import com.user.Dto.UserDTO;
import com.user.service.UserService;
import com.user.service.exception.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class UserController {



    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/addUser")
    public ResponseEntity<String> saveUser(@RequestBody @Valid UserDTO userDTO) {
        try{
            userService.addUser(userDTO);
            return new ResponseEntity<String>("User has been saved successfully", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/getAllUser")
    public ResponseEntity<List<UserDTO>> getAllUser(){
        List<UserDTO> users=userService.getAllUser();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            UserDTO userDTO = userService.getById(id);
            return new ResponseEntity<>(userDTO,HttpStatus.OK);
        }catch (UserNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }



    }
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,@RequestBody @Valid UserDTO userDTO) {
      UserDTO  user=userService.getById(id);
      if(userDTO==null){
          return new ResponseEntity<>("user is not available for id="+id,HttpStatus.NOT_FOUND);
      }
      user.setId(id);
        user.setFirstName(userDTO.getFirstName());
      user.setLastName(userDTO.getLastName());
      user.setEmail(userDTO.getEmail());
      user.setPassword(userDTO.getPassword());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        userService.addUser(user);

        return new ResponseEntity<>(user,HttpStatus.OK);


    }
@GetMapping("/getAllProduct")
public ResponseEntity<List<ProductDTO>> getAllProducts(){
  List<ProductDTO>  allProduct=userService.getProductsFromProductService();
        return new ResponseEntity<List<ProductDTO>>(allProduct,HttpStatus.OK);
}

@GetMapping("/sercgByName/{name}")
    public ResponseEntity<List<ProductDTO>> getSercgByName(@PathVariable String name){

   List<ProductDTO> list=userService.serchProductByName(name);
   return new ResponseEntity<>(list,HttpStatus.OK);

}


}
