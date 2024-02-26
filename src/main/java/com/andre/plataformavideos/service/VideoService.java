package com.andre.plataformavideos.service;

import com.andre.plataformavideos.dto.VideoDto;
import com.andre.plataformavideos.entity.Video;
import com.andre.plataformavideos.exceptional.ResourceNotFoundException;
import com.andre.plataformavideos.repositories.VideoRepostitory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VideoService {

    @Autowired
    VideoRepostitory repostitory;

    @Transactional(readOnly = true)
    public Page<VideoDto> findAll (Pageable pageable){
        Page<Video> result = repostitory.findAll(pageable);
        return  result.map(video -> new VideoDto(video));
    }

    @Transactional(readOnly = true)
    public  VideoDto findById(Long id){
        Video video = repostitory.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Video not found with id: " + id));
        return  new VideoDto(video);
    }

    public VideoDto createVideo(VideoDto videoDto){
        Video video = new Video();
        video.setTitulo(videoDto.getTitulo());
        video.setDescricao(videoDto.getDescricao());
        video.setUrl(videoDto.getUrl());
        repostitory.save(video);
        return  new VideoDto(video);

    }
    @Transactional
    public VideoDto updateVideo (Long id, VideoDto dto) {

        try{
            //Video entity = repostitory.findById(id).get();
            Video entity = repostitory.getReferenceById(id);
            System.out.println("Service: " + entity);
            if (dto.getTitulo() != null) entity.setTitulo(dto.getTitulo());
            if(dto.getDescricao() != null) entity.setDescricao(dto.getDescricao());
            if (dto.getUrl() != null) entity.setUrl(dto.getUrl());

            repostitory.save(entity);
            System.out.println("Service atualizado: " + entity);


            return new VideoDto(entity);


        }catch (EntityNotFoundException e){
            throw  new ResourceNotFoundException("Video not found with id: " + id);

        }


    }
}
