package org.example.model.cliente;

public class Cliente {
    int id;
    String nome;
    ClienteType tipoCliente;
    String telefone;
    String email;
    String documento;

    public Cliente(int id, String nome, ClienteType tipoCliente, String telefone, String email, String documento) {
        this.id = id;
        this.nome = nome;
        this.tipoCliente = tipoCliente;
        this.telefone = telefone;
        this.email = email;
        this.documento = documento;
    }

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

    public ClienteType getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(ClienteType tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
}
