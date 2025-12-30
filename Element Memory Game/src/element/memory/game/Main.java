
package element.memory.game;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Element> list = new ArrayList<>();
        // 12 Farklı Element ve Bilgileri
        list.add(new Element("Hydrogen", "H", "It is the most abundant element in the universe."));
        list.add(new Element("Helium", "He", "The liquid state is the coldest known substance."));
        list.add(new Element("Lithium", "Li", "It is the lightest metal and is used in batteries."));
        list.add(new Element("Beryllium", "Be", "It is found in the structure of emeralds and rubies."));
        list.add(new Element("Boron", "B", "It is used in the production of heat-resistant glass."));
        list.add(new Element("Carbon", "C", "It is the basic component of diamonds and coal."));
        list.add(new Element("Nitrogen", "N", "It makes up 78% of the atmosphere."));
        list.add(new Element("Oxygen", "O", "It is essential for living things to breathe."));
        list.add(new Element("Fluorine", "F", "It is the most reactive nonmetal and is found in toothpaste."));
        list.add(new Element("Neon", "Ne", "It is used in the bright lights of advertising signs."));
        list.add(new Element("Sodium", "Na", "It is a basic component of table salt."));
        list.add(new Element("Magnesium", "Mg", "It is a light metal found in the structure of chlorophyll."));

        new MenuWindow(list);
    }
}