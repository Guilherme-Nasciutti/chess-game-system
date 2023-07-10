package application;

import chess.ChessMatch;
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

    /*Metodo para limpar a tela apos cada jogada*/
    // https://stackoverflow.com/questions/2979383/java-clear-the-console
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

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

    /*Metodo para mostrar na tela os dados da partida*/
    public static void printMatch(ChessMatch chessMatch) {
        printBoard(chessMatch.getPieces());
        System.out.println();
        System.out.println("\nTurn: " + chessMatch.getTurn());
        System.out.println("Waiting player " + chessMatch.getCurrentPlayer());
    }

    /*Metodo que imprimi na tela o tabuleiro de xadrez*/
    public static void printBoard(ChessPiece[][] pieces) {
        for (int row = 0; row < pieces.length; row++) {  //Uso do pieces.length considerando que a matriz será quadrada.
            System.out.print((8 - row) + " ");
            for (int column = 0; column < pieces.length; column++) {
                printPiece(pieces[row][column], false);
            }
            System.out.println();
        }
        System.out.print("  a b c d e f g h");
    }

    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        for (int row = 0; row < pieces.length; row++) {
            System.out.print((8 - row) + " ");
            for (int column = 0; column < pieces.length; column++) {
                printPiece(pieces[row][column], possibleMoves[row][column]);
            }
            System.out.println();
        }
        System.out.print("  a b c d e f g h");
    }


    /*Metodo auxiliar para imprimir uma peça*/
    private static void printPiece(ChessPiece piece, boolean background) {
        if (background) {
            System.out.print(ANSI_BLUE_BACKGROUND);
        }
        if (piece == null) {
            System.out.print("-" + ANSI_RESET);
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
