package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

/*Classe que será a responsável por conter as regras do jogo.*/
public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private Board board;

    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
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

    /*Metodo usado para avaliar quais os possiveis movimentos de cada peça, e sendo utilizada para que possa ser colorido o background com as possiveis jogadas*/
    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition(); //converte uma posição de xadrez em uma posição de matriz.
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source); //Operação responsavel por validar a posição de origem
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        nextTurn();
        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position source, Position target) {
        Piece p = board.removePiece(source); //Variavel para retirar a peça da posição de origem
        Piece capturedPiece = board.removePiece(target); //Variavel para remover a possivel peça que esteja na posição de destino, se tornando por padrao a peça capturada
        board.placePiece(p, target);
        return capturedPiece;
    }

    /*Metodo para a validação da posição de origem*/
    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("There is no piece on source position!");
        }
        if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()){
            throw new ChessException("The chosen piece is not your!");
        }
        if (!board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("There is no possible moves for the chosen piece");
        }
    }

    /*Metodo para validar se é possivel mover a peça para determinada posição*/
    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMoves(target)) {
            throw new ChessException("The chosen piece can't move to target position!");
        }
    }

    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
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
