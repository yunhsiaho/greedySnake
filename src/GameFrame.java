import javax.swing.*;

public class GameFrame extends JFrame {
    ImageIcon imgSnake = new ImageIcon("src/snake.png");
    GameFrame(){
        this.add(new GamePanel());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Greedy Snake");
        this.setVisible(true);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setIconImage(imgSnake.getImage());//SPECIALS
        //pack() is to size the frame so all its components are at its preferable size or above
        /*
         In general, using pack is preferable to calling setSize,
         since pack leaves the frame layout manager in charge of the frame size,
         and layout managers are good at adjusting to platform dependencies and other
         factors that affect component size.
         */
        this.pack();
    }
}
