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
                                btn.setValor(Integer.MAX_VALUE);
                                startX = row;
                                startY = col;
                                btn.setText("Inicio ");
                                btn.setBackground(Color.magenta);
                                btn.setStar(true);
                                cuenta = 1;
                            }
                        } else if (cuenta == 1) {
                            int confirm = JOptionPane.showConfirmDialog(frame, "¿Desea que esta sea la meta?", "Meta",
                                    JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                btn.setGoal(true);
                                btn.setValor(Integer.MAX_VALUE);
                                btn.setText("Meta");
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
                        if (btn.getValor() != Integer.MAX_VALUE){
                            int weight = Math.abs(startX - i) + Math.abs(startY - j); // Distancia Manhattan
                            btn.setValor(weight);
                            if(weight+1 == cuenta){
                                btn.setText("(" + i + "," + j + ") - Peso: " + btn.getValor());
                                btn.setBackground(Color.yellow);
                            }
                        }
                    }
                }
                cuenta++;
            }
        });

        btnRetroceder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botón Retroceder presionado.");
                // Agrega aquí la lógica para avanzar
                // Lógica para avanzar
                for (int i = 0; i < numRows; i++) {
                    for (int j = 0; j < numCols; j++) {
                        MyButton btn = buttons[i][j];
                        if (btn.getValor() != Integer.MAX_VALUE){
                            int weight = Math.abs(startX - i) + Math.abs(startY - j); // Distancia Manhattan
                            btn.setValor(weight);
                            if(weight == cuenta-1){
                                btn.setText("(" + i + "," + j + ") - Peso: " + btn.getValor());
                                btn.setBackground(Color.cyan);
                            }
                        }
                    }
                }
                cuenta--;
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
        private boolean goal;
        private boolean star;

        public MyButton(int row, int col) {
            super("(" + row + "," + col + ")");
            this.row = row;
            this.col = col;
            this.valor = 1;
            this.goal = false;
            this.star = false;
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

        public boolean isGoal() {
            return goal;
        }

        public void setGoal(boolean goal) {
            this.goal = goal;
        }
        
        public boolean isStar() {
            return star;
        }

        public void setStar(boolean star) {
            this.star = star;
        }
    }

    public static void main(String[] args) {
        ShortPath shortPath = new ShortPath();
        int numRows = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de filas: "));
        int numCols = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de columnas: "));
        shortPath.generateButtons(numRows, numCols);
    }
}