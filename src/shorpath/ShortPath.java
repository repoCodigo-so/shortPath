/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package shorpath;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ShortPath {
    private JFrame frame;
    private JPanel panel;
    private MyButton[][] buttons;
    private int numRows;
    private int numCols;
    private int cuenta;
    private int startX;
    private int startY;

    public ShortPath() {
        frame = new JFrame("Short Path");
        panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public void generateButtons(int numRows, int numCols) {
        cuenta = 0;
        this.numRows = numRows;
        this.numCols = numCols;
        buttons = new MyButton[numRows][numCols];
        panel.setLayout(new GridLayout(numRows, numCols));

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                buttons[i][j] = new MyButton(i, j);
                panel.add(buttons[i][j]);
                buttons[i][j].addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        MyButton btn = (MyButton) e.getSource();
                        int row = btn.getRow();
                        int col = btn.getCol();
                        if (cuenta == 0) {
                            int confirm = JOptionPane.showConfirmDialog(frame,
                                    "¿Desea bloquear este botón y establecer su peso como infinito?", "Bloquear botón",
                                    JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                btn.setValor(Integer.MAX_VALUE);
                                btn.setText("(" + row + "," + col + ") - Peso: ∞");
                                btn.setBackground(Color.blue);
                                btn.setEnabled(false);
                                
                            } else {
                                btn.setValor(0);
                                startX = row;
                                startY = col;
                                btn.setText("(" + row + "," + col + ") - Valor: " + btn.getValor());
                                btn.setBackground(Color.magenta);
                                cuenta = 1;
                            }
                        } else if (cuenta == 1) {
                            int confirm = JOptionPane.showConfirmDialog(frame, "¿Desea que esta sea la meta?", "Meta",
                                    JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                btn.setValor(Integer.MIN_VALUE);
                                btn.setText("(" + row + "," + col + ") - Peso: -∞");
                                btn.setBackground(Color.orange);
                                cuenta = 2;
                            }
                        }
                    }
                });
            }
        }

        JButton btnAvanzar = new JButton("Avanzar");
        JButton btnRetroceder = new JButton("Retroceder");

        btnAvanzar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botón Avanzar presionado.");
                // Agrega aquí la lógica para avanzar
                // Lógica para avanzar
                for (int i = 0; i < numRows; i++) {
                    for (int j = 0; j < numCols; j++) {
                        MyButton btn = buttons[i][j];
                        if (btn.getValor() == 1) {
                            // Calcula el peso basado en la distancia al botón de valor 0
                        int weight = Math.abs(startX - i) + Math.abs(startY - j); // Distancia Manhattan
                        btn.setValor(weight);

                        /*// Encuentra el botón con el menor peso adyacente
                        int minWeight = Integer.MAX_VALUE;
                        MyButton minBtn = null;
                        if (i > 0 && buttons[i - 1][j].getValor() < minWeight) {
                            minWeight = buttons[i - 1][j].getValor();
                            minBtn = buttons[i - 1][j];
                        }
                        if (i < numRows - 1 && buttons[i + 1][j].getValor() < minWeight) {
                            minWeight = buttons[i + 1][j].getValor();
                            minBtn = buttons[i + 1][j];
                        }
                        if (j > 0 && buttons[i][j - 1].getValor() < minWeight) {
                            minWeight = buttons[i][j - 1].getValor();
                            minBtn = buttons[i][j - 1];
                        }
                        if (j < numCols - 1 && buttons[i][j + 1].getValor() < minWeight) {
                            minWeight = buttons[i][j + 1].getValor();
                            minBtn = buttons[i][j + 1];
                        }

                        // Actualiza el peso y texto del botón con el menor peso adyacente
                        if (minBtn != null) {
                            btn.setValor(btn.getValor() + minWeight);
                            btn.setText("(" + i + "," + j + ") - Peso: " + btn.getValor());
                            btn.setBackground(Color.yellow);
                        }*/
                        btn.setText("(" + i + "," + j + ") - Peso: " + btn.getValor());
                        btn.setBackground(Color.yellow);
                        }
                    }
                }

                cuenta = 3;
            }
        });

        btnRetroceder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botón Retroceder presionado.");
                // Agrega aquí la lógica para retroceder
            }
        });

        frame.add(btnAvanzar, BorderLayout.WEST);
        frame.add(btnRetroceder, BorderLayout.EAST);
        frame.pack();
}


    private class MyButton extends JButton {
        private int valor;
        private int row;
        private int col;

        public MyButton(int row, int col) {
            super("(" + row + "," + col + ")");
            this.row = row;
            this.col = col;
            this.valor = 1;
            this.setText("(" + row + "," + col + ") - Valor: " + valor);
        }

        public int getValor() {
            return valor;
        }

        public void setValor(int valor) {
            this.valor = valor;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }
    }

    public static void main(String[] args) {
        ShortPath shortPath = new ShortPath();
        int numRows = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de filas: "));
        int numCols = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de columnas: "));
        shortPath.generateButtons(numRows, numCols);
    }
}