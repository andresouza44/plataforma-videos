package com.andre.plataformavideos.service;

import com.andre.plataformavideos.dto.CategoriaDto;
import com.andre.plataformavideos.dto.VideoDto;
import com.andre.plataformavideos.entity.Categoria;
import com.andre.plataformavideos.entity.Video;
import com.andre.plataformavideos.exceptions.CategoriaNotFoundException;
import com.andre.plataformavideos.exceptions.ResourceNotFoundException;
import com.andre.plataformavideos.repositories.CategoriaRepository;
import com.andre.plataformavideos.repositories.VideoRepostitory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService {

    @Autowired
    VideoRepostitory repostitory;

    @Autowired
    CategoriaRepository categoriaRepository;


    @Transactional(readOnly = true)
    public Page<VideoDto> findAll (Pageable pageable){
        System.out.println(pageable);
        Page<Video> result = repostitory.findAll(pageable);
        System.out.println(result);
        return  result.map(video -> new VideoDto(video));
    }

    @Transactional(readOnly = true)
    public  VideoDto findById(Long id){
        Video video = repostitory.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Video not found with id: " + id));
        return  new VideoDto(video);
    }

    @Transactional(readOnly = true)
    public List<VideoDto> findByTitulo (String titulo){
        List<Video> videos = repostitory.findByTituloContainingIgnoreCase(titulo);

        return videos.stream().map(video -> new VideoDto(video)).toList();
    }

    @Transactional(readOnly = true)
    public VideoDto createVideo(VideoDto videoDto){
        Video video = new Video();
        video.setTitulo(videoDto.getTitulo());
        video.setDescricao(videoDto.getDescricao());
        video.setUrl(videoDto.getUrl());

        if (videoDto.getCategoriaId() == null) {
            saveCategoria(video, 1L);
        }
        else {
            Long categoriaId =  videoDto.getCategoriaId();
            saveCategoria(video, categoriaId);
        }

        repostitory.save(video);
        return  new VideoDto(video);
    }

    @Transactional
    public VideoDto updateVideo (Long id, VideoDto dto) {
        try{
            Video entity = repostitory.getReferenceById(id);

            if (dto.getTitulo() != null) entity.setTitulo(dto.getTitulo());
            if (dto.getDescricao() != null) entity.setDescricao(dto.getDescricao());
            if (dto.getUrl() != null) entity.setUrl(dto.getUrl());
        //    if (dto.getCategoria() != null) saveCategoria(entity,dto.getCategoria().getId());
            if (dto.getCategoriaId() != null) saveCategoria(entity,dto.getCategoriaId());
            repostitory.save(entity);

            return new VideoDto(entity);

        }catch (EntityNotFoundException e){
            throw  new ResourceNotFoundException("Video not found with id: " + id);
        }
    }

    public void deleteById (Long id){
        if (!repostitory.existsById(id)){
            throw new ResourceNotFoundException("Video not found with id: " + id);
        }
        try{
            repostitory.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw  new DataIntegrityViolationException("Data Integrity Violation Excpetion" + e.getMessage());
        }
    }

    private void saveCategoria(Video entity, Long id){
        if(!categoriaRepository.existsById(id)) {
            throw new CategoriaNotFoundException("Categoria not found with id: " + id);
        }
        try {
                Categoria categoria = categoriaRepository.findById(id).get();
                entity.setCategoria(categoria);

        }catch (NullPointerException | EntityNotFoundException e) {
            throw  new CategoriaNotFoundException("Categoria not found with id: " + id);

        }
    }
}