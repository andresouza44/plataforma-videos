package com.andre.plataformavideos.dto;

import com.andre.plataformavideos.entity.Categoria;
import com.andre.plataformavideos.entity.Video;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoriaDto {
    private Long id;
    private String titulo;
    private String cor;

    private List<VideoDto> videos = new ArrayList<>();

    public CategoriaDto(){
    }

    public CategoriaDto(Long id) {
        this.id = id;
    }

    public CategoriaDto(Categoria categoria){
        this.id = categoria.getId();
        this.titulo = categoria.getTitulo();
        this.cor = categoria.getCor();

        for (Video video : categoria.getVideo()) {
            VideoDto videoDto = new VideoDto(video);
            videos.add(videoDto);
        }
    }

    public CategoriaDto(Long id, String titulo, String cor) {
        this.id = id;
        this.titulo = titulo;
        this.cor = cor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public List<VideoDto> getVideos() {
        return videos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoriaDto that = (CategoriaDto) o;

        if (!Objects.equals(id, that.id)) return false;
        return Objects.equals(titulo, that.titulo);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (titulo != null ? titulo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CategoriaDto{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", cor='" + cor + '\'' +
                ", videos=" + videos +
                '}';
    }
}
