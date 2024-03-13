package com.andre.plataformavideos.servicies;

import com.andre.plataformavideos.dto.VideoDto;
import com.andre.plataformavideos.entity.Video;
import com.andre.plataformavideos.exceptions.ResourceNotFoundException;
import com.andre.plataformavideos.repositories.VideoRepository;
import com.andre.plataformavideos.service.VideoService;
import com.andre.plataformavideos.tests.factory.Factory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class VideoServiceTests {

    @InjectMocks
    private VideoService service;

    @Mock
    private VideoRepository repository;

    private Long existId;
    private Long nonExistId;
    private Long dependentId;

    String existTitulo;
    String nonExistTitulo;

    private Video video;
    private VideoDto videoDto;
    private PageImpl page;

    @BeforeEach
    void setUp() {
        existId = 1L;
        nonExistId = 2000L;
        dependentId = 2L;
        existTitulo = "rest";
        nonExistTitulo = "abra";
        video = Factory.creatVideo();
        videoDto = Factory.creatVideoDto();
        page = new PageImpl<>(List.of(video));

        when(repository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);

        when(repository.findById(existId)).thenReturn(Optional.of(video));
        when(repository.findById(nonExistId)).thenReturn(Optional.empty());

        when(repository.findByTituloContainingIgnoreCase(existTitulo)).thenReturn(List.of(video));
        when(repository.findByTituloContainingIgnoreCase(nonExistTitulo)).thenReturn(List.of());

        when(repository.getReferenceById(existId)).thenReturn(video);
        when(repository.getReferenceById(nonExistId)).thenThrow(EntityNotFoundException.class);

        when(repository.existsById(existId)).thenReturn(true);
        when(repository.existsById(nonExistId)).thenReturn(false);
        when(repository.existsById(dependentId)).thenReturn(true);

        doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

    }
    @Test
    public void findAllShouldReturnPageVideoDto(){
        Pageable pageable = PageRequest.of (0,10);
        Page<VideoDto> page = service.findAll(pageable);

        Assertions.assertNotNull(page);
        Mockito.verify(repository).findAll(pageable);

    }
    @Test
    public void findByIdShouldReturnVideoDtoWhenIdExist(){
        VideoDto videoDto = service.findById(existId);

        Assertions.assertNotNull(videoDto);
        Mockito.verify(repository).findById(existId);

    }
    @Test
    public void findByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class, () ->{
           service.findById(nonExistId);
        });
        Mockito.verify(repository).findById(nonExistId);
    }

    @Test
    public void findByTituloShouldReturnListVideosDtoWhenTituloExist(){
        List<VideoDto> dtoList = service.findByTitulo(existTitulo);
        Assertions.assertNotNull(dtoList);

        Mockito.verify(repository).findByTituloContainingIgnoreCase(existTitulo);

    }

    @Test
    public void findByTituloShouldReturnEmptyListWhenTituloDoeNotExist(){
        List<VideoDto> dtoList = service.findByTitulo(nonExistTitulo);
        Assertions.assertTrue(dtoList.isEmpty());

        Mockito.verify(repository).findByTituloContainingIgnoreCase(nonExistTitulo);
    }
    @Test
    public void updateShouldReturnVideoDtoWhenExistIdCategoryDoesNotExist(){
        videoDto.setCategoriaId(null);
        VideoDto dto = service.updateVideo(existId, videoDto);

        Assertions.assertNotNull(dto);
        Mockito.verify(repository).getReferenceById(existId);

    }

    @Test
    public void updateShouldReturnResourceNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class, ()->{
            service.updateVideo(nonExistId, videoDto);
        });
        Mockito.verify(repository).getReferenceById(nonExistId);
    }

    @Test
    public void deleteShouldDoNothinWhenIdExist(){

        Assertions.assertDoesNotThrow(() ->
                service.deleteById(existId));
        Mockito.verify(repository).existsById(existId);
    }

    @Test
    public void deleteShouldReturnResourceNotFoundExceptionWhenIdDoesNotExist(){
        Assertions.assertThrows(ResourceNotFoundException.class, () ->{
            service.deleteById(nonExistId);
        });

    }

    @Test
    public void deleteShouldReturnDataIntegrityViolationExceptionWhenDependentId(){
        Assertions.assertThrows(DataIntegrityViolationException.class, ()->{
           service.deleteById(dependentId);
        });

    }

}
