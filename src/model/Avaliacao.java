package model;

import java.util.Date;

public class Avaliacao {
    private int id;
    private Usuario usuario;
    private Livro livro;
    private int nota;
    private String comentario;
    private Date dataAvaliacao;

    public Avaliacao() {
        this.dataAvaliacao = new Date();
    }

    public Avaliacao(Usuario usuario, Livro livro, int nota, String comentario) {
        this.usuario = usuario;
        this.livro = livro;
        this.nota = nota;
        this.comentario = comentario;
        this.dataAvaliacao = new Date();
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        if (nota < 1 || nota > 5) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 5");
        }
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(Date dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }
} 