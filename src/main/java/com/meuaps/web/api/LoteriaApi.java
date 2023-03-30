package com.meuaps.web.api;

import com.meuaps.constants.AppConstants;
import com.meuaps.dto.JogoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping(value = AppConstants.PATH + "/api/loteria")
@CrossOrigin(origins = "*")
@Tag(name = "Loteria-Api", description = "Endpoint para retornar os números sorteados da loteria")
public interface LoteriaApi {

    @Operation(description = "Retorna os números da megasena",
            summary = "Retorna os números da megasena",
            responses = {
                    @ApiResponse(content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = HttpStatus.class)
                            )}
                    )})
    @GetMapping("/megasena")
    ResponseEntity<List<JogoDto>> getMegaSenaNumeros();

}
