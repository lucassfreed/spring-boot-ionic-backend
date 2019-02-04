package com.lucasrodrigo.cursomc.repositories;

import com.lucasrodrigo.cursomc.domain.Cidade;
import com.lucasrodrigo.cursomc.domain.Estado;
import com.lucasrodrigo.cursomc.dto.CidadeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT obj FROM Cidade obj WHERE obj.estado.id = :estadoId ORDER BY obj.nome")
    List<Cidade> findCidades(@Param("estadoId") Integer estadoId);


}
