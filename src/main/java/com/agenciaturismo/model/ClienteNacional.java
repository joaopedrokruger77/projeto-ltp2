package com.agenciaturismo.model;

import java.util.Date;

public class ClienteNacional extends Cliente {
    private String cpf;

    public ClienteNacional() {
        super();
    }

    public ClienteNacional(String nome, String email, String telefone, Date dataNascimento, String cpf) {
        super(nome, email, telefone, dataNascimento);
        this.cpf = cpf;
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    @Override
    public String getTipoCliente() {
        return "Nacional";
    }

    @Override
    public String getDocumento() {
        return cpf;
    }

    public static boolean validarCPF(String cpf) {
        if (cpf == null || cpf.length() != 11) return false;
        
        try {
            Integer.parseInt(cpf);
        } catch (NumberFormatException e) {
            return false;
        }

        // Verificar se todos os dígitos são iguais
        boolean todosIguais = true;
        for (int i = 1; i < cpf.length(); i++) {
            if (cpf.charAt(i) != cpf.charAt(0)) {
                todosIguais = false;
                break;
            }
        }
        if (todosIguais) return false;

        // Validar primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int primeiroDigito = 11 - (soma % 11);
        if (primeiroDigito >= 10) primeiroDigito = 0;

        if (Character.getNumericValue(cpf.charAt(9)) != primeiroDigito) return false;

        // Validar segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int segundoDigito = 11 - (soma % 11);
        if (segundoDigito >= 10) segundoDigito = 0;

        return Character.getNumericValue(cpf.charAt(10)) == segundoDigito;
    }
} 