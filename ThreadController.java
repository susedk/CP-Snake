package com.company;

/**
 * Created by dalsasus on 29-01-2017.
 */
import javax.imageio.IIOException;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.awt.Graphics;

import static com.company.Window.*;
import static com.company.Window.scoreLabel;

public class ThreadController extends Thread {
    Tuple headSnakePos;
    int sizeSnake=3;
    long speed = 100;
    public static int directionSnake;
    Tuple foodPosition;
    public static int score = 0;  //score
    public static String highScore = "nobody:0"; //score


    ArrayList<Tuple> positions = new ArrayList<Tuple>();


        public ThreadController(Tuple positionDepart) {
        headSnakePos = new Tuple(positionDepart.x, positionDepart.y);
        directionSnake = 1;

        Tuple headPos = new Tuple(headSnakePos.getX(), headSnakePos.getY());
        positions.add(headPos);

        foodPosition = new Tuple(height-1, width-1);
        spawnFood(foodPosition);
    }


    public void run() {
        while (true) {
            moveInterne(directionSnake);
            checkCollision();
            moveExterne();
            deleteTail();
            pauser();

        }
    }

    private void pauser() {
        try {
            sleep(speed);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
    }

    private void checkCollision() {
            Tuple posCritique =positions.get(positions.size()-1);
            for (int i=0; i<=positions.size()-2;i++) {
                boolean biteItself = posCritique.getX()==positions.get(i).getX() && posCritique.getY()==positions.get(i).getY();
                if (biteItself) {
                    stopTheGame();
                }
            }

            boolean eatingFood = posCritique.getX()==foodPosition.y && posCritique.getY()==foodPosition.x;
            if(eatingFood) {
                System.out.println("Yummy!");
                sizeSnake = sizeSnake+1;
                score = score+100; //score
                setScore();         //score
                foodPosition=getValAleaNotInSnake();

                spawnFood(foodPosition);
            }
    }

    private void stopTheGame() {
            System.out.println("Collision! \n");
            checkScore(); //score
            while (true) {
                pauser();
            }
    }
    private  void spawnFood(Tuple foodPositionIn) {
        Grid.get(foodPosition.x).get(foodPosition.y).lightMeUp(1);
    }



    public static void setScore() {
        scoreLabel.setText(String.format("Score: %d", ThreadController.score));    }

    public void checkScore() { //score

            if (score > Integer.parseInt((highScore.split(":")[1]))) {
                String name = JOptionPane.showInputDialog("Ny highscore! Skriv dit navn: ");
                highScore = name + ":" + score;

                File scoreFile = new File("highscore.dat");
                if (!scoreFile.exists()) {
                    try {
                        scoreFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                FileWriter writeFile = null;
                BufferedWriter writer = null;
                try {
                    writeFile = new FileWriter(scoreFile);
                    writer = new BufferedWriter(writeFile);
                    writer.write(this.highScore);
                    highScoreLabel.setText(String.format("NY High Score: %s", this.highScore));

                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    try {
                        if (writer != null)
                            writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    }

    private Tuple getValAleaNotInSnake() {
        Tuple p;
        int ranX=0 + (int)(Math.random()*(width-1));
        int ranY=0 + (int)(Math.random()*(height-1));
        p = new Tuple(ranX,ranY);
        for (int i = 0;i<=positions.size()-1;i++) {
            if (p.getY()==positions.get(i).getX() && p.getX()==positions.get(i).getY()) {
                ranX=0 + (int)(Math.random()*(width-1));
                ranY=0 + (int)(Math.random()*(height-1));
                p=new Tuple(ranX,ranY);
                i=0;
            }
        }
        return p;
    }

    private void moveInterne(int dir) {
        switch(dir) {
            case 4:
                headSnakePos.ChangeData(headSnakePos.x, (headSnakePos.y+1)% height);
                positions.add(new Tuple(headSnakePos.x,headSnakePos.y));
                break;
            case 3:
                if(headSnakePos.y-1<0) {
                    headSnakePos.ChangeData(headSnakePos.x, (height-1));
                }
                else {
                    headSnakePos.ChangeData(headSnakePos.x,Math.abs(headSnakePos.y-1)% height);
                }
                positions.add(new Tuple(headSnakePos.x,headSnakePos.y));
                break;
            case 2:
                if(headSnakePos.x-1<0) {
                    headSnakePos.ChangeData((width-1),headSnakePos.y);
                }
                else {
                    headSnakePos.ChangeData(Math.abs(headSnakePos.x-1)% width,headSnakePos.y);
                }
                positions.add(new Tuple(headSnakePos.x,headSnakePos.y));
                break;
            case 1:
                headSnakePos.ChangeData(Math.abs(headSnakePos.x+1)% width,headSnakePos.y);
                positions.add(new Tuple(headSnakePos.x,headSnakePos.y));
                break;
        }
    }
    private void moveExterne() {
        for(Tuple t : positions) {
            int y = t.getX();
            int x = t.getY();
            Grid.get(x).get(y).lightMeUp(0);
        }
    }

    private void deleteTail() {
        int cmpt = sizeSnake;
        for(int i = positions.size()-1;i>=0;i--) {
            if (cmpt==0) {
                Tuple t = positions.get(i);
                Grid.get(t.y).get(t.x).lightMeUp(2);
            }
            else {
                cmpt--;
            }
        }
        cmpt = sizeSnake;
        for(int i = positions.size()-1;i>=0;i--) {
            if(cmpt==0) {
                positions.remove(i);
            }
            else {
                cmpt--;
            }
        }
    }
}