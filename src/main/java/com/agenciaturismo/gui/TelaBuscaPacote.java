package com.agenciaturismo.gui;

import com.agenciaturismo.dao.PacoteViagemDAO;
import com.agenciaturismo.model.PacoteViagem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

public class TelaBuscaPacote extends JDialog {
    private JTextField txtDestinoBusca;
    private JTable tabelaResultados;
    private DefaultTableModel modeloTabela;
    private PacoteViagemDAO pacoteDAO;

    public TelaBuscaPacote(Frame parent) {
        super(parent, "Buscar Pacote", true);
        this.pacoteDAO = new PacoteViagemDAO();
        initComponents();
        configurarJanela();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Painel de busca
        JPanel painelBusca = new JPanel(new FlowLayout());
        painelBusca.setBorder(BorderFactory.createTitledBorder("Buscar Pacote"));
        
        painelBusca.add(new JLabel("Destino:"));
        txtDestinoBusca = new JTextField(20);
        painelBusca.add(txtDestinoBusca);
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarPacotes();
            }
        });
        painelBusca.add(btnBuscar);

        add(painelBusca, BorderLayout.NORTH);

        // Tabela de resultados
        String[] colunas = {"ID", "Nome", "Destino", "Preço", "Data Início", "Data Fim", "Vagas"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaResultados = new JTable(modeloTabela);
        tabelaResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tabelaResultados);
        scrollPane.setPreferredSize(new Dimension(800, 350));
        
        add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        painelBotoes.add(btnFechar);

        add(painelBotoes, BorderLayout.SOUTH);

        // Listener para Enter no campo de busca
        txtDestinoBusca.addActionListener(e -> buscarPacotes());
    }

    private void buscarPacotes() {
        String destinoBusca = txtDestinoBusca.getText().trim();
        
        if (destinoBusca.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite um destino para buscar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            txtDestinoBusca.requestFocus();
            return;
        }

        modeloTabela.setRowCount(0);
        
        List<PacoteViagem> pacotes = pacoteDAO.buscarPorDestino(destinoBusca);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        if (pacotes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum pacote encontrado para esse destino!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (PacoteViagem pacote : pacotes) {
                Object[] linha = {
                    pacote.getId(),
                    pacote.getNome(),
                    pacote.getDestino(),
                    "R$ " + pacote.getPreco(),
                    sdf.format(pacote.getDataInicio()),
                    sdf.format(pacote.getDataFim()),
                    pacote.getVagasDisponiveis()
                };
                modeloTabela.addRow(linha);
            }
        }
    }

    private void configurarJanela() {
        setSize(850, 500);
        setLocationRelativeTo(getParent());
        setResizable(true);
    }
} 