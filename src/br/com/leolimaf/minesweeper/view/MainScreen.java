package br.com.leolimaf.minesweeper.view;

import br.com.leolimaf.minesweeper.model.Board;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {

    public MainScreen() throws HeadlessException {

        Board board = new Board(17,28,50);

        add(new BoardPanel(board));

        setTitle("Minesweeper");
        setSize(850,548);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

    }

    public static void main(String[] args) {

        new MainScreen();

    }

}
