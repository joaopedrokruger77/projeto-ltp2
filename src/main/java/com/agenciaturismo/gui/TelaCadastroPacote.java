package com.agenciaturismo.gui;

import com.agenciaturismo.dao.PacoteViagemDAO;
import com.agenciaturismo.model.PacoteViagem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TelaCadastroPacote extends JDialog {
    private JTextField txtNome;
    private JTextField txtDestino;
    private JTextArea txtDescricao;
    private JTextField txtPreco;
    private JTextField txtDataInicio;
    private JTextField txtDataFim;
    private JTextField txtVagas;
    private PacoteViagemDAO pacoteDAO;

    public TelaCadastroPacote(Frame parent) {
        super(parent, "Cadastro de Pacote de Viagem", true);
        this.pacoteDAO = new PacoteViagemDAO();
        initComponents();
        configurarJanela();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Painel principal
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBorder(BorderFactory.createTitledBorder("Dados do Pacote"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Nome
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        painelPrincipal.add(new JLabel("Nome do Pacote:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtNome = new JTextField(20);
        painelPrincipal.add(txtNome, gbc);

        // Destino
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        painelPrincipal.add(new JLabel("Destino:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtDestino = new JTextField(20);
        painelPrincipal.add(txtDestino, gbc);

        // Descrição
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        painelPrincipal.add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.BOTH;
        txtDescricao = new JTextArea(4, 20);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        JScrollPane scrollDescricao = new JScrollPane(txtDescricao);
        painelPrincipal.add(scrollDescricao, gbc);

        // Preço
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        painelPrincipal.add(new JLabel("Preço (R$):"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtPreco = new JTextField(10);
        painelPrincipal.add(txtPreco, gbc);

        // Vagas
        gbc.gridx = 2; gbc.gridy = 3; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel painelVagas = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelVagas.add(new JLabel("Vagas:"));
        txtVagas = new JTextField(5);
        painelVagas.add(txtVagas);
        painelPrincipal.add(painelVagas, gbc);

        // Data Início
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        painelPrincipal.add(new JLabel("Data Início (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtDataInicio = new JTextField(10);
        painelPrincipal.add(txtDataInicio, gbc);

        // Data Fim
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        painelPrincipal.add(new JLabel("Data Fim (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtDataFim = new JTextField(10);
        painelPrincipal.add(txtDataFim, gbc);

        add(painelPrincipal, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnSalvar = new JButton("Salvar");
        JButton btnLimpar = new JButton("Limpar");
        JButton btnCancelar = new JButton("Cancelar");

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarPacote();
            }
        });

        btnLimpar.addActionListener(e -> limparCampos());
        btnCancelar.addActionListener(e -> dispose());

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnCancelar);

        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void salvarPacote() {
        if (!validarCampos()) {
            return;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dataInicio = sdf.parse(txtDataInicio.getText());
            Date dataFim = sdf.parse(txtDataFim.getText());

            PacoteViagem pacote = new PacoteViagem(
                txtNome.getText(),
                txtDestino.getText(),
                txtDescricao.getText(),
                new BigDecimal(txtPreco.getText().replace(",", ".")),
                dataInicio,
                dataFim,
                Integer.parseInt(txtVagas.getText())
            );

            if (pacoteDAO.inserir(pacote)) {
                JOptionPane.showMessageDialog(this, "Pacote cadastrado com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar pacote!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao processar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarCampos() {
        // Validar campos obrigatórios
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome do pacote é obrigatório!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtNome.requestFocus();
            return false;
        }

        if (txtDestino.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Destino é obrigatório!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtDestino.requestFocus();
            return false;
        }

        if (txtPreco.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preço é obrigatório!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtPreco.requestFocus();
            return false;
        }

        // Validar preço
        try {
            double preco = Double.parseDouble(txtPreco.getText().replace(",", "."));
            if (preco <= 0) {
                JOptionPane.showMessageDialog(this, "Preço deve ser maior que zero!", "Validação", JOptionPane.WARNING_MESSAGE);
                txtPreco.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço inválido!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtPreco.requestFocus();
            return false;
        }

        // Validar vagas
        if (txtVagas.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Número de vagas é obrigatório!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtVagas.requestFocus();
            return false;
        }

        try {
            int vagas = Integer.parseInt(txtVagas.getText());
            if (vagas <= 0) {
                JOptionPane.showMessageDialog(this, "Número de vagas deve ser maior que zero!", "Validação", JOptionPane.WARNING_MESSAGE);
                txtVagas.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número de vagas inválido!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtVagas.requestFocus();
            return false;
        }

        // Validar datas
        if (txtDataInicio.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data de início é obrigatória!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtDataInicio.requestFocus();
            return false;
        }

        if (txtDataFim.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data de fim é obrigatória!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtDataFim.requestFocus();
            return false;
        }

        // Validar formato das datas
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            Date dataInicio = sdf.parse(txtDataInicio.getText());
            Date dataFim = sdf.parse(txtDataFim.getText());

            if (dataFim.before(dataInicio)) {
                JOptionPane.showMessageDialog(this, "Data de fim deve ser posterior à data de início!", "Validação", JOptionPane.WARNING_MESSAGE);
                txtDataFim.requestFocus();
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido! Use dd/MM/yyyy", "Validação", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    private void limparCampos() {
        txtNome.setText("");
        txtDestino.setText("");
        txtDescricao.setText("");
        txtPreco.setText("");
        txtDataInicio.setText("");
        txtDataFim.setText("");
        txtVagas.setText("");
    }

    private void configurarJanela() {
        setSize(600, 500);
        setLocationRelativeTo(getParent());
        setResizable(false);
    }
} 