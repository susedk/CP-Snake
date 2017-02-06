package com.company;

/**
 * Created by dalsasus on 29-01-2017.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Window extends JFrame {
    public static ArrayList<ArrayList<DataOfSquare>> Grid;
    public static int width=20;
    public static int height=20;

    public static JLabel scoreLabel;
    public static JLabel highScoreLabel;


    public String GetHighScore() {
        FileReader readFile = null;
        BufferedReader reader = null;
        try {
            readFile = new FileReader("highscore.dat");
            reader = new BufferedReader(readFile);
            return reader.readLine();
                    }
        catch (Exception e)
        {
            return "0";
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Window() {
        Grid = new ArrayList<ArrayList<DataOfSquare>>();
        ArrayList<DataOfSquare> data;

        for (int i = 0; i < width; i++) {
            data = new ArrayList<DataOfSquare>();
            for (int j = 0; j < height; j++) {
                DataOfSquare c = new DataOfSquare(2);
                data.add(c);
            }
            Grid.add(data);
        }

        // Panel med tern til spillet
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(width, height, 0, 0));

        // Placer hvr tern i vinduet
        for (int i = 0; i < width; i++)             {
            for (int j = 0; j < height; j++) {
                gamePanel.add(Grid.get(i).get(j).square);
            }
        }

        // Panel til labels med score
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new BorderLayout(5, 5));
        scoreLabel = new JLabel("Score: 0");
        highScoreLabel = new JLabel("High Score: " + GetHighScore());
        ThreadController.highScore = GetHighScore();
        scorePanel.add(scoreLabel, BorderLayout.WEST);
        scorePanel.add(highScoreLabel, BorderLayout.EAST);
        getContentPane().add(gamePanel, BorderLayout.CENTER);
        getContentPane().add(scorePanel, BorderLayout.SOUTH);

        Tuple position = new Tuple(10, 10);
        ThreadController c = new ThreadController(position);
        c.start();

        this.addKeyListener((KeyListener) new KeyboardListener());
    }


}
