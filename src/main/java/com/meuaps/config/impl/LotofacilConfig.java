package com.meuaps.config.impl;

import com.meuaps.config.LoteriaConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Qualifier("lotofacilConfig")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LotofacilConfig implements LoteriaConfig {

    @Value("${loteria.lotofacil.tamanho-thread}")
    private Integer threadSize;

    @Value("${loteria.lotofacil.quantidade-jogos}")
    private Long qtdJogos;

    private Integer universoJogo = 25;
    private Integer tamanhoJogo = 15;

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
        return "LOTOFACIL";
    }
}
