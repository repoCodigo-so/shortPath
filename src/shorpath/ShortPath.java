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
                buttons[i][j] = new MyButton("(" + i + "," + j + ")");
                panel.add(buttons[i][j]);
                buttons[i][j].addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        MyButton btn = (MyButton) e.getSource();
                        if(cuenta == 0){
                            int confirm = JOptionPane.showConfirmDialog(frame, "¿Desea bloquear este botón y establecer su peso como infinito?", "Bloquear botón", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                btn.setValor(Integer.MAX_VALUE);
                                btn.setText("(" + numRows + "," + numCols + ") - Peso: ∞");
                                btn.setEnabled(false);
                            }
                            else{
                                if (btn.getValor() == 1) {
                                    btn.setValor(0);
                                    cuenta = 1;
                                }
                                btn.setText("(" + numRows + "," + numCols + ") - Valor: " + btn.getValor());
                            }
                        }
                        else if(cuenta == 1){
                            int confirm = JOptionPane.showConfirmDialog(frame, "¿Desea que esta sea la meta?", "Meta", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                btn.setValor(Integer.MIN_VALUE);
                                btn.setText("(" + numRows + "," + numCols + ") - Peso: -∞");
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
                // Agrega aquí la lógica para avanzar en el camino
            }
        });

        btnRetroceder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botón Retroceder presionado.");
                // Agrega aquí la lógica para retroceder en el camino
            }
        });

        frame.add(btnAvanzar, BorderLayout.WEST);
        frame.add(btnRetroceder, BorderLayout.EAST);
        frame.pack();
    }

    private class MyButton extends JButton {
        private int valor;

        public MyButton(String text) {
            super(text);
            this.valor = 1;
        }

        public int getValor() {
            return valor;
        }

        public void setValor(int valor) {
            this.valor = valor;
        }
    }

    public static void main(String[] args) {
        ShortPath shortPath = new ShortPath();
        int numRows = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de filas: "));
        int numCols = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de columnas: "));
        shortPath.generateButtons(numRows, numCols);
    }
}
