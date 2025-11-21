package com.user.Dto;

import java.util.ArrayList;
import java.util.List;

public class FallBackList {

    private static final List<ProductDTO> products = new ArrayList<>();

    public static List<ProductDTO> getProducts() {
       ProductDTO productDTO = new ProductDTO();
        productDTO.setName("abc");
        productDTO.setDescription("PQR");
        productDTO.setQuantity(12);
        products.add(productDTO);


        return products;
    }


}
