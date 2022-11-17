package com.twms.wms.repositories;

import com.twms.wms.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    public List<Client> findByName(String name);

    public List<Client> findByCNPJ(String cnpj);

}
