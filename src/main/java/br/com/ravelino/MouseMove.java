package br.com.ravelino;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MouseMove {
    
    private static final int MOUSE_MOVE_TIME = 15000;

    public static void main(String[] args) {
        try {
            layout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    private static void layout() {
        var frame = new JFrame("Mouse Mover");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        var firstPanel = new JPanel();
        firstPanel.setLayout(null);
        firstPanel.add(getButtonStart());
        
        frame.add(firstPanel);
        frame.setSize(150, 100);
        frame.setVisible(true);
    }
    
    private static JButton getButtonStart() {
        
        var button = new JButton("Start");
        button.setLayout(null);
        button.setBounds(10, 10, 120, 30);
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
    
    private static Runnable getRunnable() {
        return () -> { 
            try {
                while (true) {
                    TimeUnit.MILLISECONDS.sleep(MOUSE_MOVE_TIME);
                    mouseMove();
                }
            } catch (Exception e) {
                System.out.println("Thread removida, parando runnable");
            }    
        };
    }
    
    private static void mouseMove() throws AWTException {
        var size = Toolkit.getDefaultToolkit().getScreenSize();
        var width = (int) size.getWidth();
        var height = (int) size.getHeight();

        var randX = (int) (Math.random() * width) + 1;
        var randY = (int) (Math.random() * height) + 1;

        var robot = new Robot();
        System.out.println("Movendo mouse X: " + randX + ", Y:" + randY);
        robot.mouseMove(randX, randY);
    }

}
