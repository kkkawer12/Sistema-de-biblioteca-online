package model;

import java.util.Date;

public class Projeto {
    private int codigo;
    private String descricao;
    private String ods;
    private String responsavel;
    private String telefone;
    private Date dataCriacao;
    private String status;

    public Projeto() {}

    public Projeto(String descricao, String ods, String responsavel, String telefone, Date dataCriacao, String status) {
        this.descricao = descricao;
        this.ods = ods;
        this.responsavel = responsavel;
        this.telefone = telefone;
        this.dataCriacao = dataCriacao;
        this.status = status;
    }

    // Getters e Setters
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getOds() {
        return ods;
    }

    public void setOds(String ods) {
        this.ods = ods;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
} 