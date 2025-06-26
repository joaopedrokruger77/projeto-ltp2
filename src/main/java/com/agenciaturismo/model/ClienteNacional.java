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
        // Garante que a string não seja nula e tenha 11 caracteres.
        // A limpeza de máscara (pontos e traços) já foi feita na tela de cadastro.
        if (cpf == null || cpf.length() != 11) {
            return false;
        }

        // BLOCO COM ERRO REMOVIDO DAQUI

        // Verificar se todos os dígitos são iguais (ex: 111.111.111-11)
        // A regex (\\d)\\1{10} faz essa verificação de forma eficiente.
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Se chegou até aqui, o resto do seu código de cálculo já estava correto.
        try {
            // Validar primeiro dígito verificador
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int primeiroDigito = 11 - (soma % 11);
            if (primeiroDigito >= 10) {
                primeiroDigito = 0;
            }

            if (Character.getNumericValue(cpf.charAt(9)) != primeiroDigito) {
                return false;
            }

            // Validar segundo dígito verificador
            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int segundoDigito = 11 - (soma % 11);
            if (segundoDigito >= 10) {
                segundoDigito = 0;
            }

            // Retorna true se os dois dígitos verificadores estiverem corretos.
            return Character.getNumericValue(cpf.charAt(10)) == segundoDigito;

        } catch (Exception e) {
            // Se qualquer outro erro inesperado ocorrer durante o cálculo, considera inválido.
            return false;
        }
    }
}