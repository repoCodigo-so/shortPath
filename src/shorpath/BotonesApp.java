/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shorpath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class BotonesApp extends JFrame implements ActionListener {
    private JButton[][] botones;  // Arreglo de botones
    private int filas;  // Número de filas en el arreglo de botones
    private int columnas;  // Número de columnas en el arreglo de botones
    private int inicioX;  // Coordenada X del botón de inicio
    private int inicioY;  // Coordenada Y del botón de inicio
    private int finX;  // Coordenada X del botón de fin
    private int finY;  // Coordenada Y del botón de fin
    private boolean[][] bloqueados;  // Arreglo de booleanos para indicar si un botón está bloqueado o no
    private JButton btnAvanzar;  // Botón de avanzar
    private JButton btnRegresar;  // Botón de regresar
    private PriorityQueue<Nodo> colaPrioridad;  // Cola de prioridad para el algoritmo de Dijkstra
    private Set<Nodo> nodosVisitados;  // Conjunto de nodos visitados en el algoritmo de Dijkstra

    // Constructor
    public BotonesApp(int filas, int columnas, int inicioX, int inicioY, int finX, int finY) {
        this.filas = filas;
        this.columnas = columnas;
        this.inicioX = inicioX;
        this.inicioY = inicioY;
        this.finX = finX;
        this.finY = finY;
        this.bloqueados = new boolean[filas][columnas];
        this.botones = new JButton[filas][columnas];
        this.colaPrioridad = new PriorityQueue<>();
        this.nodosVisitados = new HashSet<>();
        inicializarInterfaz();
    }

    // Inicializar la interfaz gráfica
    private void inicializarInterfaz() {
        setTitle("Arreglo de Botones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(filas, columnas));

        // Crear los botones y agregarlos al panel
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                JButton boton = new JButton();
                boton.addActionListener(this);
                add(boton);
                botones[i][j] = boton;
            }
        }

        // Configurar el botón de avanzar
        btnAvanzar = new JButton("Avanzar");
        btnAvanzar.addActionListener(this);
        add(btnAvanzar);

        // Configurar el botón de regresar
        btnRegresar = new JButton("Regresar");
        btnRegresar.addActionListener(this);
        add(btnRegresar);

        pack();
        setVisible(true);
    }

    // Método para bloquear un botón
    private void bloquearBoton(int x, int y) {
        bloqueados[x][y] = true;
        botones[x][y].setEnabled(false);
        botones[x][y].setBackground(Color.RED);
    }
private void desbloquearBoton(int x, int y) {
bloqueados[x][y] = false;
botones[x][y].setEnabled(true);
botones[x][y].setBackground(null);
}
private void encontrarCaminoMasCorto() {
    Nodo inicio = new Nodo(inicioX, inicioY, 0);
    colaPrioridad.offer(inicio);

    while (!colaPrioridad.isEmpty()) {
        Nodo nodoActual = colaPrioridad.poll();
        int x = nodoActual.x;
        int y = nodoActual.y;
        int distancia = nodoActual.distancia;

        if (x == finX && y == finY) {
            // Llegamos al botón de fin
            JOptionPane.showMessageDialog(this, "Camino más corto encontrado!");
            break;
        }

        if (!nodosVisitados.contains(nodoActual)) {
            nodosVisitados.add(nodoActual);

            // Explorar vecinos
            if (x > 0 && !bloqueados[x - 1][y]) {
                colaPrioridad.offer(new Nodo(x - 1, y, distancia + 1, nodoActual));
            }
            if (x < filas - 1 && !bloqueados[x + 1][y]) {
                colaPrioridad.offer(new Nodo(x + 1, y, distancia + 1, nodoActual));
            }
            if (y > 0 && !bloqueados[x][y - 1]) {
                colaPrioridad.offer(new Nodo(x, y - 1, distancia + 1, nodoActual));
            }
            if (y < columnas - 1 && !bloqueados[x][y + 1]) {
                colaPrioridad.offer(new Nodo(x, y + 1, distancia + 1, nodoActual));
            }
        }
    }
}

// Implementación del ActionListener para manejar eventos de botones
@Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() instanceof JButton) {
        JButton boton = (JButton) e.getSource();

        if (boton.equals(btnAvanzar)) {
            encontrarCaminoMasCorto();
        } else if (boton.equals(btnRegresar)) {
            // Regresar al estado anterior
            if (!colaPrioridad.isEmpty()) {
                Nodo nodoAnterior = colaPrioridad.poll().anterior;
                if (nodoAnterior != null) {
                    desbloquearBoton(nodoAnterior.x, nodoAnterior.y);
                    nodosVisitados.remove(nodoAnterior);
                }
            }
        } else {
            // Obtener la posición del botón clickeado
            int x = -1;
            int y = -1;
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    if (boton.equals(botones[i][j])) {
                        x = i;
                        y = j;
                        break;
                    }
                }
            }

            if (x != -1 && y != -1) {
                // Verificar si es el botón de inicio o fin, y bloquearlo si es necesario
                if (x == inicioX && y == inicioY) {
                    JOptionPane.showMessageDialog(this, "Este es el botón de inicio!");
                            botones[x][y].setBackground(Color.green);

                } else if (x == finX && y == finY) {
                    JOptionPane.showMessageDialog(this, "Este es el botón de fin!");
                            botones[x][y].setBackground(Color.orange);

                } else {
                    bloquearBoton(x, y);
}
}
}
}
}
private class Nodo implements Comparable<Nodo> {
    int x;
    int y;
    int distancia;
    Nodo anterior;

    public Nodo(int x, int y, int distancia) {
        this.x = x;
        this.y = y;
        this.distancia = distancia;
        this.anterior = null;
    }

    public Nodo(int x, int y, int distancia, Nodo anterior) {
        this.x = x;
        this.y = y;
        this.distancia = distancia;
        this.anterior = anterior;
    }

    @Override
    public int compareTo(Nodo otro) {
        return Integer.compare(this.distancia, otro.distancia);
    }
}

// Método principal para iniciar la aplicación
public static void main(String[] args) {
        int filas = 5;
        int columnas = 5;
        int inicioX = 0;
        int inicioY = 0;
        int finX = 4;
        int finY = 4;

        BotonesApp botonesApp = new BotonesApp(filas, columnas, inicioX, inicioY, finX, finY);
        botonesApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        botonesApp.pack();
        botonesApp.setVisible(true);
    }
}