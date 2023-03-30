package com.meuaps.util;

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

    public static Integer gerarNumeroRandom(Integer min, Integer max) {
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }
}
