package com.meuaps.repository;

import com.meuaps.document.impl.DuplaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoteriaDuplaRepository extends MongoRepository<DuplaDocument, Long> {

}
