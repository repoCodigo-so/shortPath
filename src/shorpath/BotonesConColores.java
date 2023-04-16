/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shorpath;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class BotonesConColores {

    private JFrame frame;
    private JPanel panel;
    private JButton[][] buttons;
    private Color[][] colors;
    private int numRows = 5; // Número de filas
    private int numCols = 5; // Número de columnas
    private int startRow = 0; // Fila de inicio
    private int startCol = 0; // Columna de inicio
    private int endRow = 4; // Fila de fin
    private int endCol = 4; // Columna de fin
    private int[] blockedRows = {1, 3}; // Filas bloqueadas
    private int[] blockedCols = {2, 3}; // Columnas bloqueadas
    private boolean[][] blocked;
    private int[][] distances;
    private JButton btnAvanzar;
    private JButton btnRegresar;

    public BotonesConColores() {
        frame = new JFrame("Botones con Colores");
        panel = new JPanel(new GridLayout(numRows, numCols));
        buttons = new JButton[numRows][numCols];
        colors = new Color[numRows][numCols];
        blocked = new boolean[numRows][numCols];
        distances = new int[numRows][numCols];
        btnAvanzar = new JButton("Avanzar");
        btnRegresar = new JButton("Regresar");

        // Inicializar los colores y bloqueos
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                buttons[i][j] = new JButton();
                colors[i][j] = Color.WHITE;
                blocked[i][j] = false;
                distances[i][j] = Integer.MAX_VALUE;
                panel.add(buttons[i][j]);
                final int row = i;
                final int col = j;
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!blocked[row][col]) {
                            colors[row][col] = Color.CYAN;
                            buttons[row][col].setBackground(colors[row][col]);
                        }
                    }
                });
            }
        }

        // Establecer el color de inicio y fin
        colors[startRow][startCol] = Color.GREEN;
        buttons[startRow][startCol].setBackground(colors[startRow][startCol]);
        colors[endRow][endCol] = Color.RED;
        buttons[endRow][endCol].setBackground(colors[endRow][endCol]);

        // Bloquear las casillas marcadas como bloqueadas
        for (int i = 0; i < blockedRows.length; i++) {
            blocked[blockedRows[i]][blockedCols[i]] = true;
            colors[blockedRows[i]][blockedCols[i]] = Color.BLACK;
            buttons[blockedRows[i]][blockedCols[i]].setBackground(colors[blockedRows[i]][blockedCols[i]]);
        }

        // Agregar el botón de avanzar y su listener
        btnAvanzar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                djikstra();
            }
        });

        //
// Agregar el botón de regresar y su listener
btnRegresar.addActionListener(new ActionListener() {
@Override
public void actionPerformed(ActionEvent e) {
regresar();
}
});
       
    frame.add(btnAvanzar, BorderLayout.WEST);
        frame.add(btnRegresar, BorderLayout.EAST);
    frame.add(panel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
}

// Implementar el algoritmo de Dijkstra para encontrar el camino más corto
private void djikstra() {
    distances[startRow][startCol] = 0;

    for (int i = 0; i < numRows * numCols; i++) {
        int minDistance = Integer.MAX_VALUE;
        int currentRow = -1;
        int currentCol = -1;

        // Encontrar el nodo con la distancia más corta
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (!blocked[row][col] && distances[row][col] < minDistance) {
                    minDistance = distances[row][col];
                    currentRow = row;
                    currentCol = col;
                }
            }
        }

        if (currentRow == -1 || currentCol == -1) {
            // No se encontró un camino
            break;
        }

        // Actualizar las distancias de los vecinos del nodo actual
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (!blocked[row][col] && !colors[row][col].equals(Color.CYAN)) {
                    int distance = Math.abs(row - currentRow) + Math.abs(col - currentCol);
                    if (distances[currentRow][currentCol] + distance < distances[row][col]) {
                        distances[row][col] = distances[currentRow][currentCol] + distance;
                    }
                }
            }
        }

        // Marcar el nodo actual como visitado
        colors[currentRow][currentCol] = Color.CYAN;
        buttons[currentRow][currentCol].setBackground(colors[currentRow][currentCol]);

        if (currentRow == endRow && currentCol == endCol) {
            // Se encontró el camino más corto
            break;
        }
    }
}

// Regresar al estado anterior
private void regresar() {
    for (int i = 0; i < numRows; i++) {
        for (int j = 0; j < numCols; j++) {
            if (!blocked[i][j]) {
                colors[i][j] = Color.WHITE;
                buttons[i][j].setBackground(colors[i][j]);
            }
        }
    }

    // Restablecer el color de inicio y fin
    colors[startRow][startCol] = Color.GREEN;
    buttons[startRow][startCol].setBackground(colors[startRow][startCol]);
    colors[endRow][endCol] = Color.RED;
    buttons[endRow][endCol].setBackground(colors[endRow][endCol]);

    distances = new int[numRows][numCols];
    for (int i = 0; i < numRows; i++) {
        for (int j = 0; j < numCols; j++) {
            distances[i][j] = Integer.MAX_VALUE;
        }
    }
    distances[startRow][startCol] = 0;
}

public static void main(String[] args) {
    BotonesConColores botonesConColores = new BotonesConColores();
}
}