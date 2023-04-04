package com.meuaps.service;

import com.meuaps.dto.JogoDto;

import java.util.List;

public interface LoteriaService {

    void gerarMegaSenaNumeros();
    List<JogoDto> getMegaSenaNumeros();
    void gerarLotoFacilNumeros();
    List<JogoDto> getLotoFacilNumeros();
    void gerarDuplaNumeros();
    List<JogoDto> getDuplaNumeros();
}
