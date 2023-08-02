package br.com.ravelino;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MouseMove {
    
    private static final int SECONDS_MOUSE_MOVE = 60;
    
    private JLabel jLabel = new JLabel();
    private JLabel jLabelDate = new JLabel();
    
    public void run() {
        layout();
    }
    
    
    
    
    private void layout() {
        var frame = new JFrame("Mouse Move");
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        var firstPanel = new JPanel();
        firstPanel.setLayout(null);
        
        
        jLabel.setBounds(15, 40, 260, 30);
        jLabelDate.setBounds(76, 60, 260, 30);
        jLabel.setBackground(Color.BLUE);

        firstPanel.add(jLabel);
        firstPanel.add(jLabelDate);
        firstPanel.add(getButtonStart());
        frame.add(firstPanel);
        frame.setSize(280, 140);
        frame.setVisible(true);
    }
    
    private JButton getButtonStart() {
        
        var button = new JButton("Start");
        button.setLayout(null);
        button.setBounds(72, 10, 120, 30);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLUE);
        button.setFocusPainted(false);
        button.addActionListener(new ActionListener() {

            Thread thread = null;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = getRunnable();
                if (thread == null) {
                    System.out.println("Criando THREAD");
                    thread = new Thread(runnable);
                }
                
                if (button.getText().equals("Start")) { 
                    button.setText("Stop");
                    button.setBackground(Color.GREEN);
                    thread.start();
                } else {
                    thread.interrupt();
                    thread = null;
                    button.setBackground(Color.BLUE);
                    button.setText("Start");
                }
            }
        });
        
        return button;
    }
    
    private Runnable getRunnable() {
        return () -> { 
            try {
                while (true) {
                    TimeUnit.SECONDS.sleep(SECONDS_MOUSE_MOVE);
                    mouseMove();
                }
            } catch (Exception e) {
                System.out.println("Thread removida, parando runnable");
            }    
        };
    }
    
    private void mouseMove() throws AWTException {
        var size = Toolkit.getDefaultToolkit().getScreenSize();
        var width = (int) size.getWidth();
        var height = (int) size.getHeight();

        var randX = (int) (Math.random() * width) + 1;
        var randY = (int) (Math.random() * height) + 1;

        var robot = new Robot();
        System.out.println("Movendo mouse X: " + randX + ", Y:" + randY);
        jLabel.setText("Ultima posição EixoX = " + randX + ", EixoY = " + randY);
        jLabelDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")   ));
        robot.mouseMove(randX, randY);
    }

}
