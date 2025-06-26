package com.agenciaturismo.gui;

import com.agenciaturismo.dao.ClienteDAO;
import com.agenciaturismo.dao.PacoteViagemDAO;
import com.agenciaturismo.dao.ContratoDAO;
import com.agenciaturismo.model.Cliente;
import com.agenciaturismo.model.PacoteViagem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class TelaConsultaContratos extends JDialog {
    private JComboBox<String> cbTipoConsulta;
    private JComboBox<Cliente> cbClientes;
    private JComboBox<PacoteViagem> cbPacotes;
    private JTable tabelaResultados;
    private DefaultTableModel modeloTabela;
    private ClienteDAO clienteDAO;
    private PacoteViagemDAO pacoteDAO;
    private ContratoDAO contratoDAO;

    public TelaConsultaContratos(Frame parent) {
        super(parent, "Consulta de Contratos", true);
        this.clienteDAO = new ClienteDAO();
        this.pacoteDAO = new PacoteViagemDAO();
        this.contratoDAO = new ContratoDAO();
        initComponents();
        configurarJanela();
        carregarDados();
    }

    private void initComponents() {
        setLayout(new BorderLayout());


        JPanel painelConsulta = new JPanel(new GridBagLayout());
        painelConsulta.setBorder(BorderFactory.createTitledBorder("Consultar Contratos"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);


        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        painelConsulta.add(new JLabel("Tipo de Consulta:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        cbTipoConsulta = new JComboBox<>(new String[]{
            "Pacotes por Cliente", "Clientes por Pacote"
        });
        cbTipoConsulta.addActionListener(e -> atualizarCamposConsulta());
        painelConsulta.add(cbTipoConsulta, gbc);


        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        painelConsulta.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        cbClientes = new JComboBox<>();
        cbClientes.setPreferredSize(new Dimension(300, 25));
        painelConsulta.add(cbClientes, gbc);


        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        painelConsulta.add(new JLabel("Pacote:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        cbPacotes = new JComboBox<>();
        cbPacotes.setPreferredSize(new Dimension(300, 25));
        painelConsulta.add(cbPacotes, gbc);


        gbc.gridx = 2; gbc.gridy = 1; gbc.gridheight = 2; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnConsultar = new JButton("Consultar");
        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarConsulta();
            }
        });
        painelConsulta.add(btnConsultar, gbc);

        add(painelConsulta, BorderLayout.NORTH);

        // Tabela de resultados
        String[] colunas = {"Nome", "Destino/Documento", "Preço/Tipo", "Data Início", "Data Fim"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaResultados = new JTable(modeloTabela);
        tabelaResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tabelaResultados);
        scrollPane.setPreferredSize(new Dimension(700, 350));
        
        add(scrollPane, BorderLayout.CENTER);


        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        painelBotoes.add(btnFechar);

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

        atualizarCamposConsulta();
    }

    private void atualizarCamposConsulta() {
        String tipoConsulta = cbTipoConsulta.getSelectedItem().toString();
        
        if ("Pacotes por Cliente".equals(tipoConsulta)) {
            cbClientes.setEnabled(true);
            cbPacotes.setEnabled(false);
            // Atualizar cabeçalhos da tabela
            modeloTabela.setColumnIdentifiers(new String[]{
                "Pacote", "Destino", "Preço", "Data Início", "Data Fim"
            });
        } else {
            cbClientes.setEnabled(false);
            cbPacotes.setEnabled(true);
            // Atualizar cabeçalhos da tabela
            modeloTabela.setColumnIdentifiers(new String[]{
                "Cliente", "E-mail", "Tipo", "Documento", "Telefone"
            });
        }
        
        modeloTabela.setRowCount(0);
    }

    private void realizarConsulta() {
        String tipoConsulta = cbTipoConsulta.getSelectedItem().toString();
        modeloTabela.setRowCount(0);

        if ("Pacotes por Cliente".equals(tipoConsulta)) {
            Cliente clienteSelecionado = (Cliente) cbClientes.getSelectedItem();
            if (clienteSelecionado == null) {
                JOptionPane.showMessageDialog(this, "Selecione um cliente!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            List<PacoteViagem> pacotes = contratoDAO.buscarPacotesPorCliente(clienteSelecionado.getId());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            if (pacotes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Este cliente não possui pacotes contratados!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                for (PacoteViagem pacote : pacotes) {
                    Object[] linha = {
                        pacote.getNome(),
                        pacote.getDestino(),
                        "R$ " + pacote.getPreco(),
                        sdf.format(pacote.getDataInicio()),
                        sdf.format(pacote.getDataFim())
                    };
                    modeloTabela.addRow(linha);
                }
            }
        } else {
            PacoteViagem pacoteSelecionado = (PacoteViagem) cbPacotes.getSelectedItem();
            if (pacoteSelecionado == null) {
                JOptionPane.showMessageDialog(this, "Selecione um pacote!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            List<Cliente> clientes = contratoDAO.buscarClientesPorPacote(pacoteSelecionado.getId());
            
            if (clientes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Este pacote não possui clientes contratantes!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                for (Cliente cliente : clientes) {
                    Object[] linha = {
                        cliente.getNome(),
                        cliente.getEmail(),
                        cliente.getTipoCliente(),
                        cliente.getDocumento(),
                        cliente.getTelefone()
                    };
                    modeloTabela.addRow(linha);
                }
            }
        }
    }

    private void configurarJanela() {
        setSize(750, 550);
        setLocationRelativeTo(getParent());
        setResizable(true);
    }
} 