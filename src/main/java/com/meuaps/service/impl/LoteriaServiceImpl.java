package com.meuaps.service.impl;

import com.meuaps.config.LoteriaConfig;
import com.meuaps.document.MegaSenaDocument;
import com.meuaps.dto.JogoDto;
import com.meuaps.factory.JogoDtoFactory;
import com.meuaps.repository.LoteriaMegaSenaRepository;
import com.meuaps.service.LoteriaService;
import com.meuaps.util.LoteriaUtil;
import com.meuaps.util.ThreadLoteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoteriaServiceImpl implements LoteriaService {

    @Qualifier("megaSenaConfig")
    private final LoteriaConfig loteriaMegaSenaConfig;

    private final LoteriaMegaSenaRepository loteriaMegaSenaRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public List<JogoDto> getMegaSenaNumeros() {

        this.loteriaMegaSenaRepository.deleteAll();

        for (int i = 0; i < (int) Math.ceil((double) loteriaMegaSenaConfig.getQtdJogos() / loteriaMegaSenaConfig.getThreadSize()); i++) {
            ThreadLoteria threadLoteria = new ThreadLoteria(loteriaMegaSenaConfig, loteriaMegaSenaRepository);

            threadLoteria.run();
        }

        JogoDto jogoComUmaOcorrencia = this.findJogoComUmaOcorrencia();
        log.info("Jogo com 1 ocorrencia: " + Arrays.toString(jogoComUmaOcorrencia.getNumeros().toArray()));

        MegaSenaDocument newJogo;
        boolean existJogo;
        do {
            newJogo = new MegaSenaDocument(LoteriaUtil.gerarJogo(loteriaMegaSenaConfig.getTamanhoJogo(),
                    loteriaMegaSenaConfig.getUniversoJogo()));
            Example<MegaSenaDocument> documentExample = Example.of(newJogo);
            existJogo = loteriaMegaSenaRepository.exists(documentExample);
        } while (existJogo);
        log.info("Jogo com 0 ocorrencia: " + Arrays.toString(newJogo.getNumeros().toArray()));

        log.info("finalizou!");
        return Arrays.asList(jogoComUmaOcorrencia, JogoDtoFactory.createFrom(newJogo));
    }

    private JogoDto findJogoComUmaOcorrencia() {
        GroupOperation groupByStateAndSumPop = group("numeros")
                .count().as("qtdJogos");
        MatchOperation filterStates = match(new Criteria("qtdJogos").is(1));
        SampleOperation sampleOperation = sample(1);

        Aggregation aggregation = newAggregation(
                groupByStateAndSumPop, filterStates, sampleOperation);
        aggregation = aggregation.withOptions(AggregationOptions.builder()
                .allowDiskUse(true)
                .build());
        AggregationResults<JogoDto> result = mongoTemplate.aggregate(
                aggregation, "megasena", JogoDto.class);
        return result.getUniqueMappedResult();
    }
}
