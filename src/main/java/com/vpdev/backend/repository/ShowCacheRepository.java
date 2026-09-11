/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.vpdev.backend.repository;

import com.vpdev.backend.model.ShowCacheDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * @author marco
 */
@Repository
public interface ShowCacheRepository extends MongoRepository<ShowCacheDocument, Long> {
}