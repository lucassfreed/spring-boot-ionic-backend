package com.lucasrodrigo.cursomc.services;

import com.lucasrodrigo.cursomc.domain.Estado;
import com.lucasrodrigo.cursomc.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository repo;

    public List<Estado> findAll() {
        return repo.findAllByOrderByNome();
    }

}
