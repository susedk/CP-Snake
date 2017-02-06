package com.company;

/**
 * Created by dalsasus on 29-01-2017.
 */
import java.awt.Color;
import javax.swing.JPanel;

public class SquarePanel extends JPanel {

    public SquarePanel(Color d) {
        this.setBackground(d);
    }

    public void ChangeColor(Color d) {
        this.setBackground(d);
        this.repaint();
    }
}
