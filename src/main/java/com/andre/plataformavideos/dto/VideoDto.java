package com.andre.plataformavideos.dto;

import com.andre.plataformavideos.entity.Categoria;
import com.andre.plataformavideos.entity.Video;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public class VideoDto {
    private Long id;

    @NotBlank (message = "Campo requerido")
    @Size( max = 100, message = "Máximo de 100 caracteres")
    private String titulo;

    @NotBlank (message = "Campo requerido")
    private String descricao;

    @NotBlank (message = "Campo requerido")
    @URL(message="Adicione uma URL Válida")
    private String url;

    private Long categoriaId;


    public VideoDto(){

    }

    public VideoDto(Video video) {
        id = video.getId();
        titulo = video.getTitulo();
        descricao = video.getDescricao();
        url = video.getUrl();
        categoriaId = video.getCategoria().getId();


    }



    public VideoDto(Long id, String titulo, String descricao, String url, Long categoriaId) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.url = url;
        this.categoriaId = categoriaId;

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    @Override
    public String toString() {
        return "VideoDto{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
