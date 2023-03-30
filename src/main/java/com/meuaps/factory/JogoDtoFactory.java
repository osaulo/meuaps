package com.meuaps.factory;

import com.meuaps.document.MegaSenaDocument;
import com.meuaps.dto.JogoDto;

public final class JogoDtoFactory {

    public static JogoDto createFrom(MegaSenaDocument megaSenaDocument) {
        return JogoDto.builder()
                .numeros(megaSenaDocument.getNumeros())
                .qtdJogos(0)
                .build();
    }
}
