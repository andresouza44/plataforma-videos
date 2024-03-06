package com.andre.plataformavideos.controller;

import com.andre.plataformavideos.dto.VideoDto;
import com.andre.plataformavideos.entity.Video;
import com.andre.plataformavideos.service.VideoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping
    public ResponseEntity<Page<VideoDto>> findAll (@RequestParam(name="size", defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(0,size);
        Page<VideoDto> pages = service.findAll(pageable);
        return ResponseEntity.ok(pages);

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity <VideoDto> findById (@PathVariable Long id){
        VideoDto dto = service.findById(id);

        return  ResponseEntity.ok(dto);

        }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(value = "/")
    public ResponseEntity <List<VideoDto>> searcheByTitulo (
            @RequestParam(name="search", defaultValue = "") String titulo){
        List<VideoDto> videoDtoList = service.findByTitulo(titulo);
        return ResponseEntity.ok(videoDtoList);
    }

    @GetMapping(value = "/free")
    public ResponseEntity<List<VideoDto>> freeVideos (){
        List<VideoDto> videoDtos = service.getFreeVideos();
        return ResponseEntity.ok(videoDtos);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<VideoDto> createVideo (@Valid @RequestBody VideoDto dto){
        dto = service.createVideo(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return  ResponseEntity.created(uri).body(dto);

    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping (value = "/{id}")
    public ResponseEntity<VideoDto> updateVideo ( @Valid @PathVariable  Long id, @RequestBody VideoDto dto){
        VideoDto result = service.updateVideo(id, dto);
        return ResponseEntity.ok(dto);

    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteById( @PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    }

