package com.meuaps.util;

import com.meuaps.config.LoteriaConfig;
import com.meuaps.document.MegaSenaDocument;
import com.meuaps.repository.LoteriaMegaSenaRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@NoArgsConstructor
public class ThreadLoteria extends Thread {

    private LoteriaConfig loteriaConfig;

    private LoteriaMegaSenaRepository loteriaMegaSenaRepository;

    public ThreadLoteria(LoteriaConfig loteriaConfig, LoteriaMegaSenaRepository loteriaMegaSenaRepository) {
        this.loteriaConfig = loteriaConfig;
        this.loteriaMegaSenaRepository = loteriaMegaSenaRepository;
    }

    public void run(){
        for (int i = 0; loteriaConfig.getThreadSize() > i; i++) {
            MegaSenaDocument megaSenaDocument = new MegaSenaDocument(LoteriaUtil.gerarJogo(loteriaConfig.getTamanhoJogo(),
                    loteriaConfig.getUniversoJogo()));
            loteriaMegaSenaRepository.save(megaSenaDocument);
            log.info(Arrays.toString(megaSenaDocument.getNumeros().toArray()));
        }
    }
}
