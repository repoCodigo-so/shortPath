/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package shorpath;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 *
 * @author User
 */
public class Djkstra {
    private JFrame frame;
    private JPanel panel;
    private MyButton[][] buttons;
    private int numRows;
    private int numCols;
    private int cuenta;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int current;
    List<List<Posicion>> rutas = new ArrayList<>();
    

    public Djkstra() {
        frame = new JFrame("Short Path");
        panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
    
    
    public void generateButtons(int numRows, int numCols) {
        cuenta = 1;
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
                        if (cuenta == 3) {
                            int confirm = JOptionPane.showConfirmDialog(frame,
                                    "¿Desea bloquear este botón y establecer su peso como infinito?", "Bloquear botón",
                                    JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                btn.setBlock(true);
                                btn.setBackground(Color.blue);
                            }
                            else{
                                cuenta = 0;
                            }
                        }
                        else if(cuenta == 1) {
                            int confirm = JOptionPane.showConfirmDialog(frame, "¿Desea que esta sea el inicio?", "Inicio",
                                    JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                startX = row;
                                startY = col;
                                btn.setText("Inicio ");
                                btn.setValor(0);
                                btn.setBackground(Color.magenta);
                                btn.setStart(true);
                                btn.setExplored(true);
                                cuenta = 2;
                            }
                        }
                        else if (cuenta == 2) {
                            int confirm = JOptionPane.showConfirmDialog(frame, "¿Desea que esta sea la meta?", "Meta",
                                    JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                btn.setGoal(true);
                                btn.setText("Meta");
                                btn.setBackground(Color.orange);
                                endX = row;
                                endY = col;
                                cuenta = 3;
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
                calcular(startX,startY,endX,endY,buttons);
                /*for (int i = 0; i < numRows; i++) {
                    for (int j = 0; j < numCols; j++) {
                        MyButton btn = buttons[i][j];
                        if (btn.isBlock()) {
                            btn.setBackground(Color.blue);
                        }else if (btn.isStart()) {
                            btn.setBackground(Color.magenta);
                        } else if (btn.isGoal()) {
                            btn.setBackground(Color.orange);
                            if (cuenta == 0){
                                JOptionPane.showMessageDialog(null, "LLego a la meta.", "Alerta", JOptionPane.WARNING_MESSAGE);
                            }
                        } else if (btn.isRoad()) {
                            if(current == btn.getValor()){
                                btn.setBackground(Color.cyan);
                            }
                        } else if (btn.isExplored()) {
                            if(current == btn.getValor()){
                                btn.setBackground(Color.green);
                            }
                        } else {
                            btn.setBackground(panel.getBackground());
                        }
                    }
                }
                current++;*/
            }
        });

        btnRetroceder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Botón Retroceder presionado.");
                for (int i = 0; i < numRows; i++) {
                    for (int j = 0; j < numCols; j++) {
                        MyButton btn = buttons[i][j];
                        if (btn.isBlock()) {
                            btn.setBackground(Color.blue);
                        } else if (btn.isStart()) {
                            btn.setBackground(Color.magenta);
                            if (cuenta == 0){
                                JOptionPane.showMessageDialog(null, "LLego al inicio.", "Alerta", JOptionPane.WARNING_MESSAGE);
                            }
                        } else if (btn.isGoal()) {
                            btn.setBackground(Color.orange);
                        } else if (btn.isRoad()) {
                            if(current == btn.getValor()){
                                btn.setBackground(Color.cyan);
                            }
                        } else if (btn.isExplored()) {
                            if(current == btn.getValor()){
                                btn.setBackground(Color.green);
                            }
                        } else {
                            btn.setBackground(panel.getBackground());
                        }
                    }
                }
                current--;
            }
        });
        
        frame.add(btnAvanzar, BorderLayout.WEST);
        frame.add(btnRetroceder, BorderLayout.EAST);
        frame.pack();
}
    public void calcular(int startX, int startY, int endX, int endY, MyButton[][] buttons) {
    
    
    // Crear una lista de nodos sin explorar, que se inicializará con el nodo de inicio
    List<MyButton> unexploredNodes = new ArrayList<>();
    MyButton startNode = buttons[startX][startY];
    startNode.setValor(0);
    startNode.setExplored(true);
    unexploredNodes.add(startNode);
    
    while (!unexploredNodes.isEmpty()) {
        // Buscar el nodo no explorado con el peso más pequeño
        MyButton currentNode = unexploredNodes.get(0);
        int smallestWeight = currentNode.getValor();
        for (int i = 1; i < unexploredNodes.size(); i++) {
            MyButton nextNode = unexploredNodes.get(i);
            if (nextNode.getValor() < smallestWeight) {
                currentNode = nextNode;
                smallestWeight = currentNode.getValor();
            }
        }
        
        // Si el nodo con el peso más pequeño es el nodo de destino, detener la búsqueda
        if (currentNode.getRow() == endX && currentNode.getCol() == endY) {
            break;
        }
        
        // Explorar el nodo actual y actualizar el peso de sus vecinos si es necesario
        unexploredNodes.remove(currentNode);
        int currentX = currentNode.getRow();
        int currentY = currentNode.getCol();
        if (currentX < numRows - 1) {
            MyButton nextNode = buttons[currentX + 1][currentY];
            int weight = currentNode.getValor() + Math.abs(currentX + 1 - startX) + Math.abs(currentY - startY);
            if (!nextNode.isBlock() && weight < nextNode.getValor()) {
                nextNode.setValor(weight);
                nextNode.setExplored(true);
                unexploredNodes.add(nextNode);
            }
        }
        if (currentX > 0) {
            MyButton nextNode = buttons[currentX - 1][currentY];
            int weight = currentNode.getValor() + Math.abs(currentX - 1 - startX) + Math.abs(currentY - startY);
            if (!nextNode.isBlock() && weight < nextNode.getValor()) {
                nextNode.setValor(weight);
                nextNode.setExplored(true);
                unexploredNodes.add(nextNode);
            }
        }
        if (currentY > 0) {
MyButton nextNode = buttons[currentX][currentY - 1];
int weight = currentNode.getValor() + Math.abs(currentX - startX) + Math.abs(currentY - 1 - startY);
if (!nextNode.isBlock() && weight < nextNode.getValor()) {
nextNode.setValor(weight);
nextNode.setExplored(true);
unexploredNodes.add(nextNode);
}
}
if (currentY < numCols - 1) {
MyButton nextNode = buttons[currentX][currentY + 1];
int weight = currentNode.getValor() + Math.abs(currentX - startX) + Math.abs(currentY + 1 - startY);
if (!nextNode.isBlock() && weight < nextNode.getValor()) {
nextNode.setValor(weight);
nextNode.setExplored(true);
unexploredNodes.add(nextNode);
}
}
}
    List<MyButton> path = new ArrayList<>();
MyButton endNode = buttons[endX][endY];
path.add(endNode);
MyButton currentNode = endNode;
while (currentNode.getRow() != startX || currentNode.getCol() != startY) {
    int currentX = currentNode.getRow();
    int currentY = currentNode.getCol();
    MyButton parent = null;
    int smallestWeight = Integer.MAX_VALUE;
    if (currentX < numRows - 1) {
        MyButton nextNode = buttons[currentX + 1][currentY];
        if (nextNode.isExplored() && nextNode.getValor() < smallestWeight) {
            parent = nextNode;
            smallestWeight = nextNode.getValor();
        }
    }
    if (currentX > 0) {
        MyButton nextNode = buttons[currentX - 1][currentY];
        if (nextNode.isExplored() && nextNode.getValor() < smallestWeight) {
            parent = nextNode;
            smallestWeight = nextNode.getValor();
        }
    }
    if (currentY > 0) {
        MyButton nextNode = buttons[currentX][currentY - 1];
        if (nextNode.isExplored() && nextNode.getValor() < smallestWeight) {
            parent = nextNode;
            smallestWeight = nextNode.getValor();
        }
    }
    if (currentY < numCols - 1) {
        MyButton nextNode = buttons[currentX][currentY + 1];
        if (nextNode.isExplored() && nextNode.getValor() < smallestWeight) {
            parent = nextNode;
            smallestWeight = nextNode.getValor();
        }
    }
    path.add(parent);
    currentNode = parent;
}

// Invertir la ruta para obtener la dirección correcta y resaltarla en la interfaz de usuario
Collections.reverse(path);
for (MyButton node : path) {
    node.setBackground(Color.YELLOW);
}
}
                


    private class MyButton extends JButton {
        private int valor;
        private int row;
        private int col;
        private boolean goal;
        private boolean start;
        private boolean explored;
        private boolean road;
        private boolean block;

        public MyButton(int row, int col) {
            this.row = row;
            this.col = col;
            this.valor = Integer.MAX_VALUE;
            this.goal = false;
            this.start = false;
            this.explored = false;
            this.block = false;
            this.road = false;
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
        
        public boolean isStart() {
            return start;
        }

        public void setStart(boolean start) {
            this.start = start;
        }
        
        public boolean isExplored() {
            return explored;
        }

        public void setExplored(boolean explored) {
            this.explored = explored;
        }

        public boolean isBlock() {
            return block;
        }

        public void setBlock(boolean block) {
            this.block = block;
        }
        
        public boolean isRoad() {
            return road;
        }

        public void setRoad(boolean road) {
            this.road = road;
        }
    }
    
    public class Posicion {
        private int x;
        private int y;

        public Posicion(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
    
    public static void main(String[] args) {
        Djkstra shortPath = new Djkstra();
        int numRows = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de filas: "));
        int numCols = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número de columnas: "));
        shortPath.generateButtons(numRows, numCols);
    }
}