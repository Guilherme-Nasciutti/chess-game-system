package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

/*Classe que será a responsável por conter as regras do jogo.*/
public class ChessMatch {

    private Board board;

    public ChessMatch() {
        board = new Board(8, 8);
        initialSetup();
    }

    /*Metodo que irá retornar uma matriz das peças de xadrez, correspondentes a partida.*/
    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for (int l = 0; l < board.getRows(); l++) {
            for (int c = 0; c < board.getColumns(); c++) {
                mat[l][c] = (ChessPiece) board.piece(l, c);
            }
        }
        return mat;
    }

    /*Metodo responsável por iniciar a partida de xadrez, colocando as peças no tabuleiro*/
    private void initialSetup() {
        board.placePiece(new Rook(board, Color.WHITE), new Position(0, 0));
        board.placePiece(new King(board, Color.WHITE), new Position(0, 4));
        board.placePiece(new King(board, Color.BLACK), new Position(7, 4));

    }
}
