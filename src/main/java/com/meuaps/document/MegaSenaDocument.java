package com.meuaps.document;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "megasena")
public class MegaSenaDocument {
    private List<Integer> numeros;
//    private Integer qtdJogoRealizados;
}
