package boardgame;

public abstract class Piece {

    protected Position position;
    private Board board;

    public Piece(Board board) {
        this.board = board;
    }

    protected Board getBoard() {
        return board;
    }

    public abstract boolean[][] possibleMoves();

    /*Metodo que será responsável por dizer se é possivel que a peça seja ou não movida a dada posição*/
    public boolean possibleMoves(Position position) { //Metodo concreto que está utilizando um metodo abstrato
        return possibleMoves()[position.getRow()][position.getColumn()];
    }

    /*Metodo que irá retornar se existe algum movimento possivel para a peça*/
    public boolean isThereAnyPossibleMove() {
        boolean[][] mat = possibleMoves(); //Variavel auxiliar para percorrer a matriz
        for (int row = 0; row < mat.length; row++) {
            for (int column = 0; column < mat.length; column++) {
                if (mat[row][column]) {
                    return true;
                }
            }
        }
        return false;
    }

}
