package com.example.biblioteca_api.model;

import jakarta.validation.constraints.NotBlank;

public class Usuario {
    @NotBlank(message = "Nome obrigatório")
    private String nome;
    @NotBlank(message = "CPF obrigatório")
    private String cpf;

    public Usuario(){}

    public Usuario(String nome, String cpf){
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
