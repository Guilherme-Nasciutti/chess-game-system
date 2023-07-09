package boardgame;

public class Board {

    private int rows;
    private int columns;
    private Piece[][] pieces;

    public Board(int rows, int columns) {
        if (rows < 1 || columns < 1) {
            throw new BoardException("Error creating board: There must be at least 1 row and 1 column!");
        }
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    /*Metodo que irá retornar a peça, dado uma linha e uma coluna*/
    public Piece piece(int row, int column) {
        if (!positionExists(row, column)) {
            throw new BoardException("Position does not exist on the board!");
        }
        return pieces[row][column];
    }

    /*Metodo que irá retornar retornar a peça atraves da posição*/
    public Piece piece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position does not exist on the board!");
        }
        return pieces[position.getRow()][position.getColumn()];
    }

    /*Metodo responsável por atribuir a peça a posição informada*/
    public void placePiece(Piece piece, Position position) {
        if (thereIsAPiece(position)) {
            throw new BoardException("There is already a part in that position " + position);
        }
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    /*Metodo para testar se uma posição existe no tabuleiro dado uma linha e uma coluna*/
    private boolean positionExists(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    /*Função para testar se uma posição existe no tabuleiro*/
    public boolean positionExists(Position position) {
        return positionExists(position.getRow(), position.getColumn());
    }

    /*Metodo usado para testar se tem uma peça em certa posição*/
    public boolean thereIsAPiece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position does not exist on the board");
        }
        return piece(position) != null;
    }

}
