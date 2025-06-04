package model;

import java.util.Date;

public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private Categoria categoria;
    private String descricao;
    private String arquivoPath;
    private Date dataCadastro;

    public enum Categoria {
        SAUDE_ALIMENTAR,
        PSICOLOGIA,
        BEM_ESTAR
    }

    public Livro() {}

    public Livro(String titulo, String autor, Categoria categoria, String descricao, String arquivoPath) {
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.descricao = descricao;
        this.arquivoPath = arquivoPath;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getArquivoPath() {
        return arquivoPath;
    }

    public void setArquivoPath(String arquivoPath) {
        this.arquivoPath = arquivoPath;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
} 