package com.meuaps.service.impl;

import com.meuaps.config.LoteriaConfig;
import com.meuaps.document.impl.DuplaDocument;
import com.meuaps.document.impl.LotoFacilDocument;
import com.meuaps.document.impl.MegaSenaDocument;
import com.meuaps.dto.JogoDto;
import com.meuaps.factory.JogoDtoFactory;
import com.meuaps.repository.LoteriaDuplaRepository;
import com.meuaps.repository.LoteriaLotoFacilRepository;
import com.meuaps.repository.LoteriaMegaSenaRepository;
import com.meuaps.service.LoteriaService;
import com.meuaps.util.LoteriaUtil;
import com.meuaps.util.ThreadLoteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
@Slf4j
public class LoteriaServiceImpl implements LoteriaService {

    private final LoteriaConfig loteriaMegaSenaConfig;
    private final LoteriaConfig duplaConfig;
    private final LoteriaConfig lotofacilConfig;

    private final LoteriaMegaSenaRepository loteriaMegaSenaRepository;
    private final LoteriaLotoFacilRepository loteriaLotoFacilRepository;
    private final LoteriaDuplaRepository loteriaDuplaRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public LoteriaServiceImpl(@Qualifier("megaSenaConfig") LoteriaConfig loteriaMegaSenaConfig,
                              @Qualifier("duplaConfig") LoteriaConfig duplaConfig,
                              @Qualifier("lotofacilConfig") LoteriaConfig lotofacilConfig,
                              LoteriaMegaSenaRepository loteriaMegaSenaRepository,
                              LoteriaLotoFacilRepository loteriaLotoFacilRepository,
                              LoteriaDuplaRepository loteriaDuplaRepository,
                              MongoTemplate mongoTemplate) {
        this.loteriaMegaSenaConfig = loteriaMegaSenaConfig;
        this.duplaConfig = duplaConfig;
        this.lotofacilConfig = lotofacilConfig;
        this.loteriaMegaSenaRepository = loteriaMegaSenaRepository;
        this.loteriaLotoFacilRepository = loteriaLotoFacilRepository;
        this.loteriaDuplaRepository = loteriaDuplaRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void gerarMegaSenaNumeros() {
        gerarNumeros(loteriaMegaSenaConfig, loteriaMegaSenaRepository);
    }

    @Override
    public List<JogoDto> getMegaSenaNumeros() {
        JogoDto jogoComUmaOcorrencia = this.findJogoComUmaOcorrencia("megasena");
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

    @Override
    public void gerarLotoFacilNumeros() {
        gerarNumeros(lotofacilConfig, loteriaLotoFacilRepository);
    }

    @Override
    public List<JogoDto> getLotoFacilNumeros() {

        JogoDto jogoComUmaOcorrencia = this.findJogoComUmaOcorrencia("lotofacil");
        log.info("Jogo com 1 ocorrencia: " + Arrays.toString(jogoComUmaOcorrencia.getNumeros().toArray()));

        LotoFacilDocument newJogo;
        boolean existJogo;
        do {
            newJogo = new LotoFacilDocument(LoteriaUtil.gerarJogo(lotofacilConfig.getTamanhoJogo(),
                    lotofacilConfig.getUniversoJogo()));
            Example<LotoFacilDocument> documentExample = Example.of(newJogo);
            existJogo = loteriaLotoFacilRepository.exists(documentExample);
        } while (existJogo);
        log.info("Jogo com 0 ocorrencia: " + Arrays.toString(newJogo.getNumeros().toArray()));

        log.info("finalizou!");
        return Arrays.asList(jogoComUmaOcorrencia, JogoDtoFactory.createFrom(newJogo));
    }

    @Override
    public void gerarDuplaNumeros() {
        gerarNumeros(duplaConfig, loteriaDuplaRepository);
    }

    @Override
    public List<JogoDto> getDuplaNumeros() {

        JogoDto jogoComUmaOcorrencia = this.findJogoComUmaOcorrencia("dupla");
        log.info("Jogo com 1 ocorrencia: " + Arrays.toString(jogoComUmaOcorrencia.getNumeros().toArray()));

        DuplaDocument newJogo;
        boolean existJogo;
        do {
            newJogo = new DuplaDocument(LoteriaUtil.gerarJogo(duplaConfig.getTamanhoJogo(),
                    duplaConfig.getUniversoJogo()));
            Example<DuplaDocument> documentExample = Example.of(newJogo);
            existJogo = loteriaDuplaRepository.exists(documentExample);
        } while (existJogo);
        log.info("Jogo com 0 ocorrencia: " + Arrays.toString(newJogo.getNumeros().toArray()));

        log.info("finalizou!");
        return Arrays.asList(jogoComUmaOcorrencia, JogoDtoFactory.createFrom(newJogo));
    }

    private void gerarNumeros(LoteriaConfig loteriaConfig, MongoRepository mongoRepository) {
        mongoRepository.deleteAll();

        for (int i = 0; i < (int) Math.ceil((double) loteriaConfig.getQtdJogos() / loteriaConfig.getThreadSize()); i++) {
            ThreadLoteria threadLoteria = new ThreadLoteria(loteriaConfig, mongoRepository);

            threadLoteria.run();
        }
    }

    private JogoDto findJogoComUmaOcorrencia(String collectionName) {
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
                aggregation, collectionName, JogoDto.class);
        return result.getUniqueMappedResult();
    }
}
