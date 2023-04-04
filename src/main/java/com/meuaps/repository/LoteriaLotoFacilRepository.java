package com.meuaps.repository;

import com.meuaps.document.impl.LotoFacilDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LoteriaLotoFacilRepository extends MongoRepository<LotoFacilDocument, Long> {

}
