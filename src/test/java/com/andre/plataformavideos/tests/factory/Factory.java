package com.andre.plataformavideos.tests.factory;

import com.andre.plataformavideos.entity.Categoria;
import com.andre.plataformavideos.entity.Video;

public class Factory {

    public static Categoria creatCategoria(){
        Categoria categoria = new Categoria(1L,"Educativos", "#00FFFF");
        return categoria;
    }

    public static Video creatVideo(){
        Video video = new Video(1L,"Quais as melhores práticas com React?",
                "Recebemos o @Dev Soutinho nesse Hipsters Ponto Tube para falar sobre as melhores práticas com React e como você pode aproveitar melhor as possibilidades dessa tecnologia no cenário atual.",
                "https://www.youtube.com/watch?v=k77WZrvuDQU",creatCategoria());
        return video;
    }
}
