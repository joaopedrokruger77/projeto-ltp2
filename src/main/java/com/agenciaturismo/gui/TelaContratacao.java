package com.agenciaturismo.gui;

import com.agenciaturismo.dao.ClienteDAO;
import com.agenciaturismo.dao.PacoteViagemDAO;
import com.agenciaturismo.dao.ContratoDAO;
import com.agenciaturismo.model.Cliente;
import com.agenciaturismo.model.PacoteViagem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaContratacao extends JDialog {
    private JComboBox<Cliente> cbClientes;
    private JComboBox<PacoteViagem> cbPacotes;
    private JTextArea txtDetalhes;
    private ClienteDAO clienteDAO;
    private PacoteViagemDAO pacoteDAO;
    private ContratoDAO contratoDAO;

    public TelaContratacao(Frame parent) {
        super(parent, "Contratação de Pacote", true);
        this.clienteDAO = new ClienteDAO();
        this.pacoteDAO = new PacoteViagemDAO();
        this.contratoDAO = new ContratoDAO();
        initComponents();
        configurarJanela();
        carregarDados();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Painel principal
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBorder(BorderFactory.createTitledBorder("Contratação de Pacote"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Cliente
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        painelPrincipal.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        cbClientes = new JComboBox<>();
        cbClientes.setPreferredSize(new Dimension(300, 25));
        painelPrincipal.add(cbClientes, gbc);


        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        painelPrincipal.add(new JLabel("Pacote:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        cbPacotes = new JComboBox<>();
        cbPacotes.setPreferredSize(new Dimension(300, 25));
        cbPacotes.addActionListener(e -> atualizarDetalhes());
        painelPrincipal.add(cbPacotes, gbc);


        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        painelPrincipal.add(new JLabel("Detalhes:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; gbc.weighty = 1.0;
        txtDetalhes = new JTextArea(8, 30);
        txtDetalhes.setEditable(false);
        txtDetalhes.setBackground(getBackground());
        txtDetalhes.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane scrollDetalhes = new JScrollPane(txtDetalhes);
        painelPrincipal.add(scrollDetalhes, gbc);

        add(painelPrincipal, BorderLayout.CENTER);


        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnContratar = new JButton("Contratar");
        JButton btnCancelar = new JButton("Cancelar");

        btnContratar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contratarPacote();
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        painelBotoes.add(btnContratar);
        painelBotoes.add(btnCancelar);

        add(painelBotoes, BorderLayout.SOUTH);
    }

    private void carregarDados() {

        cbClientes.removeAllItems();
        List<Cliente> clientes = clienteDAO.listarTodos();
        for (Cliente cliente : clientes) {
            cbClientes.addItem(cliente);
        }


        cbPacotes.removeAllItems();
        List<PacoteViagem> pacotes = pacoteDAO.listarTodos();
        for (PacoteViagem pacote : pacotes) {
            cbPacotes.addItem(pacote);
        }

        atualizarDetalhes();
    }

    private void atualizarDetalhes() {
        PacoteViagem pacoteSelecionado = (PacoteViagem) cbPacotes.getSelectedItem();
        if (pacoteSelecionado != null) {
            StringBuilder detalhes = new StringBuilder();
            detalhes.append("Nome: ").append(pacoteSelecionado.getNome()).append("\n");
            detalhes.append("Destino: ").append(pacoteSelecionado.getDestino()).append("\n");
            detalhes.append("Descrição: ").append(pacoteSelecionado.getDescricao()).append("\n");
            detalhes.append("Preço: R$ ").append(pacoteSelecionado.getPreco()).append("\n");
            
            if (pacoteSelecionado.getDataInicio() != null) {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
                detalhes.append("Data Início: ").append(sdf.format(pacoteSelecionado.getDataInicio())).append("\n");
                detalhes.append("Data Fim: ").append(sdf.format(pacoteSelecionado.getDataFim())).append("\n");
            }
            
            detalhes.append("Vagas Disponíveis: ").append(pacoteSelecionado.getVagasDisponiveis());
            
            txtDetalhes.setText(detalhes.toString());
        } else {
            txtDetalhes.setText("");
        }
    }

    private void contratarPacote() {
        Cliente clienteSelecionado = (Cliente) cbClientes.getSelectedItem();
        PacoteViagem pacoteSelecionado = (PacoteViagem) cbPacotes.getSelectedItem();

        if (clienteSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente!", "Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (pacoteSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um pacote!", "Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (pacoteSelecionado.getVagasDisponiveis() <= 0) {
            JOptionPane.showMessageDialog(this, "Este pacote não possui vagas disponíveis!", "Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Confirmar contratação
        int confirmacao = JOptionPane.showConfirmDialog(
            this,
            "Confirma a contratação do pacote '" + pacoteSelecionado.getNome() + 
            "' para o cliente '" + clienteSelecionado.getNome() + "'?",
            "Confirmar Contratação",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            if (contratoDAO.contratarPacote(clienteSelecionado.getId(), pacoteSelecionado.getId())) {
                JOptionPane.showMessageDialog(this, "Pacote contratado com sucesso!");
                
                // Atualizar vagas do pacote
                pacoteSelecionado.setVagasDisponiveis(pacoteSelecionado.getVagasDisponiveis() - 1);
                carregarDados(); // Recarregar dados para atualizar as vagas
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao contratar pacote!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void configurarJanela() {
        setSize(500, 450);
        setLocationRelativeTo(getParent());
        setResizable(false);
    }
} 