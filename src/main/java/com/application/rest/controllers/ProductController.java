package com.application.rest.controllers;




import com.application.rest.controllers.dto.MakerDTO;
import com.application.rest.controllers.dto.ProductDTO;
import com.application.rest.entities.Maker;
import com.application.rest.entities.Product;
import com.application.rest.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private IProductService productService;


    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){

    Optional<Product> productOptional = productService.findById(id);

    if(productOptional.isPresent()){
        Product product = productOptional.get();
        ProductDTO productDTO = ProductDTO.builder()
                .id(product.getId())
                .price(product.getPrice())
                .maker(product.getMaker())
                .build();

         return ResponseEntity.ok(productDTO);
    }


return ResponseEntity.notFound().build();

}


    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {

        List<ProductDTO> productDTOList = productService.findAll()
                .stream()
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .maker(product.getMaker())
                        .build()
                ).toList();


        return ResponseEntity.ok(productDTOList);


    }


    @GetMapping("/findInRange")
    public ResponseEntity<?> findProductInRange(@RequestParam BigDecimal minPrice, @RequestParam BigDecimal maxPrice){

        List<ProductDTO> productList = productService.findByPriceInRange(minPrice,maxPrice)
                .stream().map(product -> ProductDTO.builder()
                .name(product.getName())
                .price(product.getPrice())
                .maker(product.getMaker())
                .build())
                .toList();

if (productList.isEmpty()){
    return ResponseEntity.notFound().build();
}
        return ResponseEntity.ok(productList);
    }


    @PostMapping("/save")
    public ResponseEntity<?> saveProduct(@RequestBody ProductDTO productDTO) throws URISyntaxException {


        if(productDTO.getName().isBlank() || productDTO.getPrice() == null || productDTO.getMaker() == null){
            return ResponseEntity.badRequest().build();
        }

        productService.save(Product.builder()
                        .name(productDTO.getName())
                        .price(productDTO.getPrice())
                        .maker(productDTO.getMaker())
                         .build());

        return ResponseEntity.created(new URI("api/product/save")).build();

    }


    @PutMapping("/update/{id}")
    private ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        Optional<Product> productOptional = productService.findById(id);

        if(productOptional.isPresent()){
            Product product = productOptional.get();
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setMaker(productDTO.getMaker());
            productService.save(product);
            return ResponseEntity.ok("Registro Actualizado");

        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){

        Optional<Product> productOptional = productService.findById(id);

        if (id!= null && productOptional.isPresent()){
            productService.deleteById(id);
            return ResponseEntity.ok("Registro Eliminado");
        }

        return ResponseEntity.notFound().build();

    }







}
