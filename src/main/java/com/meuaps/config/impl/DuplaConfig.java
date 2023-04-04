package com.meuaps.config.impl;

import com.meuaps.config.LoteriaConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Qualifier("duplaConfig")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DuplaConfig implements LoteriaConfig {

    @Value("${loteria.dupla.tamanho-thread}")
    private Integer threadSize;

    @Value("${loteria.dupla.quantidade-jogos}")
    private Long qtdJogos;

    private Integer universoJogo = 50;
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

    @Override
    public String getType() {
        return "DUPLASENA";
    }
}
