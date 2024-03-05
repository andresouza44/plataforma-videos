package com.andre.plataformavideos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.*;

@Entity
@Table(name="tb_categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Título não pode estar em branco")
    private String titulo;

    @NotBlank(message = "Cor não pode estar em branco")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$" , message = "Entre com um padrão de cor Hexadecimal válido")
    private String cor;

    @OneToMany(mappedBy = "categoria" )
    private List<Video> video = new ArrayList<>();

    public Categoria(){

    }

    public Categoria(Long id, String titulo, String cor) {
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

    public List<Video> getVideo() {
        return video;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Categoria categoria = (Categoria) o;

        if (!Objects.equals(id, categoria.id)) return false;
        return Objects.equals(titulo, categoria.titulo);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (titulo != null ? titulo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", cor='" + cor + '\'' +
                ", video=" + video +
                '}';
    }
}
