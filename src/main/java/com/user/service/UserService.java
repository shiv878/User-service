package com.user.service;

import com.user.Dto.ProductDTO;
import com.user.Dto.UserDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {
    void addUser( UserDTO userDTO);

    List<UserDTO> getAllUser();

    UserDTO getById(Long id);

    List<ProductDTO> getProductsFromProductService();

    List<ProductDTO> serchProductByName(String name);
}
