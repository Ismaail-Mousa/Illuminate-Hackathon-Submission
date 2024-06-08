import javax.swing.*;

public class Main {
    public static void Main(String[] args) throws Exception {
        int gameWidth = 1000;
        int gameHeight = gameWidth;
        JFrame frame = new JFrame("Adventures of Integrals!");
        frame.setVisible(true);
        frame.setSize(gameHeight, gameWidth);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game adventure = new Game(gameWidth, gameHeight);
        frame.add(adventure);
        frame.pack();
        adventure.requestFocus();

    }
}