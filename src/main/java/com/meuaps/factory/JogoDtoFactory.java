package com.meuaps.factory;

import com.meuaps.document.impl.DuplaDocument;
import com.meuaps.document.impl.LotoFacilDocument;
import com.meuaps.document.impl.MegaSenaDocument;
import com.meuaps.dto.JogoDto;

public final class JogoDtoFactory {

    public static JogoDto createFrom(MegaSenaDocument megaSenaDocument) {
        return JogoDto.builder()
                .numeros(megaSenaDocument.getNumeros())
                .qtdJogos(0)
                .build();
    }

    public static JogoDto createFrom(LotoFacilDocument lotoFacilDocument) {
        return JogoDto.builder()
                .numeros(lotoFacilDocument.getNumeros())
                .qtdJogos(0)
                .build();
    }

    public static JogoDto createFrom(DuplaDocument duplaDocument) {
        return JogoDto.builder()
                .numeros(duplaDocument.getNumeros())
                .qtdJogos(0)
                .build();
    }
}
