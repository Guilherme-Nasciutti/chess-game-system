package boardgame;

public class Board {

    private int rows;
    private int columns;
    private Piece[][] pieces;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    /*Metodo que irá retornar a peça, dado uma linha e uma coluna*/
    public Piece piece(int row, int column){
        return pieces[row][column];
    }

    /*Metodo que irá retornar retornar a peça atraves da posição*/
    public Piece piece(Position position){
        return pieces[position.getRow()] [position.getColumn()];
    }
}
