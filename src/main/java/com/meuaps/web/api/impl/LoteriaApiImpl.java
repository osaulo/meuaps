package com.meuaps.web.api.impl;

import com.meuaps.dto.JogoDto;
import com.meuaps.service.LoteriaService;
import com.meuaps.web.api.LoteriaApi;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoteriaApiImpl implements LoteriaApi {

    private final LoteriaService loteriaService;

    @Override
    public ResponseEntity<List<JogoDto>> getMegaSenaNumeros() {
        List<JogoDto> jogoDtoList = loteriaService.getMegaSenaNumeros();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jogoDtoList);
    }
}
