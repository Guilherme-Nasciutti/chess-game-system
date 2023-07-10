package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "K";
    }

    /*Metodo provisório sem a implementação dos possiveis movimentos da peça, ainda retornando false em todas as casas do tabuleiro*/
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat =  new boolean[getBoard().getRows()][getBoard().getColumns()];
        return mat;
    }
}
