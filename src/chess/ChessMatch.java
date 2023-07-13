package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*Classe que será a responsável por conter as regras do jogo.*/
public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private Board board;
    private boolean check;
    private boolean checkMate;

    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

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

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckMate() {
        return checkMate;
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

    /*Metodo que executa a jogada de xadrez*/
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source); //Operação responsavel por validar a posição de origem
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);

//        Testando para saber se o jogador se auto colocou em check
        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check!");
        }

//        Testando se o oponente está em check
        check = (testCheck(opponent(currentPlayer))) ? true : false;

//        Testando se houve o checkMate e o jogo será encerrado
        if (testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }
        return (ChessPiece) capturedPiece;
    }

    /*Metodo responsável por realizar o movimento da peça*/
    private Piece makeMove(Position source, Position target) {
        ChessPiece p = (ChessPiece) board.removePiece(source); //Variavel para retirar a peça da posição de origem
        p.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target); //Variavel para remover a possivel peça que esteja na posição de destino, se tornando por padrao a peça capturada
        board.placePiece(p, target);
        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
//        #Movimento especial - Roque Curto
        if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position positionSourceRook = new Position(source.getRow(), source.getColumn() + 3);
            Position positionTargetRook = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(positionSourceRook);
            board.placePiece(rook, positionTargetRook);
            rook.increaseMoveCount();
        }
//            #Movimento especial - Roque Grande
        if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position positionSourceRook = new Position(source.getRow(), source.getColumn() - 4);
            Position positionTargetRook = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(positionSourceRook);
            board.placePiece(rook, positionTargetRook);
            rook.increaseMoveCount();
        }
        return capturedPiece;
    }

    /*Metodo responsável por desfazer um movimento da peça*/
    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece p = (ChessPiece) board.removePiece(target);
        p.decreaseMoveCount();
        board.placePiece(p, source);

        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }
//        #Movimento especial - Roque Curto
        if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position positionSourceRook = new Position(source.getRow(), source.getColumn() + 3);
            Position positionTargetRook = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(positionTargetRook);
            board.placePiece(rook, positionSourceRook);
            rook.decreaseMoveCount();
        }
//        #Movimento especial - Roque Grande
        if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position positionSourceRook = new Position(source.getRow(), source.getColumn() - 4);
            Position positionTargetRook = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(positionTargetRook);
            board.placePiece(rook, positionSourceRook);
            rook.decreaseMoveCount();
        }
    }

    /*Metodo para a validação da posição de origem*/
    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("There is no piece on source position!");
        }
        if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
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

    /*Metodo que realiza a troca de turnos*/
    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    /*Metodo para indentificar uma peça adversária*/
    private Color opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    /*Metodo para identificar o Rei de uma determinada cor.*/
    private ChessPiece king(Color color) {
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).collect(Collectors.toList());
        for (Piece p : list) {
            if (p instanceof King) {
                return (ChessPiece) p;
            }
        }
        throw new IllegalStateException("There is no " + color + "king on the board!"); //Essa exceção não vai ser tratada pois não deverá ocorrer de forma alugma na execução do programa principal.
    }

    /*Metodo responsável por testar se  o Rei estará ou não em check*/
    private boolean testCheck(Color color) {
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList()); //lista para mapear as peças oponentes.
        for (Piece p : opponentPieces) {
            boolean[][] mat = p.possibleMoves(); //lista de possiveis movimentos da peça adversária
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testCheckMate(Color color) {
        if (!testCheck(color)) {
            return false;
        }
//        Testando se alguma peça da cor do argumento não tem um movimento possivel para tirar do check
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == (color)).collect(Collectors.toList());
        for (Piece p : list) {
            boolean[][] mat = p.possibleMoves(); //pegando todos os movimentos possiveis da peça
            for (int row = 0; row < board.getRows(); row++) {
                for (int column = 0; column < board.getColumns(); column++) {
                    if (mat[row][column]) { //testando se o movimento possivel é capaz de tirar o rei do check
                        Position source = ((ChessPiece) p).getChessPosition().toPosition(); //converte a peça para peça de xadrez,pegando a posição de xadrez e convertendo para a posição da matriz
                        Position target = new Position(row, column); //a posição de destino é a posição da matriz com o movimento possivel
                        Piece capturedPiece = makeMove(source, target); //pega a peça e faz o movimento
                        boolean testCheck = testCheck(color); //testa se o rei da cor passada ainda está em check
                        undoMove(source, target, capturedPiece); //depois de testar desfaz o movimento para não atrapalhar o jogo e a posição do tabuleiro
                        if (!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /*Metodo que irá receber as coordenadas do xadrez*/
    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    /*Metodo responsável por iniciar a partida de xadrez, colocando as peças no tabuleiro*/
    private void initialSetup() {
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
    }
}
