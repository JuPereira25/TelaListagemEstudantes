package TelaListagemEstudantes.java;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class ListaAlunosGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private List<Aluno> alunos;
    private DefaultTableModel model;
    private JTable tabela;

    public ListaAlunosGUI() {
        setTitle("Lista de Alunos");
        setSize(500, 400);

        // Adiciona um listener para fechar a janela
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        // Inicializa a lista de alunos
        alunos = getAlunos();

        // Nomes das colunas
        String[] colunas = {"Nome", "Idade", "Matrícula"};

        // Modelo de tabela
        model = new DefaultTableModel(colunas, 0);
        tabela = new JTable(model);
        atualizarTabela();

        // Adiciona a tabela ao painel de rolagem
        JScrollPane scrollPane = new JScrollPane(tabela);
        add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel panel = new JPanel();
        JButton addButton = new JButton("Adicionar");
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Excluir");

        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
        add(panel, BorderLayout.SOUTH);

        // Ação do botão Adicionar
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarAluno();
            }
        });

        // Ação do botão Editar
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarAluno();
            }
        });

        // Ação do botão Excluir
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirAluno();
            }
        });
    }

    private void adicionarAluno() {
        String nome = JOptionPane.showInputDialog("Nome do aluno:");
        String idadeStr = JOptionPane.showInputDialog("Idade do aluno:");
        String matricula = JOptionPane.showInputDialog("Matrícula do aluno:");

        if (nome != null && idadeStr != null && matricula != null) {
            try {
                int idade = Integer.parseInt(idadeStr);
                Aluno aluno = new Aluno(nome, idade, matricula);
                alunos.add(aluno);
                atualizarTabela();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Idade inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarAluno() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow >= 0) {
            Aluno aluno = alunos.get(selectedRow);
            String nome = JOptionPane.showInputDialog("Nome do aluno:", aluno.getNome());
            String idadeStr = JOptionPane.showInputDialog("Idade do aluno:", aluno.getIdade());
            String matricula = JOptionPane.showInputDialog("Matrícula do aluno:", aluno.getMatricula());

            if (nome != null && idadeStr != null && matricula != null) {
                try {
                    int idade = Integer.parseInt(idadeStr);
                    aluno.setNome(nome);
                    aluno.setIdade(idade);
                    aluno.setMatricula(matricula);
                    atualizarTabela();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Idade inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum aluno selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirAluno() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow >= 0) {
            alunos.remove(selectedRow);
            atualizarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum aluno selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarTabela() {
        model.setRowCount(0);
        for (Aluno aluno : alunos) {
            model.addRow(new Object[]{aluno.getNome(), aluno.getIdade(), aluno.getMatricula()});
        }
    }

    private List<Aluno> getAlunos() {
        // Lista de alunos de exemplo
        List<Aluno> alunos = new ArrayList<>();
        alunos.add(new Aluno("João Silva", 20, "202101"));
        alunos.add(new Aluno("Maria Oliveira", 21, "202102"));
        alunos.add(new Aluno("Pedro Sousa", 22, "202103"));
        return alunos;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ListaAlunosGUI frame = new ListaAlunosGUI();
            frame.setVisible(true);
        });
    }
}

