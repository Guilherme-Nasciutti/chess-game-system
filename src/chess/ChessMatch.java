package chess;

import boardgame.Board;
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

    /*Metodo que irá receber as coordenadas do xadrez*/
    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }

    /*Metodo responsável por iniciar a partida de xadrez, colocando as peças no tabuleiro*/
    private void initialSetup() {
        placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
    }
}
