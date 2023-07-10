package application;

import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {

    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    /*Metodo utilizado para ler a peça do xadrez*/
    public static ChessPosition readChessPosition(Scanner sc) {
        try {
            String s = sc.nextLine(); // variavel auxiliar para usar a string que representa a jogada
            char column = s.charAt(0); //variavel para usar o caractere que representa a coluna
            int row = Integer.parseInt(s.substring(1)); //variavel para transformar em int o numero da string em linha do tabuleiro
            return new ChessPosition(column, row);
        } catch (RuntimeException error) {
            throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8!");
        }
    }

    public static void printBoard(ChessPiece[][] pieces) {
        for (int l = 0; l < pieces.length; l++) {  //Uso do pieces.length considerando que a matriz será quadrada.
            System.out.print((8 - l) + " ");
            for (int c = 0; c < pieces.length; c++) {
                printPiece(pieces[l][c]);
            }
            System.out.println();
        }
        System.out.print("  a b c d e f g h");
    }

    /*Metodo auxiliar para imprimir uma peça*/
    private static void printPiece(ChessPiece piece) {
        if (piece == null) {
            System.out.print("-");
        } else {
            if (piece.getColor() == Color.WHITE) {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            } else {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }
}
