package br.com.ravelino;

public class Application {
    
    public static void main(String[] args) {
        try {
            var mouseMove = new MouseMove();
            mouseMove.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
