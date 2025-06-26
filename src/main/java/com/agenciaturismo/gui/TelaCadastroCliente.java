package com.agenciaturismo.gui;

import com.agenciaturismo.dao.ClienteDAO;
import com.agenciaturismo.model.ClienteNacional;
import com.agenciaturismo.model.ClienteEstrangeiro;
import com.agenciaturismo.util.ValidadorEmail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TelaCadastroCliente extends JDialog {
    private JTextField txtNome;
    private JTextField txtEmail;
    private JTextField txtTelefone;
    private JTextField txtDataNascimento;
    private JRadioButton rbNacional;
    private JRadioButton rbEstrangeiro;
    private JTextField txtCpf;
    private JTextField txtPassaporte;
    private JTextField txtNacionalidade;
    private JPanel painelDocumento;
    private ClienteDAO clienteDAO;

    public TelaCadastroCliente(Frame parent) {
        super(parent, "Cadastro de Cliente", true);
        this.clienteDAO = new ClienteDAO();
        initComponents();
        configurarJanela();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Painel principal
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBorder(BorderFactory.createTitledBorder("Dados do Cliente"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Nome
        gbc.gridx = 0; gbc.gridy = 0;
        painelPrincipal.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtNome = new JTextField(20);
        painelPrincipal.add(txtNome, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        painelPrincipal.add(new JLabel("E-mail:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtEmail = new JTextField(20);
        painelPrincipal.add(txtEmail, gbc);

        // Telefone
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        painelPrincipal.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtTelefone = new JTextField(20);
        painelPrincipal.add(txtTelefone, gbc);

        // Data de Nascimento do Cliente
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        painelPrincipal.add(new JLabel("Data Nascimento (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtDataNascimento = new JTextField(20);
        painelPrincipal.add(txtDataNascimento, gbc);

        // Tipo de Cliente
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        painelPrincipal.add(new JLabel("Tipo de Cliente:"), gbc);
        
        JPanel painelTipo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rbNacional = new JRadioButton("Nacional", true);
        rbEstrangeiro = new JRadioButton("Estrangeiro");
        ButtonGroup bgTipo = new ButtonGroup();
        bgTipo.add(rbNacional);
        bgTipo.add(rbEstrangeiro);
        
        rbNacional.addActionListener(e -> atualizarCamposDocumento());
        rbEstrangeiro.addActionListener(e -> atualizarCamposDocumento());
        
        painelTipo.add(rbNacional);
        painelTipo.add(rbEstrangeiro);
        
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        painelPrincipal.add(painelTipo, gbc);

        // Painel para documentos do cliente
        painelDocumento = new JPanel(new CardLayout());
        
        // Painel CPF valido
        JPanel painelCpf = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelCpf.add(new JLabel("CPF:"));
        txtCpf = new JTextField(15);
        painelCpf.add(txtCpf);
        
        // Painel Passaporte Cliente
        JPanel painelPassaporte = new JPanel(new GridLayout(2, 2, 5, 5));
        painelPassaporte.add(new JLabel("Passaporte:"));
        txtPassaporte = new JTextField(15);
        painelPassaporte.add(txtPassaporte);
        painelPassaporte.add(new JLabel("Nacionalidade:"));
        txtNacionalidade = new JTextField(15);
        painelPassaporte.add(txtNacionalidade);
        
        painelDocumento.add(painelCpf, "Nacional");
        painelDocumento.add(painelPassaporte, "Estrangeiro");
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        painelPrincipal.add(painelDocumento, gbc);

        add(painelPrincipal, BorderLayout.CENTER);

        // Painel de botões para executar
        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarCliente();
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);

        add(painelBotoes, BorderLayout.SOUTH);
        
        atualizarCamposDocumento();
    }

    private void atualizarCamposDocumento() {
        CardLayout cl = (CardLayout) painelDocumento.getLayout();
        if (rbNacional.isSelected()) {
            cl.show(painelDocumento, "Nacional");
        } else {
            cl.show(painelDocumento, "Estrangeiro");
        }
    }

    private void salvarCliente() {
        // Validações dos Campos
        if (!validarCampos()) {
            return;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dataNascimento = sdf.parse(txtDataNascimento.getText());

            if (rbNacional.isSelected()) {
                ClienteNacional cliente = new ClienteNacional(
                    txtNome.getText(),
                    txtEmail.getText(),
                    txtTelefone.getText(),
                    dataNascimento,
                    txtCpf.getText()
                );

                if (clienteDAO.inserir(cliente)) {
                    JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                ClienteEstrangeiro cliente = new ClienteEstrangeiro(
                    txtNome.getText(),
                    txtEmail.getText(),
                    txtTelefone.getText(),
                    dataNascimento,
                    txtPassaporte.getText(),
                    txtNacionalidade.getText()
                );

                if (clienteDAO.inserir(cliente)) {
                    JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao processar dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarCampos() {
        // Validar campos obrigatórios com tela de erros
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome é obrigatório!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtNome.requestFocus();
            return false;
        }

        if (txtEmail.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "E-mail é obrigatório!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtEmail.requestFocus();
            return false;
        }

        // Validar formato do e-mail pra exigir @
        if (!ValidadorEmail.validar(txtEmail.getText())) {
            JOptionPane.showMessageDialog(this, "Formato de e-mail inválido!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtEmail.requestFocus();
            return false;
        }

        if (txtTelefone.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Telefone é obrigatório!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtTelefone.requestFocus();
            return false;
        }

        if (txtDataNascimento.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Data de nascimento é obrigatória!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtDataNascimento.requestFocus();
            return false;
        }

        // Validar formato da data
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            sdf.parse(txtDataNascimento.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Formato de data inválido! Use dd/MM/yyyy", "Validação", JOptionPane.WARNING_MESSAGE);
            txtDataNascimento.requestFocus();
            return false;
        }

        // Validar documentos específicos
        if (rbNacional.isSelected()) {
            if (txtCpf.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "CPF é obrigatório!", "Validação", JOptionPane.WARNING_MESSAGE);
                txtCpf.requestFocus();
                return false;
            }

            if (!ClienteNacional.validarCPF(txtCpf.getText().replaceAll("[^0-9]", ""))) {
                JOptionPane.showMessageDialog(this, "CPF inválido!", "Validação", JOptionPane.WARNING_MESSAGE);
                txtCpf.requestFocus();
                return false;
            }
        } else {
            if (txtPassaporte.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Passaporte é obrigatório!", "Validação", JOptionPane.WARNING_MESSAGE);
                txtPassaporte.requestFocus();
                return false;
            }

            if (!ClienteEstrangeiro.validarPassaporte(txtPassaporte.getText())) {
                JOptionPane.showMessageDialog(this, "Passaporte inválido!", "Validação", JOptionPane.WARNING_MESSAGE);
                txtPassaporte.requestFocus();
                return false;
            }

            if (txtNacionalidade.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nacionalidade é obrigatória!", "Validação", JOptionPane.WARNING_MESSAGE);
                txtNacionalidade.requestFocus();
                return false;
            }
        }

        return true;
    }

    private void limparCampos() {
        txtNome.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        txtDataNascimento.setText("");
        txtCpf.setText("");
        txtPassaporte.setText("");
        txtNacionalidade.setText("");
        rbNacional.setSelected(true);
        atualizarCamposDocumento();
    }

    private void configurarJanela() {
        setSize(500, 400);
        setLocationRelativeTo(getParent());
        setResizable(false);
    }
} 