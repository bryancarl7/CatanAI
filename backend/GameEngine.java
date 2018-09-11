package CatanAI.backend;

import java.util.*;

import CatanAI.frontend.Hexagon;
import CatanAI.frontend.Point;
import processing.core.PApplet;
import processing.core.PImage;

public class GameEngine extends PApplet {

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
    public static int[] tilesResource = new int[]{0, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5};
    public static int[] tilesNumber = new int[]{2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12};
    public static int[][] vertexDependencies = new int[][]{
            new int[]{0, 1, 8},
            new int[]{1, 0, 2},
            new int[]{2, 1, 3, 10},
            new int[]{3, 2, 4},
            new int[]{4, 3, 5, 12},
            new int[]{5, 4, 6},
            new int[]{6, 5, 14},
            new int[]{7, 8, 17},
            new int[]{8, 0, 7, 9},
            new int[]{9, 8, 10, 19},
            new int[]{10, 2, 9, 11},
            new int[]{11, 10, 12, 21},
            new int[]{12, 4, 11, 13},
            new int[]{13, 12, 14, 23},
            new int[]{14, 6, 13, 15},
            new int[]{15, 14, 25},
            new int[]{16, 17, 29},
            new int[]{17, 7, 16, 18},
            new int[]{18, 17, 29, 29},
            new int[]{19, 9, 18, 20},
            new int[]{20, 19, 21, 31},
            new int[]{21, 11, 20, 22},
            new int[]{22, 21, 23, 33},
            new int[]{23, 13, 22, 24},
            new int[]{24, 23, 25, 35},
            new int[]{25, 15, 24, 26},
            new int[]{26, 25, 37},
            new int[]{27, 16, 28},
            new int[]{28, 27, 29, 38},
            new int[]{29, 18, 28, 30},
            new int[]{30, 29, 31, 40},
            new int[]{31, 20, 30, 32},
            new int[]{32, 31, 33, 42},
            new int[]{33, 22, 32, 34},
            new int[]{34, 33, 35, 44},
            new int[]{35, 24, 34, 36},
            new int[]{36, 35, 37, 46},
            new int[]{37, 26, 36},
            new int[]{38, 28, 39},
            new int[]{39, 38, 40, 47},
            new int[]{40, 30, 39, 41},
            new int[]{41, 40, 42, 49},
            new int[]{42, 32, 41, 43},
            new int[]{43, 42, 44, 51},
            new int[]{44, 34, 43, 45},
            new int[]{45, 44, 46, 53},
            new int[]{46, 36, 45},
            new int[]{47, 39, 48},
            new int[]{48, 47, 49},
            new int[]{49, 41, 48, 50},
            new int[]{50, 49, 51},
            new int[]{51, 43, 50, 52},
            new int[]{52, 51, 53},
            new int[]{53, 45, 52}
    };
    public static int[][] resourceDependencies = new int[][]{
            new int[]{0, 0, 1, 2, 8, 9, 10},
            new int[]{1, 2, 3, 4, 10, 11, 12},
            new int[]{2, 4, 5, 6, 12, 13 , 14},
            new int[]{3, 7, 8, 9, 17, 18, 19},
            new int[]{4, 9, 10, 11, 19, 20, 21},
            new int[]{5, 11, 12, 13, 21, 22, 23},
            new int[]{6, 13, 14, 15, 23, 24, 25},
            new int[]{7, 16, 17, 18, 27, 28, 29},
            new int[]{8, 18, 19, 20, 29, 30, 31},
            new int[]{9, 20, 21, 22, 31, 32, 33},
            new int[]{10, 22, 23, 24, 33, 34, 35},
            new int[]{11, 24, 25, 26, 35, 36, 37},
            new int[]{12, 28, 29, 30, 38, 39, 40},
            new int[]{13, 30, 31, 32, 40, 41, 42},
            new int[]{14, 32, 33, 34, 42, 43, 44},
            new int[]{15, 34, 35, 36, 44, 45, 46},
            new int[]{16, 39, 40, 41, 47, 48, 49},
            new int[]{17, 41, 42, 43, 49, 50, 51},
            new int[]{18, 43, 44, 45, 51, 52, 53},
    };

    static ArrayDeque<Player> playerPool = new ArrayDeque<>();
    static ArrayDeque<VertexNode> vertexPool = new ArrayDeque<>();
    static ArrayDeque<BoardState> boardPool = new ArrayDeque<>();
    static ArrayDeque<MutablePair> pairPool = new ArrayDeque<>();
    static ArrayDeque<ArrayList<Integer>> arrayIntPool = new ArrayDeque<>();
    static ArrayDeque<PriorityQueue<MutablePair>> priorityPool = new ArrayDeque<>();

    static ArrayList<Integer> playerOrder = new ArrayList<>();
    static Random randomGen = new Random();
    public BoardState boardState;

    int roundNumber = 0;

    public void setup(){
        // Create Window
        size(900, 700);
        // Load Images
        //Todo: change filepath to relative
        desertTile = loadImage("/Users/ronak/IdeaProjects/ProcessingCatan/resources/desert.png");
        brickTile = loadImage("/Users/ronak/IdeaProjects/ProcessingCatan/resources/brick.png");
        forestTile = loadImage("/Users/ronak/IdeaProjects/ProcessingCatan/resources/forest.png");
        oreTile = loadImage("/Users/ronak/IdeaProjects/ProcessingCatan/resources/ore.png");
        pastureTile = loadImage("/Users/ronak/IdeaProjects/ProcessingCatan/resources/pasture.png");
        wheatTile = loadImage("/Users/ronak/IdeaProjects/ProcessingCatan/resources/wheat.png");

        initHexagons(); // Create all the different hexagon classes
        BoardState.randomizeArray(tilesResource);
        BoardState.randomizeArray(tilesNumber);
        boardState = getBoard();
        boardState.initBoard();
        // Todo: create method to request player size as input from console
        int playerSize = 4;
        boardState.initPlayers(playerSize);
        for(int i = 0; i < playerSize; i++){
            playerOrder.add(i);
        }
        Collections.shuffle(playerOrder);
    }

    public void initGame(int playerSize){
        BoardState.randomizeArray(tilesResource);
        BoardState.randomizeArray(tilesNumber);
        boardState = getBoard();
        boardState.initBoard();
        boardState.initPlayers(playerSize);
        for(int i = 0; i < playerSize; i++){
            playerOrder.add(i);
        }
        Collections.shuffle(playerOrder);
    }

    // Todo: Complete game functionality
    public void draw(){
        background(255);
        drawTileLabel();
        drawHexagons();
        if(roundNumber == 0){
            settlementPhase();
        }
        if(!getWinner(boardState)){
            playRound();
            roundNumber++;
        }
        this.toString();
    }
    // Method to draw lable on each tile
    private void drawTileLabel(){
        fill(0); // Label color
        textSize(40); // Label size
        textAlign(LEFT, CENTER);
        int tileNumber = 0;
        PImage toDraw = null;
        for(int resource: tilesResource){
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
            int tileDiceNumber = tilesNumber[tileNumber];
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

    private void playRound(){
        int dieRoll = rollDice();
        System.out.println("DieRoll(" + dieRoll + ")");
        boardState.applyDice(dieRoll); // Adds resources to each player's hand
        for(Player player: boardState.playerList){
            player.victoryPoints += 1;
        }
        nextPlayer(boardState);
    }

    private void settlementPhase(){
        int slot;
        for(Integer playerID : playerOrder){
            slot = boardState.startSettlement(playerID);
            drawSettlement(playerID.intValue(), slot);
        }
        for(int i = playerOrder.size() - 1; i >= 0; i--){
            slot = boardState.startSettlement(playerOrder.get(i));
            drawSettlement(playerOrder.get(i).intValue(), slot);
        }
    }


    public void playGame(){
        System.out.println("Settlement Phase Begin");
        settlementPhase();
        System.out.println("Settlement Phase End");
        while(!getWinner(boardState)){
           playRound();
        }
        System.out.println(toString());
    }

    static public void nextPlayer(BoardState boardState){
        if(boardState.playerTurn == playerOrder.size() - 1){
            boardState.playerTurn = 0;
        } else {
            boardState.playerTurn += 1;
        }
    }

    static public boolean getWinner(BoardState boardState){
        return boardState.playerList.get(boardState.playerTurn).victoryPoints >= 10;
    }

    @Override
    public String toString() {
        return "{\"backend.GameEngine\" : {" +
                "\"tilesResource\" : " + Arrays.toString(tilesResource) +
                ", \"tilesNumber\" : " + Arrays.toString(tilesNumber) +
                ", \"playerOrder\" : " + Arrays.toString(playerOrder.toArray()) +
                ", \"boardState\" : " + boardState +
                "}}";
    }

    static public Player getPlayer(){
        if(!playerPool.isEmpty()){
            return playerPool.removeLast();
        } else {
            return new Player();
        }
    }

    static public VertexNode getVertex(){
        if(!vertexPool.isEmpty()){
            return vertexPool.removeLast();
        } else {
            return new VertexNode();
        }
    }

    static public BoardState getBoard(){
        if(!boardPool.isEmpty()){
            return boardPool.removeLast();
        } else {
            return new BoardState();
        }
    }

    static public MutablePair getPair(){
        if(!pairPool.isEmpty()){
            return pairPool.removeLast();
        } else {
            return new MutablePair();
        }
    }

    static public ArrayList<Integer> getIntArray(){
        if(!arrayIntPool.isEmpty()){
            return arrayIntPool.removeLast();
        } else {
            return new ArrayList<>();
        }
    }

    static public PriorityQueue<MutablePair> getPriority(){
        if(!priorityPool.isEmpty()){
            return priorityPool.removeLast();
        } else {
            return new PriorityQueue<>();
        }
    }

    static public void resetPriority(PriorityQueue<MutablePair> queue){
        queue.remove().reset();
        priorityPool.add(queue);
    }

    static public int rollDice(){
        return randomGen.nextInt(6) + randomGen.nextInt(6) + 2;
    }

    static public int getRarity(int resourceNumber){
        if(resourceNumber <= 7){
            return resourceNumber - 1;
        } else {
            return 13 - resourceNumber;
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

    private void drawHexagons() {
        for (Hexagon tile : tiles) {
            tile.display();
        }
    }

    private void drawSettlement(int playerNumber, int slot){
        Point location = findPoint(slot);
        switch(playerNumber){
            case 0: fill(255, 0, 0); // Red
                    break;
            case 1: fill(0, 0, 255); // Blue
                    break;
            case 2: fill(244, 149, 66); // Orange
                    break;
            case 3: fill(0, 0, 0); // Black
                    break;
        }
        int size = 20;
        rect(location.getX() - (size/2), location.getY() - size/2, size, size);
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


    //TODO----------------------------------------------------------------------

}
