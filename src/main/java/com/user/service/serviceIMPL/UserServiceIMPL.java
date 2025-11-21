package com.user.service.serviceIMPL;

import com.user.Dto.FallBackList;
import com.user.Dto.ProductDTO;
import com.user.Dto.UserDTO;
import com.user.entity.User;
import com.user.repositories.UserRepository;
import com.user.service.UserService;
import com.user.service.exception.UserNotFoundException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Service
public class UserServiceIMPL implements UserService {

    @Autowired
    RestTemplate restTem;




    private static final String product_service_CB = "productServicerCB";
    private static final String product_service_retry = "productServiceRetry";

    private final String URL="http://product";

private final UserRepository userRepository;

public UserServiceIMPL(UserRepository userRepository) {
    this.userRepository = userRepository;
}


Logger log = Logger.getLogger(UserServiceIMPL.class.getName());
    @Override
    public void addUser(UserDTO userDTO) {
  try {

      User user = new User();
      if(userDTO.getId()!=null){
          user.setId(userDTO.getId());
      }
      user.setFirstName(userDTO.getFirstName());
      user.setLastName(userDTO.getLastName());
      user.setEmail(userDTO.getEmail());
      user.setPassword(userDTO.getPassword());
      user.setRole(String.valueOf(User.Role.User));
      user.setCreatedDate(LocalDateTime.now());
      user.setPhoneNumber(userDTO.getPhoneNumber());

      userRepository.save(user);
  }catch (Exception e){
      throw e;
  }
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<User> getuser=userRepository.findAll();
        List<UserDTO> userDTO=new ArrayList<UserDTO>();


        getuser.forEach(user->{
            UserDTO userDTO1=new UserDTO();
            userDTO1.setId(user.getId());
            userDTO1.setFirstName(user.getFirstName());
            userDTO1.setLastName(user.getLastName());
            userDTO1.setEmail(user.getEmail());
            userDTO1.setPassword(user.getPassword());
            userDTO1.setPhoneNumber(user.getPhoneNumber());
            userDTO.add(userDTO1);
        });
        return userDTO;


    }

    @Override
    public UserDTO getById(Long id) {
    User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("user is not found for id"+id));
    UserDTO userDTO=new UserDTO();
userDTO.setId(user.getId());
    userDTO.setFirstName(user.getFirstName());
    userDTO.setLastName(user.getLastName());
    userDTO.setEmail(user.getEmail());
    userDTO.setPassword(user.getPassword());
    userDTO.setPhoneNumber(user.getPhoneNumber());

        return userDTO;
    }

    @Override
    //@Retry(name = product_service_retry)
    @CircuitBreaker(name = product_service_CB, fallbackMethod = "fallbackForGetAllProducts")
    public List<ProductDTO> getProductsFromProductService() {
        System.out.println("time--"+System.currentTimeMillis()+"---number of times"+Math.random());
        List<ProductDTO> forObject = restTem.getForObject(URL + "/getAllProduct", List.class);
        return forObject;
    }

    @Override
    //@Retry(name = product_service_CB)
    @CircuitBreaker(name = product_service_CB, fallbackMethod = "fallbackForSearchProduct")
    public List<ProductDTO> serchProductByName(String name) {

        System.out.println("time--"+System.currentTimeMillis()+"---number of times"+Math.random());
        List<ProductDTO> getAll = restTem.getForObject(URL + "/serchProductByName/{name}", List.class, name);

        return getAll;


    }

    private List<ProductDTO> fallbackForGetAllProducts(Throwable throwable) {
        log.warning("⚠️ product-service is down: " + throwable.getMessage());

        return FallBackList.getProducts();
    }


    private List<ProductDTO> fallbackForSearchProduct(String name, Throwable throwable) {
        log.warning("⚠️ product-service search fallback triggered for name=" + name + " cause=" + throwable.getMessage());

        return FallBackList.getProducts();
    }
}
