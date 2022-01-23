package com.devsuperior.DSClientREST.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.DSClientREST.model.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

}
