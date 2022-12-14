package com.bancoItau.bancoItau.adapter.out.db.repository;

import com.bancoItau.bancoItau.domain.model.ChavePix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChavePixRepository extends JpaRepository<ChavePix, UUID> {
}
