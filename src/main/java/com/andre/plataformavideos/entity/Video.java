package com.andre.plataformavideos.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table( name="tb_video")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String titulo;
    private String descricao;
    private String url;


    @ManyToOne
    @JoinColumn(name = "categoriaId")
    private Categoria categoria;


    public  Video(){

    }
    public Video(Long id, String titulo, String descricao, String url) {
        Id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.url = url;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Video video = (Video) o;

        if (!Objects.equals(Id, video.Id)) return false;
        return Objects.equals(titulo, video.titulo);
    }

    @Override
    public int hashCode() {
        int result = Id != null ? Id.hashCode() : 0;
        result = 31 * result + (titulo != null ? titulo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Video{" +
                "Id=" + Id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
