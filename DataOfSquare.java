package com.company;

/**
 * Created by dalsasus on 29-01-2017.
 */

import java.util.ArrayList;
import java.awt.Color;

public class DataOfSquare {
    ArrayList<Color> C =new ArrayList<Color>();

    SquarePanel square;
    public DataOfSquare(int c) {

        C.add(Color.black);
        C.add(Color.blue);
        C.add(Color.white);
        square = new SquarePanel(C.get(c));
    }

    public void lightMeUp(int c) {
        square.ChangeColor(C.get(c));
    }
}
