package com.andre.plataformavideos.repositories;

import com.andre.plataformavideos.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepostitory extends JpaRepository<Video, Long> {
}
