package com.agenciaturismo.model;

import java.util.Date;

public class ClienteEstrangeiro extends Cliente {
    private String passaporte;
    private String nacionalidade;

    public ClienteEstrangeiro() {
        super();
    }

    public ClienteEstrangeiro(String nome, String email, String telefone, Date dataNascimento, 
                            String passaporte, String nacionalidade) {
        super(nome, email, telefone, dataNascimento);
        this.passaporte = passaporte;
        this.nacionalidade = nacionalidade;
    }

    public String getPassaporte() { return passaporte; }
    public void setPassaporte(String passaporte) { this.passaporte = passaporte; }

    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }

    @Override
    public String getTipoCliente() {
        return "Estrangeiro";
    }

    @Override
    public String getDocumento() {
        return passaporte;
    }

    public static boolean validarPassaporte(String passaporte) {
        return passaporte != null && passaporte.trim().length() >= 6 && passaporte.trim().length() <= 15;
    }
} 