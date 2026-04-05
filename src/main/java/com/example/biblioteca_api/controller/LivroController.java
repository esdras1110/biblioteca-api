package com.example.biblioteca_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.biblioteca_api.model.Livro;
import com.example.biblioteca_api.service.BibliotecaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/livros")
public class LivroController {
    private BibliotecaService bibliotecaService;

    public LivroController(BibliotecaService bibliotecaService){
        this.bibliotecaService = bibliotecaService;
    }

    @GetMapping
    public List<Livro> listarLivros(){
        return bibliotecaService.listarLivros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarLivro(@PathVariable int id){
        Livro livro = bibliotecaService.buscaLivro(id);

        if(livro == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Livro não encontrado");
        }else{
            return ResponseEntity.ok(livro);
        }
    }

    @PostMapping
    public ResponseEntity<String> cadastrarLivro(@RequestBody @Valid Livro livro){
        String erro = bibliotecaService.cadastrarLivro(livro);

        if(erro != null){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(erro);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                            .body("Cadastro realizado com sucesso");
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarLivro(@PathVariable int id){
        String erro = bibliotecaService.deletarLivro(id);

        if(erro != null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(erro);
        }

        return ResponseEntity.status(HttpStatus.OK)
                            .body("Livro deletado com sucesso");
    }   

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarLivro(@PathVariable int id, @RequestBody Livro livroAtualizado){
        String erro = bibliotecaService.atualizarLivro(id, livroAtualizado);

        if(erro != null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(erro);
        }

        return ResponseEntity.status(HttpStatus.OK)
                            .body("Livro atualizado com sucesso");
    }
}
