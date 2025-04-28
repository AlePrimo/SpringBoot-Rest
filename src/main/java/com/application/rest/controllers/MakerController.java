package com.application.rest.controllers;

import com.application.rest.controllers.dto.MakerDTO;
import com.application.rest.entities.Maker;
import com.application.rest.services.IMakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/maker")
public class MakerController {

    @Autowired
    private IMakerService makerService;


    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Optional<Maker> makerOptional = makerService.findById(id);  //PRIMERO OBTENEMOS EL OPTIONAL DE TIPO MAKER

        if (makerOptional.isPresent()) { //  SI EL OBJETO EXISTE EN BASE DE DATOS DEBEMOS RETORNARLO PERO PARA ESO DEBEMOS CREAR UNA CLASE DE TIPO DTO(DATA TRANSFER OBJECT)

            Maker maker = makerOptional.get(); //OBTENEMOS EL OBJETO MAKER DEL OPTIONAL

            MakerDTO makerDTO = MakerDTO.builder() //CREAMOS EL DTO A PARTIR DE LOS ATRIBUTOS DEL MAKER
                    .id(maker.getId())
                    .name(maker.getName())
                    .products(maker.getProducts())
                    .build();

            return ResponseEntity.ok(makerDTO);
        }


        return ResponseEntity.notFound().build();
    }


    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {

        List<MakerDTO> makerDTOList = makerService.findAll()
                .stream()
                .map(maker -> MakerDTO.builder()
                        .id(maker.getId())
                        .name(maker.getName())
                        .products(maker.getProducts())
                        .build()).toList();


        return ResponseEntity.ok(makerDTOList);


    }



    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody MakerDTO makerDTO) throws URISyntaxException {

        if(makerDTO.getName().isBlank()){
            return ResponseEntity.badRequest().build();
        }

        makerService.save(Maker.builder().name(makerDTO.getName()).build());

        return ResponseEntity.created(new URI("/api/maker/save")).build();

    }




   @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMaker(@PathVariable Long id, @RequestBody MakerDTO makerDTO){

        Optional<Maker> makerOptional = makerService.findById(id);
        if (makerOptional.isPresent()){
            Maker maker = makerOptional.get();
            maker.setName(makerDTO.getName());
            makerService.save(maker);
            return ResponseEntity.ok("Registro Actualizado");
        }

        return ResponseEntity.notFound().build();

}

@DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){

    Optional<Maker> makerOptional = makerService.findById(id);

    if (id!= null && makerOptional.isPresent()){
        makerService.deleteById(id);
        return ResponseEntity.ok("Registro Eliminado");
    }

    return ResponseEntity.notFound().build();

}












}
