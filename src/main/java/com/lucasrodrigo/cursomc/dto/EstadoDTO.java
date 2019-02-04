package com.lucasrodrigo.cursomc.dto;

import com.lucasrodrigo.cursomc.domain.Estado;

import java.io.Serializable;

public class EstadoDTO implements Serializable {
    public static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;

    public EstadoDTO() {
    }

    public EstadoDTO(Estado obj) {
        this.id = obj.getId();
        this.nome = obj.getNome();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
