package com.andre.plataformavideos;

import com.andre.plataformavideos.entity.Categoria;
import com.andre.plataformavideos.entity.Video;
import com.andre.plataformavideos.repositories.CategoriaRepository;
import com.andre.plataformavideos.repositories.VideoRepostitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private VideoRepostitory videoRepostitory;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public void run(String... args) throws Exception {

        Categoria categoria1 = new Categoria();
        categoria1.setTitulo("LIVRE");
        categoria1.setCor("#FFFFFF"); // WHITE

        Categoria categoria2 = new Categoria();
        categoria2.setTitulo("Educativos");
        categoria2.setCor("#00FFFF"); // Ciano

        Categoria categoria3 = new Categoria();
        categoria3.setTitulo("Lives");
        categoria3.setCor("#FFFFCC"); // Amarelo Claro

        categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2, categoria3));

        Video video1 = new Video();
        video1.setTitulo("REAGINDO a Portfólios Dos INSCRITOS");
        video1.setDescricao("Nesse video vou reagir ao portfolio dos inscritos e trazer alguns insights que podem te ajudar no seu portfolio.");
        video1.setUrl("https://youtu.be/iQk2NveOTgQ?si=OT_xMaIgfGBpbTQB");
        video1.setCategoria(categoria3);

        Video video3 = new Video();
        video3.setTitulo("REMOVER BLOATWARES do Windows");
        video3.setDescricao("Como REMOVER os BLOATWARES do Windows em 2 cliques!!");
        video3.setUrl("https://youtu.be/D6h-aveKM7U?si=774Wt7aOIRm1SZ_V");
        video3.setCategoria(categoria2);

        Video video4 = new Video();
        video4.setTitulo("Challenge 1: Decodificador de Texto - Programa ONE TURMA 5");
        video4.setDescricao("Esse é o primeiro Challenge(desafio) do programa #ONE  da #Oracle  em parceria com a #Alura");
        video4.setUrl("https://youtu.be/bpPkRBt0L_8?si=jmwpwPyCgm09pEg6");
        video4.setCategoria(categoria1);



        videoRepostitory.saveAll(Arrays.asList(video1, video3, video4));



    }
}
