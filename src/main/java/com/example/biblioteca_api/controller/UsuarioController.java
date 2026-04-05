package com.example.biblioteca_api.controller;

import java.util.List;
import com.example.biblioteca_api.model.Usuario;

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

import com.example.biblioteca_api.service.BibliotecaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private BibliotecaService bibliotecaService;

    public UsuarioController(BibliotecaService bibliotecaService){
        this.bibliotecaService = bibliotecaService;
    }

    @GetMapping
    public List<Usuario> listarUsuarios(){
        return bibliotecaService.listarUsuarios();
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<?> buscaUsuario(@PathVariable String cpf){
        Usuario usuario = bibliotecaService.buscarUsuario(cpf);

        if(usuario == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Usuário não encontrado");
        }

        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<String> cadastrarUsuario(@RequestBody @Valid Usuario usuario){
        String erro = bibliotecaService.cadastrarUsuario(usuario);

        if(erro != null){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(erro);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                            .body("Cadastro realizado com sucesso");
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<String> deletarUsuario(@PathVariable String cpf){
        String erro = bibliotecaService.deletarUsuario(cpf);

        if(erro != null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(erro);
        }

        return ResponseEntity.status(HttpStatus.OK)
                            .body("Livro deletado com sucesso");
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<String> atualizarUsuario(@PathVariable String cpf, @RequestBody Usuario usuarioAtualizado){
        String erro = bibliotecaService.atualizarUsuario(cpf, usuarioAtualizado);

        if(erro != null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(erro);
        }

        return ResponseEntity.status(HttpStatus.OK)
                            .body("Usuário atualizado com sucesso");
    }
}
