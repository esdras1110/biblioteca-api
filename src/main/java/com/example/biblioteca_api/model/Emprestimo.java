package com.example.biblioteca_api.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class Emprestimo {
    @Positive(message = "ID obrigatório")
    private int idEmprestimo;
    @Positive(message = "ID do livro obrigatório")
    private int idLivro;
    @NotBlank(message = "CPF do usuário obrigatório")
    private String cpfUsuario;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private boolean ativo = true;

    public Emprestimo(){}

    public Emprestimo(int idEmprestimo, int idLivro, String cpfUsuario){
        this.idEmprestimo = idEmprestimo;
        this.idLivro = idLivro;
        this.cpfUsuario = cpfUsuario;
        this.dataEmprestimo = LocalDate.now();
    }

    public int getIdEmprestimo(){
        return idEmprestimo;
    }
    public int getIdLivro() {
        return idLivro;
    }
    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }
    public String getCpfUsuario() {
        return cpfUsuario;
    }
    public void setCpfUsuario(String cpfUsuario) {
        this.cpfUsuario = cpfUsuario;
    }
    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }
    public boolean isAtivo() {
        return ativo;
    }
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    public LocalDate getDataDevolucao(){
        return dataDevolucao;
    }
    public void devolver(){
        this.ativo = false;
        this.dataDevolucao = LocalDate.now();
    }
    
}
