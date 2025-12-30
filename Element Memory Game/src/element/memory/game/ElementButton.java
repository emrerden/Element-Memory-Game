
package element.memory.game;

import javax.swing.JButton;
import java.awt.Font;

public class ElementButton extends JButton {
    private String value;

    public ElementButton(String value) {
        super(value);
        this.value = value;
        this.setFont(new Font("Arial", Font.BOLD, 14));
        this.setFocusable(false);
    }

    public String getValue() { return value; }
}