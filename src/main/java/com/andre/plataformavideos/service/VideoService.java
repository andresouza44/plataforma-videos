package com.andre.plataformavideos.service;

import com.andre.plataformavideos.dto.VideoDto;
import com.andre.plataformavideos.entity.Video;
import com.andre.plataformavideos.exceptional.ResourceNotFoundException;
import com.andre.plataformavideos.repositories.VideoRepostitory;
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

    public  VideoDto findById(Long id){
        Video video = repostitory.findById(id)
               .orElseThrow(() -> new ResourceNotFoundException("Video not found with id: " + id));
        return  new VideoDto(video);
    }
}
