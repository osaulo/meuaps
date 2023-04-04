package com.meuaps.document.impl;

import com.meuaps.document.LoteriaDocument;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "megasena")
public class MegaSenaDocument implements LoteriaDocument {
    private List<Integer> numeros;
//    private Integer qtdJogoRealizados;
}
