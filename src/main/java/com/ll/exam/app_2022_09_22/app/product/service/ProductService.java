package com.ll.exam.app_2022_09_22.app.product.service;

import com.ll.exam.app_2022_09_22.app.product.entity.Product;
import com.ll.exam.app_2022_09_22.app.product.entity.ProductOption;
import com.ll.exam.app_2022_09_22.app.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product create(String name, int salePrice, int wholesalePrice , String makerShopName, List<ProductOption> options) {

        int price = (int) Math.ceil(wholesalePrice * 1.6) / 100 * 100; // 십원 단위는 날린다.

        Product product = Product.builder()
                .name(name)
                .salePrice(salePrice) // 도매가
                .price(price) // 권장소비자가
                .wholesalePrice(wholesalePrice)
                .makerShopName(makerShopName)
                .build();

        for ( ProductOption option : options ) {
            product.addOption(option);
        }

        productRepository.save(product);

        return product;
    }

}
