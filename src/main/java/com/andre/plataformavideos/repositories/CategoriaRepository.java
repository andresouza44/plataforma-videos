package com.andre.plataformavideos.repositories;

import com.andre.plataformavideos.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByTituloIgnoreCase (String titulo);
    List<Categoria> findByTitulo (String titulo);


}
