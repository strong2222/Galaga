
package Main;

import Stage.Stage;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private List<Stage> stages;
    private int currentStageIndex;

    public GameManager() {
        this.stages = new ArrayList<>();
        this.currentStageIndex = 0;
    }

    public void loadStages() {
        // Load stages from files or create them programmatically
    }

    public void startGame() {
        // Initialize the first stage and start the game loop
    }

    public void update() {
        
        if (stages.get(currentStageIndex).isCompleted()) {
            
        }
    }

    public void draw(Graphics2D g2d) {
        
        stages.get(currentStageIndex).draw(g2d);
    }
}


