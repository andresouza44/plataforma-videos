package com.andre.plataformavideos.controller;

import com.andre.plataformavideos.dto.CategoriaDto;
import com.andre.plataformavideos.dto.VideoDto;
import com.andre.plataformavideos.service.CategoriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping(value = "/categorias")
@CrossOrigin("http://localhost:3000")
public class CategoriaController {

    @Autowired
    private CategoriasService service;

    @GetMapping(value = "/")
    public ResponseEntity<List<CategoriaDto>> findAllCategpras(){
        List<CategoriaDto> dto = service.findAllCategorias();
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping (value = "/{id}/")
    public ResponseEntity<CategoriaDto> findById (@PathVariable Long id){
        CategoriaDto dto = service.FindById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/{id}/videos/")
    public ResponseEntity <List<VideoDto>> getVideosBuCategoriaId(@PathVariable Long id){

        List<VideoDto> videos = service.findVideosByCategoriaId(id);
        return ResponseEntity.ok(videos);
    }

    @PostMapping
    public ResponseEntity<CategoriaDto> createCategoria (@RequestBody CategoriaDto dto){
        dto = service.createCategoria(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateCategoria (@PathVariable Long id, @RequestBody CategoriaDto dto){

        dto = service.updateCategoria(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping(value = "/{id}/")
    public ResponseEntity<Void> deleteById (@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
