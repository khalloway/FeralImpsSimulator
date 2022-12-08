package com.kotfi.FeralImpsSimulator.repository;

import com.kotfi.FeralImpsSimulator.models.CardInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CardRepository extends MongoRepository<CardInfo, String> {

    @Query("{name:'?0'}")
    CardInfo findCardByName(String name);

    @Query("{race:'Reptile'}")
    List<CardInfo> findAllReptiles();

    @Query("{type:{$not:/(Link|XYZ|Synchro|Fusion).*/}}")
    List<CardInfo> findMainDeckReptiles();

    public long count();
}
