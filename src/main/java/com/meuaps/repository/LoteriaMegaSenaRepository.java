package com.meuaps.repository;

import com.meuaps.document.impl.MegaSenaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoteriaMegaSenaRepository extends MongoRepository<MegaSenaDocument, Long> {

}
