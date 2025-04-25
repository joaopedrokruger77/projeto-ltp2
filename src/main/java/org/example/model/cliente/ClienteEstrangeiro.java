package org.example.model.cliente;

public  class ClienteEstrangeiro extends Cliente {
    private String passaporte;

    public ClienteEstrangeiro(String nome, String passaporte, String telefone, String email) {
        super(nome, telefone, email);
        this.passaporte = passaporte;
    }

    @Override
    public String getIdentificacao() {
        return "Passaporte: " + passaporte;
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
