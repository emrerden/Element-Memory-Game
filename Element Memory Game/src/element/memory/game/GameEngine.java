
package element.memory.game;

import java.util.ArrayList;

public class GameEngine {
    private int moves = 0;
    private int pairsFound = 0;
    private ArrayList<Element> elements;

    public GameEngine(ArrayList<Element> elements) {
        this.elements = elements;
    }

    public boolean isMatch(String val1, String val2) {
        moves++;
        for (Element e : elements) {
            if ((val1.equals(e.getName()) && val2.equals(e.getSymbol())) ||
                (val1.equals(e.getSymbol()) && val2.equals(e.getName()))) {
                pairsFound++;
                return true;
            }
        }
        return false;
    }

    public int getMoves() { return moves; }
    public int getPairsFound() { return pairsFound; }
    public int getTotalPairs() { return elements.size(); }
}