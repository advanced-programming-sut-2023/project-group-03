package controller.gameControllers;

import Model.Field.GameMap;
import Model.Field.Tile;
import Model.Units.Unit;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;

public class MoveUnitController {
//    GameController gameController;
//
//    MoveUnitController(GameController gameController) {
//        this.gameController = gameController;
//    }

    private static class PathTile {
        private final Tile tile;
        private final PathTile parentTile;

        public PathTile(Tile tile, PathTile parentTile) {
            this.tile = tile;
            this.parentTile = parentTile;
        }

        public Tile getTile() {
            return tile;
        }

        public PathTile getParentTile() {
            return parentTile;
        }
    }

    public static ArrayList<Tile> findPath(Tile startTile, Tile endTile, GameMap gameMap) {
        int mapSize = gameMap.getSize();
        boolean[][] visitedTiles = new boolean[mapSize][mapSize];
        LinkedList<PathTile> queueTiles = new LinkedList<>();
        PathTile currentPathTile = new PathTile(endTile, null);
        queueTiles.addLast(currentPathTile);
        ArrayList<Tile> path = new ArrayList<>();
        path.add(startTile);

        for (int i = 0; i < mapSize; i++) for (int j = 0; j < mapSize; j++) visitedTiles[i][j] = false;
        visitedTiles[endTile.getRowNum()][endTile.getColumnNum()] = true;

        search:
        while (true) {
            for (Tile neigbour : currentPathTile.getTile().getNeighboursConnected()) {
                if (!neigbour.equals(startTile) && !visitedTiles[neigbour.getRowNum()][neigbour.getColumnNum()]) {
                    queueTiles.addLast(new PathTile(neigbour, currentPathTile));
                } else if (neigbour.equals(startTile)) {
                    currentPathTile = new PathTile(neigbour, currentPathTile);
                    break search;
                }
            }
            if (queueTiles.size() == 0) return path;
            currentPathTile = queueTiles.getFirst();
            queueTiles.removeFirst();
        }

        while (currentPathTile.getParentTile() != null) {
            currentPathTile = currentPathTile.getParentTile();
            path.add(currentPathTile.getTile());
        }

        return path;
    }

    public static ArrayList<Tile> manhattanCloseTiles(int distance, Tile tile, GameMap gameMap) {
        int mapSize = gameMap.getSize();
        boolean[][] visitedTiles = new boolean[mapSize][mapSize];
        LinkedList<PathTile> queueTiles = new LinkedList<>();
        PathTile currentPathTile = new PathTile(tile, null);
        queueTiles.addLast(currentPathTile);

        for (int i = 0; i < mapSize; i++) for (int j = 0; j < mapSize; j++) visitedTiles[i][j] = false;
        visitedTiles[tile.getRowNum()][tile.getColumnNum()] = true;
        int currentDistances = 1;
        ArrayList<Tile> answer = new ArrayList<>();
        answer.add(tile);

        while (currentDistances < distance) {
            for (Tile neigbour : currentPathTile.getTile().getNeighboursConnected()) {
                if (!visitedTiles[neigbour.getRowNum()][neigbour.getColumnNum()]) {
                    queueTiles.addLast(new PathTile(neigbour, currentPathTile));
                    answer.add(neigbour);
                }
            }
            if (queueTiles.size() == 0) break;
            currentPathTile = queueTiles.getFirst();
            queueTiles.removeFirst();
            currentDistances++;
        }
        return answer;
    }



    public static ArrayList<Tile> closeTilesForAttack(int distance, Tile tile, GameMap gameMap) {
        int mapSize = gameMap.getSize();
        boolean[][] visitedTiles = new boolean[mapSize][mapSize];
        LinkedList<Tile> queueTiles = new LinkedList<>();
        Tile currentTile = tile;
        queueTiles.addLast(tile);

        for (int i = 0; i < mapSize; i++) for (int j = 0; j < mapSize; j++) visitedTiles[i][j] = false;
        visitedTiles[tile.getRowNum()][tile.getColumnNum()] = true;
        int currentDistances = 1;
        ArrayList<Tile> answer = new ArrayList<>();
        answer.add(tile);

        while (currentDistances < distance) {
            int xCurrent = currentTile.getRowNum();
            int yCurrent = currentTile.getColumnNum();

            if (xCurrent - 1 >= 0 && !visitedTiles[xCurrent - 1][yCurrent]) {
                queueTiles.addLast(gameMap.getMap()[xCurrent - 1][yCurrent]);
                answer.add(gameMap.getMap()[xCurrent - 1][yCurrent]);
            }
            if (xCurrent + 1 < gameMap.getSize() && !visitedTiles[xCurrent + 1][yCurrent]) {
                queueTiles.addLast(gameMap.getMap()[xCurrent + 1][yCurrent]);
                answer.add(gameMap.getMap()[xCurrent + 1][yCurrent]);
            }
            if (yCurrent - 1 >= 0 && !visitedTiles[xCurrent][yCurrent - 1]) {
                queueTiles.addLast(gameMap.getMap()[xCurrent][yCurrent - 1]);
                answer.add(gameMap.getMap()[xCurrent][yCurrent - 1]);
            }
            if (xCurrent + 1 < gameMap.getSize() && !visitedTiles[xCurrent][yCurrent + 1]) {
                queueTiles.addLast(gameMap.getMap()[xCurrent][yCurrent + 1]);
                answer.add(gameMap.getMap()[xCurrent][yCurrent + 1]);
            }
            
            if (queueTiles.size() == 0) break;
            currentTile = queueTiles.getFirst();
            queueTiles.removeFirst();
            currentDistances++;
        }
        return answer;
    }
    
    public boolean checkIfImpossibleDestination(int x, int y) {
        return false;
    }

    public void moveUnit(Matcher matcher) {

    }

    public String patrolUnit(Matcher matcher) {
        //move between x1,y1 and x2,y2
        //check if the positions are valid
        return "";
    }

    public String disbandUnit(Matcher matcher) {
        return "";
    }
}
