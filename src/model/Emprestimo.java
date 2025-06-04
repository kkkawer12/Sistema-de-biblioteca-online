package model;

import java.util.Date;

public class Emprestimo {
    private int id;
    private Usuario usuario;
    private Livro livro;
    private Date dataEmprestimo;
    private Date dataDevolucao;
    private Status status;

    public enum Status {
        ATIVO,
        FINALIZADO,
        EXPIRADO
    }

    public Emprestimo() {
        this.dataEmprestimo = new Date();
        this.status = Status.ATIVO;
    }

    public Emprestimo(Usuario usuario, Livro livro, Date dataDevolucao) {
        this.usuario = usuario;
        this.livro = livro;
        this.dataEmprestimo = new Date();
        this.dataDevolucao = dataDevolucao;
        this.status = Status.ATIVO;
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

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isExpirado() {
        return new Date().after(dataDevolucao);
    }

    public void atualizarStatus() {
        if (status == Status.ATIVO) {
            if (isExpirado()) {
                status = Status.EXPIRADO;
            }
        }
    }
} 