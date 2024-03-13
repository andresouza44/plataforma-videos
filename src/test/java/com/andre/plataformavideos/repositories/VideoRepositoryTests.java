package com.andre.plataformavideos.repositories;

import com.andre.plataformavideos.entity.Video;
import com.andre.plataformavideos.tests.factory.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class VideoRepositoryTests {
    @Autowired
    VideoRepository repostitory;

    private Long existId;
    private Long nonExistId;
    private Integer countNumberOfVideos;

    private Video video;


    @BeforeEach
    void setUp() {
        existId = 1L;
        nonExistId = 1000L;
        countNumberOfVideos = 17;

        video = Factory.creatVideo();
    }

    @Test
    public void findByIdShouldReturnOptionalWhenIdIsExist(){
        Optional<Video> optional = repostitory.findById(existId);
        Assertions.assertFalse(optional.isEmpty());

    }
    @Test
    public void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExist(){
        Optional<Video> optional = repostitory.findById(nonExistId);
        Assertions.assertTrue(optional.isEmpty());
    }
    @Test
    public void saveShouldPersistIncrementIdWhenIdExist(){
        video.setId(null);
        repostitory.save(video);

        Assertions.assertNotNull(video.getId());
        Assertions.assertEquals(countNumberOfVideos+1, video.getId());

    }
    @Test
    public void deleteShouldDeleteWhenIdExist(){
        repostitory.deleteById(1L);
        Optional<Video> result = repostitory.findById(existId);

        Assertions.assertFalse(result.isPresent());

    }


}
