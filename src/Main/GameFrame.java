package Main;





import javax.swing.JFrame;

public class GameFrame extends JFrame{
    GamePanel GamePanel = new GamePanel();
    public GameFrame() {
        
        this.setResizable(false);
        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(GamePanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        GamePanel.gameThreadStart();
    }
   
    
}
