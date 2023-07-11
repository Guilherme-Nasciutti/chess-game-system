package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

    private Color color;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    /*Metodo que ira converter uma posição da peça em posição de xadrez*/
    public ChessPosition getChessPosition() {
        return ChessPosition.fromPosition(position);
    }

    /*Função para descobrir se a peça é adversária na posição desejada*/
    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p != null && p.getColor() != color;
    }
}
