package com.agenciaturismo.gui;

import com.agenciaturismo.dao.ClienteDAO;
import com.agenciaturismo.model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.List;

public class TelaBuscaCliente extends JDialog {

    private JTextField txtNomeBusca;
    private JTable tabelaResultados;
    private DefaultTableModel modeloTabela;
    private final ClienteDAO clienteDAO = new ClienteDAO();

    public TelaBuscaCliente(Frame parent) {
        super(parent, "Buscar Cliente", true);
        initComponents();
        configurarJanela();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        add(criarPainelBusca(), BorderLayout.NORTH);
        add(criarTabelaResultados(), BorderLayout.CENTER);
        add(criarPainelBotoes(), BorderLayout.SOUTH);
    }

    private JPanel criarPainelBusca() {
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.setBorder(BorderFactory.createTitledBorder("Buscar Cliente"));

        painelBusca.add(new JLabel("Nome:"));

        txtNomeBusca = new JTextField(20);
        painelBusca.add(txtNomeBusca);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setMnemonic(KeyEvent.VK_B);
        btnBuscar.addActionListener(e -> buscarClientes());
        painelBusca.add(btnBuscar);

        txtNomeBusca.addActionListener(e -> buscarClientes());

        return painelBusca;
    }

    private JScrollPane criarTabelaResultados() {
        String[] colunas = {"ID", "Nome", "E-mail", "Telefone", "Data Nascimento", "Tipo", "Documento"};
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
        return scrollPane;
    }

    private JPanel criarPainelBotoes() {
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnFechar = new JButton("Fechar");
        btnFechar.setMnemonic(KeyEvent.VK_F);
        btnFechar.addActionListener(e -> dispose());
        painelBotoes.add(btnFechar);

        return painelBotoes;
    }

    private void buscarClientes() {
        String nomeBusca = txtNomeBusca.getText().trim();

        if (nomeBusca.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite um nome para buscar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            txtNomeBusca.requestFocus();
            return;
        }

        List<Cliente> clientes = clienteDAO.buscarPorNome(nomeBusca);
        modeloTabela.setRowCount(0);

        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum cliente encontrado com esse nome!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            preencherTabela(clientes);
        }
    }

    private void preencherTabela(List<Cliente> clientes) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (Cliente cliente : clientes) {
            modeloTabela.addRow(new Object[]{
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getEmail(),
                    cliente.getTelefone(),
                    sdf.format(cliente.getDataNascimento()),
                    cliente.getTipoCliente(),
                    cliente.getDocumento()
            });
        }
    }

    private void configurarJanela() {
        setSize(750, 500);
        setLocationRelativeTo(getParent());
        setResizable(true);
        getRootPane().setDefaultButton(null); // Ou: getRootPane().setDefaultButton(btnBuscar); se quiser ativar Enter
    }
}