package br.com.leolimaf.minesweeper.view;

import br.com.leolimaf.minesweeper.model.Board;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {

    public MainScreen() throws HeadlessException {

        Board board = new Board(16,30,50);

        add(new BoardPanel(board));

        setTitle("Minesweeper");
        setSize(690,448);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

    }

    public static void main(String[] args) {

        new MainScreen();

    }

}
