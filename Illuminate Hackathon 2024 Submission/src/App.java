import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int gameWidth = 1000;
        int gameHeight = 600;
        JFrame frame = new JFrame("Integral Run");
        frame.setVisible(true);
        frame.setSize(gameHeight, gameWidth);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game adventure = new Game(gameWidth, gameHeight);
        frame.add(adventure);
        frame.pack();
        adventure.requestFocus();

    }
}