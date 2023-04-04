package com.meuaps.util;

import com.meuaps.config.LoteriaConfig;
import com.meuaps.document.LoteriaDocument;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Arrays;

@Slf4j
@NoArgsConstructor
public class ThreadLoteria extends Thread {

    private LoteriaConfig loteriaConfig;

    private MongoRepository mongoRepository;

    public ThreadLoteria(LoteriaConfig loteriaConfig, MongoRepository mongoRepository) {
        this.loteriaConfig = loteriaConfig;
        this.mongoRepository = mongoRepository;
    }

    public void run(){
        for (int i = 0; loteriaConfig.getThreadSize() > i; i++) {
            LoteriaDocument document = LoteriaUtil.gerarJogo(loteriaConfig);
            mongoRepository.save(document);
            log.info(Arrays.toString(document.getNumeros().toArray()));
        }
    }
}
