package com.agenciaturismo.gui;

import com.agenciaturismo.dao.ServicoAdicionalDAO;
import com.agenciaturismo.model.ServicoAdicional;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class TelaCadastroServico extends JDialog {
    private JTextField txtNome;
    private JTextArea txtDescricao;
    private JTextField txtPreco;
    private JComboBox<String> cbCategoria;
    private ServicoAdicionalDAO servicoDAO;

    public TelaCadastroServico(Frame parent) {
        super(parent, "Cadastro de Serviço Adicional", true);
        this.servicoDAO = new ServicoAdicionalDAO();
        initComponents();
        configurarJanela();
    }

    private void initComponents() {
        setLayout(new BorderLayout());


        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBorder(BorderFactory.createTitledBorder("Dados do Serviço"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);


        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        painelPrincipal.add(new JLabel("Nome do Serviço:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtNome = new JTextField(20);
        painelPrincipal.add(txtNome, gbc);


        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        painelPrincipal.add(new JLabel("Categoria:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        cbCategoria = new JComboBox<>(new String[]{
            "Seguro", "Transporte", "Hospedagem", "Passeio", 
            "Gastronomia", "Esporte", "Bem-estar", "Compras", "Outros"
        });
        cbCategoria.setEditable(true);
        painelPrincipal.add(cbCategoria, gbc);


        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        painelPrincipal.add(new JLabel("Preço (R$):"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtPreco = new JTextField(10);
        painelPrincipal.add(txtPreco, gbc);


        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        painelPrincipal.add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; gbc.weighty = 1.0;
        txtDescricao = new JTextArea(5, 20);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        JScrollPane scrollDescricao = new JScrollPane(txtDescricao);
        painelPrincipal.add(scrollDescricao, gbc);

        add(painelPrincipal, BorderLayout.CENTER);


        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnSalvar = new JButton("Salvar");
        JButton btnLimpar = new JButton("Limpar");
        JButton btnCancelar = new JButton("Cancelar");

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarServico();
            }
        });

        btnLimpar.addActionListener(e -> limparCampos());
        btnCancelar.addActionListener(e -> dispose());

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnCancelar);

        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void salvarServico() {
        if (!validarCampos()) {
            return;
        }

        try {
            ServicoAdicional servico = new ServicoAdicional(
                txtNome.getText(),
                txtDescricao.getText(),
                new BigDecimal(txtPreco.getText().replace(",", ".")),
                cbCategoria.getSelectedItem().toString()
            );

            if (servicoDAO.inserir(servico)) {
                JOptionPane.showMessageDialog(this, "Serviço cadastrado com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar serviço!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao processar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarCampos() {
        // Validar nome
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome do serviço é obrigatório!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtNome.requestFocus();
            return false;
        }


        if (cbCategoria.getSelectedItem() == null || cbCategoria.getSelectedItem().toString().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Categoria é obrigatória!", "Validação", JOptionPane.WARNING_MESSAGE);
            cbCategoria.requestFocus();
            return false;
        }


        if (txtPreco.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preço é obrigatório!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtPreco.requestFocus();
            return false;
        }

        try {
            double preco = Double.parseDouble(txtPreco.getText().replace(",", "."));
            if (preco < 0) {
                JOptionPane.showMessageDialog(this, "Preço não pode ser negativo!", "Validação", JOptionPane.WARNING_MESSAGE);
                txtPreco.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço inválido!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtPreco.requestFocus();
            return false;
        }

        return true;
    }

    private void limparCampos() {
        txtNome.setText("");
        txtDescricao.setText("");
        txtPreco.setText("");
        cbCategoria.setSelectedIndex(0);
    }

    private void configurarJanela() {
        setSize(500, 400);
        setLocationRelativeTo(getParent());
        setResizable(false);
    }
} 