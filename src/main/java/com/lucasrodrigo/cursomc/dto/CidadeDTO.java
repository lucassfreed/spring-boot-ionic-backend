package com.lucasrodrigo.cursomc.dto;

import com.lucasrodrigo.cursomc.domain.Cidade;

import java.io.Serializable;

public class CidadeDTO implements Serializable {
    public static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;

    public CidadeDTO() {
    }

    public CidadeDTO(Cidade obj) {
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