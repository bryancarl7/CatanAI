package backend;

import java.util.ArrayList;

public class VertexNode {
    /*
     * first = recourse type
     *      0 = Desert
     *      1 = Wood
     *      2 = Brick
     *      3 = Sheep
     *      4 = Hay
     *      5 = Rock
     * second = probability number
     */
    public ArrayList<MutablePair> resources = new ArrayList<>();

    /*
     * first = Trade Amount
     * second = Recourse. 0 is Any
     */
    public MutablePair port = new MutablePair();

    /*
     * first = road status
     *      0 = no road
     *      1 = backend.Player 1 road
     *      2 = backend.Player 2 road
     *      ...
     * second = backend.VertexNode number
     */
    public ArrayList<MutablePair> listEdges = new ArrayList<>();

    /*
     * first = player
     *      1 = backend.Player 1 city
     *      2 = backend.Player 2 city
     *      ...
     * second = strength
     *      1 = Settlement
     *      2 = City
     */
    MutablePair city = new MutablePair();

    public VertexNode(){}

    public boolean canBuildCity(BoardState boardState){
        if(city.getSecond() > 0){
            return false;
        }
        for(MutablePair pair : listEdges){
            if(boardState.vertexes[pair.getSecond()].city.getSecond() > 0){
                return false;
            }
        }
        return  true;
    }

    @Override
    public String toString() {
        return "{ " +
                "\"resources\" : "  + resources +
                ", \"port\" : " + port +
                ", \"listEdges\" : " + listEdges +
                ", \"city\" : " + city +
                "}";
    }

    public void reset(){
        port.reset();
        city.reset();
        for(MutablePair currentPair : resources){
            currentPair.reset();
        }
        for(MutablePair currentPair : listEdges){
            currentPair.reset();
        }
        resources.clear();
        listEdges.clear();
        GameEngine.vertexPool.add(this);
    }

    public VertexNode clone(VertexNode blankNode){
        port.clone(blankNode.port);
        city.clone(blankNode.city);
        for(MutablePair pair : resources){
            blankNode.resources.add(pair.clone(GameEngine.getPair()));
        }
        for(MutablePair pair : listEdges){
            blankNode.listEdges.add(pair.clone(GameEngine.getPair()));
        }
        return blankNode;
    }

    //TODO----------------------------------------------------------------------
}
