package com.example.biblioteca_api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.biblioteca_api.model.*;

import org.springframework.stereotype.Service;

@Service
public class BibliotecaService {
    List<Livro> livros = new ArrayList<>();
    List<Usuario> usuarios = new ArrayList<>();
    List<Emprestimo> emprestimos = new ArrayList<>();

    // LIVROS - MÉTODOS
    public List<Livro> listarLivros(){
        return livros;
    }

    public Livro buscaLivro(int id){
        return livros.stream()
                    .filter(l -> l.getId() == id)
                    .findFirst()
                    .orElse(null);
    }

    public String cadastrarLivro(Livro livro){
        if(buscaLivro(livro.getId()) != null){
            return "Já existe um livro cadastrado com esse ID";
        }

        livros.add(livro);
        return null;
    }

    public String deletarLivro(int id){
        Livro livro = buscaLivro(id);
        if(livro == null){
            return "Livro não encontrado";
        }

        livros.remove(livro);
        return null;
    }

    public String atualizarLivro(int id, Livro livroAtualizado){
        Livro livro = buscaLivro(id);

        if(livro == null){
            return "Livro não encontrado";
        }

        livro.setTitulo(livroAtualizado.getTitulo());
        livro.setAutor(livroAtualizado.getAutor());

        return null;
    }

    // USUÁRIOS - MÉTODOS
    public List<Usuario> listarUsuarios(){
        return usuarios;
    }

    public Usuario buscarUsuario(String cpf){
        return usuarios.stream()
                    .filter(u -> u.getCpf().equals(cpf))
                    .findFirst()
                    .orElse(null);
    }

    public String cadastrarUsuario(Usuario usuario){
        if(buscarUsuario(usuario.getCpf()) != null){
            return "Já existe um usuário cadastrado com esse ID";
        }

        usuarios.add(usuario);
        return null;
    }

    public String deletarUsuario(String cpf){
        Usuario usuario = buscarUsuario(cpf);
        if(usuario == null){
            return "Usuário não encontrado";
        }

        usuarios.remove(usuario);
        return null;
    }

    public String atualizarUsuario(String cpf, Usuario usuarioAtualizado){
        Usuario usuario = buscarUsuario(cpf);

        if(buscarUsuario(cpf) == null){
            return "Usuário não encontrado";
        }

        usuario.setNome(usuarioAtualizado.getNome());

        return null;
    }

    // EMPRÉSTIMO - MÉTODOS
    public String emprestarLivro(Emprestimo emprestimo){
        if(buscaEmprestimo(emprestimo.getIdEmprestimo()) != null){
            return "Já existe um empréstimo com esse ID";
        }

        Livro livro = buscaLivro(emprestimo.getIdLivro());
        Usuario usuario = buscarUsuario(emprestimo.getCpfUsuario());

        if(livro == null){
            return "Livro não encontrado";
        }

        if(usuario == null){
            return "Usuário não encontrado";
        }

        if(!livro.isDisponivel()){
            return "Livro indisponível para empréstimo";
        }

        emprestimos.add(emprestimo);
        livro.setDisponivel(false);
        return null;
    }

    public List<Emprestimo> listarEmprestimos(){
        return emprestimos;
    }

    public List<Emprestimo> listarEmprestimosAtivos(){
        return emprestimos.stream()
                        .filter(e -> e.isAtivo())
                        .collect(Collectors.toList());
    }

    public List<Emprestimo> listarEmprestimosUsuario(int idEmprestimo){
        return listarEmprestimosAtivos().stream()
                        .filter(e -> e.getIdEmprestimo() == idEmprestimo)
                        .collect(Collectors.toList());
    }

    public Emprestimo buscaEmprestimo(int idEmprestimo){
        return emprestimos.stream()
                            .filter(e -> e.getIdEmprestimo() == idEmprestimo)
                            .findFirst()
                            .orElse(null);
    }

    public String devolverLivro(int idEmprestimo){
        Emprestimo emprestimo = buscaEmprestimo(idEmprestimo);
        if(emprestimo == null){
            return "Emprestimo não encontrado";
        }

        if(!emprestimo.isAtivo()){
            return "Livro já foi devolvido";
        }

        emprestimo.devolver();
        buscaLivro(emprestimo.getIdLivro()).setDisponivel(true);

        return null;
    }
}
