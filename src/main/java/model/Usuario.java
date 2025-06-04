package model;

public class Usuario {
    private Long id;
    private String nome;
    private String cpf;
    private String rg;
    private String email;
    private String faculdade;
    private String ra;

    public Usuario() {}

    public Usuario(String nome, String cpf, String rg, String email, String faculdade, String ra) {
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.email = email;
        this.faculdade = faculdade;
        this.ra = ra;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getFaculdade() {
        return faculdade;
    }

    public void setFaculdade(String faculdade) {
        this.faculdade = faculdade;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }
} 