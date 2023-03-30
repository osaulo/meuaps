package com.meuaps.config.impl;

import com.meuaps.config.LoteriaConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Qualifier("megaSenaConfig")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MegaSenaConfig implements LoteriaConfig {

    @Value("${loteria.megasena.tamanho-thread}")
    private Integer threadSize;

    @Value("${loteria.megasena.quantidade-jogos}")
    private Long qtdJogos;

    private Integer universoJogo = 60;
    private Integer tamanhoJogo = 6;

    @Override
    public Integer getUniversoJogo() {
        return universoJogo;
    }

    @Override
    public Integer getTamanhoJogo() {
        return tamanhoJogo;
    }

    @Override
    public Integer getThreadSize() {
        return threadSize;
    }

    @Override
    public Long getQtdJogos() {
        return qtdJogos;
    }
}
