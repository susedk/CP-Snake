package com.company;

/**
 * Created by dalsasus on 29-01-2017.
 */

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardListener extends KeyAdapter {

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 39:
                if (ThreadController.directionSnake !=2)
                    ThreadController.directionSnake = 1;
                break;
            case 38:
                if (ThreadController.directionSnake !=4)
                    ThreadController.directionSnake = 3;
                break;

            case 37:
                if (ThreadController.directionSnake !=1)
                    ThreadController.directionSnake = 2;
                break;
            case 40:
                if (ThreadController.directionSnake !=3)
                    ThreadController.directionSnake = 4;
                break;

            default: break;
        }
    }
}
