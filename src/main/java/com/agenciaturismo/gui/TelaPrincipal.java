package com.agenciaturismo.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaPrincipal extends JFrame {
    private JPanel painelPrincipal;
    private JMenuBar menuBar;

    public TelaPrincipal() {
        initComponents();
        configurarJanela();
    }

    private void initComponents() {
        // Configurar menu
        menuBar = new JMenuBar();
        
        // Menu Clientes
        JMenu menuClientes = new JMenu("Clientes");
        JMenuItem itemCadastrarCliente = new JMenuItem("Cadastrar Cliente");
        JMenuItem itemListarClientes = new JMenuItem("Listar Clientes");
        JMenuItem itemBuscarCliente = new JMenuItem("Buscar Cliente");
        
        itemCadastrarCliente.addActionListener(e -> abrirTelaCadastroCliente());
        itemListarClientes.addActionListener(e -> abrirTelaListagemClientes());
        itemBuscarCliente.addActionListener(e -> abrirTelaBuscaCliente());
        
        menuClientes.add(itemCadastrarCliente);
        menuClientes.add(itemListarClientes);
        menuClientes.add(itemBuscarCliente);
        
        // Menu Pacotes
        JMenu menuPacotes = new JMenu("Pacotes");
        JMenuItem itemCadastrarPacote = new JMenuItem("Cadastrar Pacote");
        JMenuItem itemListarPacotes = new JMenuItem("Listar Pacotes");
        JMenuItem itemBuscarPacote = new JMenuItem("Buscar Pacote");
        
        itemCadastrarPacote.addActionListener(e -> abrirTelaCadastroPacote());
        itemListarPacotes.addActionListener(e -> abrirTelaListagemPacotes());
        itemBuscarPacote.addActionListener(e -> abrirTelaBuscaPacote());
        
        menuPacotes.add(itemCadastrarPacote);
        menuPacotes.add(itemListarPacotes);  
        menuPacotes.add(itemBuscarPacote);
        
        // Menu Serviços
        JMenu menuServicos = new JMenu("Serviços");
        JMenuItem itemCadastrarServico = new JMenuItem("Cadastrar Serviço");
        JMenuItem itemListarServicos = new JMenuItem("Listar Serviços");
        
        itemCadastrarServico.addActionListener(e -> abrirTelaCadastroServico());
        itemListarServicos.addActionListener(e -> abrirTelaListagemServicos());
        
        menuServicos.add(itemCadastrarServico);
        menuServicos.add(itemListarServicos);
        
        // Menu Contratos
        JMenu menuContratos = new JMenu("Contratos");
        JMenuItem itemContratarPacote = new JMenuItem("Contratar Pacote");
        JMenuItem itemConsultarContratos = new JMenuItem("Consultar Contratos");
        
        itemContratarPacote.addActionListener(e -> abrirTelaContratacao());
        itemConsultarContratos.addActionListener(e -> abrirTelaConsultaContratos());
        
        menuContratos.add(itemContratarPacote);
        menuContratos.add(itemConsultarContratos);
        
        menuBar.add(menuClientes);
        menuBar.add(menuPacotes);
        menuBar.add(menuServicos);
        menuBar.add(menuContratos);
        
        setJMenuBar(menuBar);
        
        // Painel principal com imagem de fundo
        painelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                // Gradiente de fundo
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(135, 206, 235), // Azul claro
                    getWidth(), getHeight(), new Color(25, 25, 112) // Azul escuro
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        painelPrincipal.setLayout(new BorderLayout());
        
        // Painel de boas-vindas
        JPanel painelBoasVindas = new JPanel();
        painelBoasVindas.setOpaque(false);
        painelBoasVindas.setLayout(new BoxLayout(painelBoasVindas, BoxLayout.Y_AXIS));
        
        JLabel lblTitulo = new JLabel("Sistema de Gerenciamento");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSubtitulo = new JLabel("Agência de Turismo");
        lblSubtitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblSubtitulo.setForeground(Color.WHITE);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblDescricao = new JLabel("Bem-vindo ao sistema! Use o menu acima para navegar.");
        lblDescricao.setFont(new Font("Arial", Font.PLAIN, 16));
        lblDescricao.setForeground(Color.WHITE);
        lblDescricao.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        painelBoasVindas.add(Box.createVerticalGlue());
        painelBoasVindas.add(lblTitulo);
        painelBoasVindas.add(Box.createVerticalStrut(10));
        painelBoasVindas.add(lblSubtitulo);
        painelBoasVindas.add(Box.createVerticalStrut(20));
        painelBoasVindas.add(lblDescricao);
        painelBoasVindas.add(Box.createVerticalGlue());
        
        painelPrincipal.add(painelBoasVindas, BorderLayout.CENTER);
        
        add(painelPrincipal);
    }

    private void configurarJanela() {
        setTitle("Sistema de Gerenciamento - Agência de Turismo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        
        // Ícone da aplicação
        try {
            setIconImage(Toolkit.getDefaultToolkit().getImage("icone.png"));
        } catch (Exception e) {
            // Ignorar se não encontrar o ícone
        }
    }

    // Métodos para abrir as diferentes telas
    private void abrirTelaCadastroCliente() {
        new TelaCadastroCliente(this).setVisible(true);
    }

    private void abrirTelaListagemClientes() {
        new TelaListagemClientes(this).setVisible(true);
    }

    private void abrirTelaBuscaCliente() {
        new TelaBuscaCliente(this).setVisible(true);
    }

    private void abrirTelaCadastroPacote() {
        new TelaCadastroPacote(this).setVisible(true);
    }

    private void abrirTelaListagemPacotes() {
        new TelaListagemPacotes(this).setVisible(true);
    }

    private void abrirTelaBuscaPacote() {
        new TelaBuscaPacote(this).setVisible(true);
    }

    private void abrirTelaCadastroServico() {
        new TelaCadastroServico(this).setVisible(true);
    }

    private void abrirTelaListagemServicos() {
        new TelaListagemServicos(this).setVisible(true);
    }

    private void abrirTelaContratacao() {
        new TelaContratacao(this).setVisible(true);
    }

    private void abrirTelaConsultaContratos() {
        new TelaConsultaContratos(this).setVisible(true);
    }

    public static void main(String[] args) {
        // Configurar Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }
} 