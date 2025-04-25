package org.example.model.cliente;

public  class ClienteNacional extends Cliente {
    private String cpf;

    public ClienteNacional(String nome, String cpf, String telefone, String email) {
        super(nome, telefone, email);
        this.cpf = cpf;
    }

    @Override
    public String getIdentificacao() {
        return "CPF: " + cpf;
    }

    @Override
    public String getEmail() {
        return "";
    }

    @Override
    public String getPassaporte() {
        return "";
    }

    @Override
    public String getCpf() {
        return "";
    }

    @Override
    public String getTelefone() {
        return "";
    }
}