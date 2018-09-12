package CatanAI.frontend;

import CatanAI.backend.GameEngine;
import CatanAI.backend.VertexNode;
import processing.core.PApplet;
import processing.core.PImage;

public class TestGame extends PApplet {
    PImage brickTile;
    PImage desertTile;
    PImage oreTile;
    PImage wheatTile;
    PImage pastureTile;
    PImage forestTile;
    private static int TILE_RADIUS = 80;
    private static int INITIAL_TILE_X = 280;
    private static int INITIAL_TILE_Y = 120;
    private Hexagon[] tiles = new Hexagon[19];
    GameEngine scenario = new GameEngine();

    public void setup(){
        // Create Window
        size(900, 700);
        // Load Images
        desertTile = loadImage("CatanAI/resources/desert.png");
        brickTile = loadImage("CatanAI/resources/brick.png");
        forestTile = loadImage("CatanAI/resources/forest.png");
        oreTile = loadImage("CatanAI/resources/ore.png");
        pastureTile = loadImage("CatanAI/resources/pasture.png");
        wheatTile = loadImage("CatanAI/resources/wheat.png");

        initHexagons();
        scenario.initGame(4);
        scenario.playGame();
    }

    // Todo: Complete game functionality
    public void draw(){
        background(255);
        drawTileLabel();
        drawHexagons();
        drawSettlements();
        updateRoads();
    }
    // Method to draw lable on each tile
    private void drawTileLabel(){
        fill(0); // Label color
        textSize(40); // Label size
        textAlign(LEFT, CENTER);
        int tileNumber = 0;
        PImage toDraw = null;
        for(int resource: GameEngine.tilesResource){
            String toPrint = "";
            switch(resource){
                case 0: toPrint = "desert";
                    toDraw = desertTile;
                    break;
                case 1: toPrint = "wood";
                    toDraw = forestTile;
                    break;
                case 2: toPrint = "brick";
                    toDraw = brickTile;
                    break;
                case 3: toPrint = "sheep";
                    toDraw = pastureTile;
                    break;
                case 4: toPrint = "hay";
                    toDraw = wheatTile;
                    break;
                case 5: toPrint = "rock";
                    toDraw = oreTile;
                    break;
            }
            int xPos = tiles[tileNumber].getCenterX();
            int yPos = tiles[tileNumber].getCenterY();
            image(toDraw, xPos - TILE_RADIUS, yPos - TILE_RADIUS, TILE_RADIUS*2, TILE_RADIUS*2);
            int tileDiceNumber = GameEngine.tilesNumber[tileNumber];
            toPrint = Integer.toString(tileDiceNumber);
            if(tileDiceNumber == 6 || tileDiceNumber == 8){
                fill(255, 0, 0);
            }
            else{
                fill(0);
            }
            text(toPrint, xPos - 10, yPos);
            tileNumber++;
        }
    }

    private void initHexagons(){
        // Top 3 tiles
        tiles[0] = new Hexagon(INITIAL_TILE_X, INITIAL_TILE_Y, this);
        assignSlots(0, 0, 1, 2, 10, 9, 8);
        tiles[1] = new Hexagon(tiles[0].getCenterX() + (TILE_RADIUS*2), tiles[0].getCenterY(), this);
        assignSlots(1, 2, 3, 4, 12, 11, 10);
        tiles[2] = new Hexagon(tiles[1].getCenterX() + (TILE_RADIUS*2), tiles[1].getCenterY(), this);
        assignSlots(2, 4, 5, 6, 14, 13, 12);

        // Second Row 4 tiles
        tiles[3] = new Hexagon(tiles[0].getCenterX() - TILE_RADIUS, tiles[0].getCenterY() + TILE_RADIUS + (TILE_RADIUS/2), this);
        assignSlots(3, 7, 8, 9, 19, 18, 17);
        tiles[4] = new Hexagon(tiles[3].getCenterX() + TILE_RADIUS*2, tiles[3].getCenterY(), this);
        assignSlots(4, 9, 10, 11, 21, 20, 19);
        tiles[5] = new Hexagon(tiles[4].getCenterX() + TILE_RADIUS*2, tiles[4].getCenterY(), this);
        assignSlots(5, 11, 12, 13, 23, 22, 21);
        tiles[6] = new Hexagon(tiles[5].getCenterX() + TILE_RADIUS*2, tiles[5].getCenterY(), this);
        assignSlots(6, 13, 14, 15, 25, 24, 23);

        // Third Row 5 tiles
        tiles[7] = new Hexagon(tiles[3].getCenterX() - TILE_RADIUS, tiles[3].getCenterY() + TILE_RADIUS + (TILE_RADIUS/2), this);
        assignSlots(7, 16, 17, 18, 29, 28, 27);
        tiles[8] = new Hexagon(tiles[7].getCenterX() + TILE_RADIUS*2, tiles[7].getCenterY(), this);
        assignSlots(8, 18, 19, 20, 31, 30, 29);
        tiles[9] = new Hexagon(tiles[8].getCenterX() + TILE_RADIUS*2, tiles[8].getCenterY(), this);
        assignSlots(9, 20, 21, 22, 33, 32, 31);
        tiles[10] = new Hexagon(tiles[9].getCenterX() + TILE_RADIUS*2, tiles[9].getCenterY(), this);
        assignSlots(10, 22, 23, 24, 35, 34, 33);
        tiles[11] = new Hexagon(tiles[10].getCenterX() + TILE_RADIUS*2, tiles[10].getCenterY(), this);
        assignSlots(11, 24, 25, 26, 37, 36, 35);

        // Fourth Row 4 tiles
        tiles[12] = new Hexagon(tiles[3].getCenterX(), tiles[7].getCenterY() + TILE_RADIUS + (TILE_RADIUS/2), this);
        assignSlots(12, 28, 29, 30, 40, 39, 38);
        tiles[13] = new Hexagon(tiles[12].getCenterX() + TILE_RADIUS*2, tiles[12].getCenterY(), this);
        assignSlots(13, 30, 31, 32, 42, 41, 40);
        tiles[14] = new Hexagon(tiles[13].getCenterX() + TILE_RADIUS*2, tiles[13].getCenterY(), this);
        assignSlots(14, 32, 33, 34, 44, 43, 42);
        tiles[15] = new Hexagon(tiles[14].getCenterX() + TILE_RADIUS*2, tiles[14].getCenterY(), this);
        assignSlots(15, 34, 35, 36, 46, 45, 44);

        // Fifth Row 3 tiles
        tiles[16] = new Hexagon(tiles[0].getCenterX(), tiles[12].getCenterY() + TILE_RADIUS + (TILE_RADIUS/2), this);
        assignSlots(16, 39, 40, 41, 49, 48, 47);
        tiles[17] = new Hexagon(tiles[16].getCenterX() + TILE_RADIUS*2, tiles[16].getCenterY(), this);
        assignSlots(17, 41, 42, 43, 51, 50, 49);
        tiles[18] = new Hexagon(tiles[17].getCenterX() + TILE_RADIUS*2, tiles[17].getCenterY(), this);
        assignSlots(18, 43, 44, 45, 53, 52, 51);
    }

    private void assignSlots(int tileNumber, int slot1, int slot2, int slot3, int slot4, int slot5, int slot6){
        Point[] temp;
        // Tile #1
        temp = tiles[tileNumber].getPoints();
        temp[0].setSlot(slot1);
        temp[1].setSlot(slot2);
        temp[2].setSlot(slot3);
        temp[3].setSlot(slot4);
        temp[4].setSlot(slot5);
        temp[5].setSlot(slot6);
    }

    // Method goes through each vertex's edges and changes color of edges where there is a road.
    // Method does not draw the edges/road. drawHexagons() draws the edges/roads.
    private void updateRoads(){
        for(int i = 0; i <scenario.boardState.vertexes.length; i++){ // i is also the Node #
            VertexNode node = scenario.boardState.vertexes[i];
            for(CatanAI.backend.MutablePair pair : node.listEdges){
                if(pair.getFirst() != -1){ // There is a road on edge
                    updateRoadColor(pair.getFirst(), i, pair.getSecond());
                }
            }
        }
    }

    private void drawHexagons() {
        for (Hexagon tile : tiles) {
            tile.display();
        }
    }

    // Method loops over all vertexes and draws a settlement with the player's color if it exists
    private void drawSettlements() {
        int size = 20;
        for(int i = 0 ; i < scenario.boardState.vertexes.length; i++){
            VertexNode node = scenario.boardState.vertexes[i];
            if(node.city.getSecond() > 0) {
                Point location = findPoint(i);
                switch (node.city.getFirst()) {
                    case 0:
                        fill(255, 0, 0); // Red
                        break;
                    case 1:
                        fill(0, 0, 255); // Blue
                        break;
                    case 2:
                        fill(244, 149, 66); // Orange
                        break;
                    case 3:
                        fill(34, 139, 34); // Green
                        break;
                }
                rect(location.getX() - (size / 2), location.getY() - size / 2, size, size);
                fill(0);
            }
        }
    }

    private Point findPoint(int slot){
        for(Hexagon tile: tiles){
            for(int i = 0; i < 6; i++){ // 6 points for each frontend.Hexagon
                Point temp = tile.getPoints()[i];
                if(temp.getSlot() == slot){
                    return temp;
                }
            }
        }
        return null;
    }
    // Todo: Fix Bug **Red roads not displaying**
    private void updateRoadColor(int playerNumber, int slot1, int slot2){
        // May not be the most efficient, but not that many to look through (loops through ~114 times)
        for(Hexagon tile: tiles){
            for(Line line: tile.getEdges()){
                int vertex1 = line.getP1().getSlot();
                int vertex2 = line.getP2().getSlot();
                if(((vertex1 == slot1) && (vertex2 == slot2)) || ((vertex1 == slot2) && (vertex2 == slot1))){
                    line.setColor(playerNumber); // The integer playerNumber represents their color
                }
            }
        }
    }


    public Hexagon[] getTiles(){
        return tiles;
    }
}
