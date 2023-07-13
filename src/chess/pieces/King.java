package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    private ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "K";
    }

    /*Metodo que determina se a peça pode ou não se mover para determinada posição*/
    private boolean canMove(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p == null || p.getColor() != getColor();
    }

    /*Metodo que será responsável por testar se é possivel fazer um Roque*/
    private boolean testRookCastling(Position position) {
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p != null && p instanceof Rook && p.getColor() == getColor() && getMoveCount() == 0;
    }

    /*Metodo dos possiveis movimentos da peça*/
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

//        acima
        p.setValues(position.getRow() - 1, position.getColumn());
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

//        abaixo
        p.setValues(position.getRow() + 1, position.getColumn());
        if ((getBoard().positionExists(p) && canMove(p))) {
            mat[p.getRow()][p.getColumn()] = true;
        }

//        esquerda
        p.setValues(position.getRow(), position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

//        direita
        p.setValues(position.getRow(), position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

//        diagonal esquerda acima
        p.setValues(position.getRow() - 1, position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

//        diagonal esquerda abaixo
        p.setValues(position.getRow() + 1, position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

//        diagonal direita acima
        p.setValues(position.getRow() - 1, position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

//        diagonal direita abaixo
        p.setValues(position.getRow() + 1, position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

//        #Movimento especial - Roque
        if (getMoveCount() == 0 && !chessMatch.getCheck()) {
//            #Roque Curto
            Position positionCastlingKingSide = new Position(position.getRow(), position.getColumn() + 3);
            if (testRookCastling(positionCastlingKingSide)) {
                Position rightToTheKing1 = new Position(position.getRow(), position.getColumn() + 1);
                Position rightToTheKing2 = new Position(position.getRow(), position.getColumn() + 2);
                if (getBoard().piece(rightToTheKing1) == null && getBoard().piece(rightToTheKing2) == null) {
                    mat[position.getRow()][position.getColumn() + 2] = true;
                }
            }
//            #Roque Grande
            Position positionCastlingQueenSide = new Position(position.getRow(), position.getColumn() - 4);
            if (testRookCastling(positionCastlingQueenSide)) {
                Position leftToTheKing1 = new Position(position.getRow(), position.getColumn() - 1);
                Position leftToTheKing2 = new Position(position.getRow(), position.getColumn() - 2);
                Position leftToTheKing3 = new Position(position.getRow(), position.getColumn() - 3);
                if (getBoard().piece(leftToTheKing1) == null && getBoard().piece(leftToTheKing2) == null && getBoard().piece(leftToTheKing3) == null) {
                    mat[position.getRow()][position.getColumn() - 2] = true;
                }
            }
        }
        return mat;
    }
}
