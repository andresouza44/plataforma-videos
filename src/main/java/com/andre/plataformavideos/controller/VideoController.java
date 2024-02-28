package com.andre.plataformavideos.controller;

import com.andre.plataformavideos.dto.VideoDto;
import com.andre.plataformavideos.entity.Video;
import com.andre.plataformavideos.service.VideoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/videos")
@CrossOrigin("*")
public class VideoController {

    @Autowired
    private VideoService service;

    @GetMapping
    public ResponseEntity<Page<VideoDto>> findAll (@RequestParam(name="size", defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(0,size);
        Page<VideoDto> pages = service.findAll(pageable);
        return ResponseEntity.ok(pages);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity <VideoDto> findById (@PathVariable Long id){
        VideoDto dto = service.findById(id);

        return  ResponseEntity.ok(dto);

        }

    @GetMapping(value = "/")
    public ResponseEntity <List<VideoDto>> searcheByTitulo (
            @RequestParam(name="search", defaultValue = "") String titulo){
        List<VideoDto> videoDtoList = service.findByTitulo(titulo);
        return ResponseEntity.ok(videoDtoList);
    }


    @PostMapping
    public ResponseEntity<VideoDto> createVideo (@Valid @RequestBody VideoDto dto){
        dto = service.createVideo(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return  ResponseEntity.created(uri).body(dto);

    }

    @PutMapping (value = "/{id}")
    public ResponseEntity<VideoDto> updateVideo ( @Valid @PathVariable  Long id, @RequestBody VideoDto dto){
        VideoDto result = service.updateVideo(id, dto);
        return ResponseEntity.ok(dto);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteById( @PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    }

