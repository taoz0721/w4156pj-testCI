package com.insomnia_studio.w4156pj.repository;

import com.insomnia_studio.w4156pj.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientEntityRepository extends JpaRepository<ClientEntity, Long> {
    boolean existsByClientId(UUID clientId);

    ClientEntity findByClientId(UUID clientId);
}
