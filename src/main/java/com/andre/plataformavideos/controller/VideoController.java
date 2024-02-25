package com.andre.plataformavideos.controller;

import com.andre.plataformavideos.dto.VideoDto;
import com.andre.plataformavideos.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/videos")
public class VideoController {

    @Autowired
    private VideoService service;

    @GetMapping
    public ResponseEntity<Page<VideoDto>> findAll (Pageable pageable) {
        Page<VideoDto> pages = service.findAll(pageable);
        return ResponseEntity.ok(pages);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity <VideoDto> findById (@PathVariable Long id){
        VideoDto dto = service.findById(id);
        return  ResponseEntity.ok(dto);

        }


    }

