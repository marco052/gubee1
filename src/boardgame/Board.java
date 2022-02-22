package boardgame;

public class Board {
    private int rows;
    private int columns;
    private Piece[][] pieces;

    public Board(int rows, int columns) {
        if(rows < 1 || columns < 1){
            throw new BoardException("Error: the board could not be created because the rows or columns are insufficient.");
        }
        this.rows = rows;
        this.columns = columns;
        this.pieces = new Piece[rows][columns];
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

    public Piece getPiece (int row, int column){
        return pieces[row][column];
    }

    public Piece getPiece (Position position){
        return pieces[position.getX()][position.getY()];
    }

    public void placePiece(Piece piece, Position position){
        pieces[position.getX()][position.getY()] = piece;
        piece.position = position;
    }

    public Piece removePiece(Position position){
        if(!positionExists(position)){
            throw new BoardException("Position isn't on the board");
        }
        if(getPiece(position) == null){

            return null;
        }
        Piece assist = getPiece(position);
        assist.position = null;
        pieces[position.getX()][position.getY()] = null;
        return assist;
    }

    private boolean positionExists (int row, int column){
        return column >= 0 && column < columns && row >= 0 && row < rows;
    }

    public boolean positionExists (Position position){
        return positionExists(position.getX(), position.getY());
    }

    public boolean thereIsAPiece(Position position){

        return getPiece(position) != null;
    }


}
