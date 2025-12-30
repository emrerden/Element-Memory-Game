
package element.memory.game;




public class Element {
    private String name;
    private String symbol;
    private String fact;

    public Element(String name, String symbol, String fact) {
        this.name = name;
        this.symbol = symbol;
        this.fact = fact;
    }

    public String getName() { return name; }
    public String getSymbol() { return symbol; }
    public String getFact() { return fact; }
}