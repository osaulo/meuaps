package com.meuaps.util;

import com.meuaps.config.LoteriaConfig;
import com.meuaps.document.LoteriaDocument;
import com.meuaps.document.impl.DuplaDocument;
import com.meuaps.document.impl.LotoFacilDocument;
import com.meuaps.document.impl.MegaSenaDocument;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoteriaUtil {

    public static List<Integer> gerarJogo(Integer tamanhoJogo, Integer universoJogo) {
        List<Integer> jogo = new ArrayList<>();
        for (int i = 0; tamanhoJogo > i; i++) {
            Integer number = null;
            do {
                number = gerarNumeroRandom(1, universoJogo);
            } while (jogo.contains(number));

            jogo.add(number);
        }
        Collections.sort(jogo);
        return jogo;
    }

    public static LoteriaDocument gerarJogo(LoteriaConfig loteriaConfig) {
        List<Integer> jogo = new ArrayList<>();
        for (int i = 0; loteriaConfig.getTamanhoJogo() > i; i++) {
            Integer number = null;
            do {
                number = gerarNumeroRandom(1, loteriaConfig.getUniversoJogo());
            } while (jogo.contains(number));

            jogo.add(number);
        }
        Collections.sort(jogo);

        LoteriaDocument result = null;
        switch (loteriaConfig.getType()) {
            case "MEGASENA":
                result = new MegaSenaDocument(jogo);
                break;
            case "LOTOFACIL":
                result = new LotoFacilDocument(jogo);
                break;
            case "DUPLASENA":
                result = new DuplaDocument(jogo);
                break;

        }
        return result;
    }

    public static Integer gerarNumeroRandom(Integer min, Integer max) {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }
}
