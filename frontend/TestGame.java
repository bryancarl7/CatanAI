package CatanAI.frontend;

import CatanAI.backend.GameEngine;
import processing.core.PApplet;

public class TestGame extends PApplet {
    public static void main(String[] args) {
        GameEngine scenario = new GameEngine();
        scenario.initGame(4);
        scenario.playGame();
    }
}
