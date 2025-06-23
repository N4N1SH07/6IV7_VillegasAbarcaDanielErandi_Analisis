/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rsa;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class Menu extends JFrame {
    
    private JButton btnCifrar;
    private JButton btnDescifrar;
    
    public Menu() {
        setTitle("Menú");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Márgenes
        
        JLabel titulo = new JLabel("Menú");
        titulo.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titulo, BorderLayout.NORTH);
        
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        
        btnCifrar = new JButton("Cifrar");
        botonesPanel.add(btnCifrar);

        btnDescifrar = new JButton("Descifrar");
        botonesPanel.add(btnDescifrar);
        
        panel.add(botonesPanel, BorderLayout.CENTER);
        
        add(panel);
        llamarVentanas();
        setVisible(true);
    }
    
    private void llamarVentanas(){
        btnCifrar.addActionListener(e -> ventCifrar());
        btnDescifrar.addActionListener(e -> ventDescifrar());
    }
    
    private void ventCifrar(){
        this.setVisible(false);
        new Cifrar();
    }
    
    private void ventDescifrar(){
        this.setVisible(false);
        new Descifrar();
    }
}