package com.example.biblioteca_api.controller;

import java.util.List;
import com.example.biblioteca_api.model.Emprestimo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.biblioteca_api.service.BibliotecaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {
    private BibliotecaService bibliotecaService;

    public EmprestimoController(BibliotecaService bibliotecaService){
        this.bibliotecaService = bibliotecaService;
    }

    @GetMapping
    public List<Emprestimo> listarEmprestimos(){
        return bibliotecaService.listarEmprestimos();
    }

    @GetMapping("/ativos")
    public List<Emprestimo> listarEmprestimosAtivos(){
        return bibliotecaService.listarEmprestimosAtivos();
    }

    @GetMapping("/{idEmprestimo}")
    public ResponseEntity<?> buscaEmprestimo(@PathVariable int idEmprestimo){
        Emprestimo emprestimo = bibliotecaService.buscaEmprestimo(idEmprestimo);

        if(emprestimo == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Emprestimo não encontrado");
        }

        return ResponseEntity.ok(emprestimo);

    }

    @PostMapping
    public ResponseEntity<String> realizarEmprestimo(@RequestBody @Valid Emprestimo emprestimo){
        String erro = bibliotecaService.emprestarLivro(emprestimo);

        if(erro != null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(erro);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                            .body("Emprestimo realizado com sucesso");
    }

    @PutMapping("/{idEmprestimo}/devolucao")
    public ResponseEntity<String> devolverLivro(@PathVariable int idEmprestimo){
        String erro = bibliotecaService.devolverLivro(idEmprestimo);

        if(erro != null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(erro);
        }

        return ResponseEntity.status(HttpStatus.OK)
                            .body("Devolução realizada com sucesso");
    }
    
}
