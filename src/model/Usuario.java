package model;

public class Usuario {
    private int id;
    private String nome;
    private String cpf;
    private String rg;
    private String email;
    private TipoUsuario tipoUsuario;

    public enum TipoUsuario {
        COMUM,
        ADMIN
    }

    public Usuario() {
        this.tipoUsuario = TipoUsuario.COMUM;
    }

    public Usuario(String nome, String cpf, String rg, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.email = email;
        this.tipoUsuario = TipoUsuario.COMUM;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public boolean isAdmin() {
        return tipoUsuario == TipoUsuario.ADMIN;
    }
} 