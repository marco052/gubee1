package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {
    public Knight(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString(){
        return "N";
    }

    private boolean canMove(Position position){
        ChessPiece p = (ChessPiece) getBoard().getPiece(position);
        return  p == null || p.getColor() != getColor();
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] assist = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        p.setPosition(position.getX() -1, position.getY()-2);
        if(getBoard().positionExists(p) && canMove(p)){
            assist[p.getX()][p.getY()] = true;
        }

        p.setPosition(position.getX() -1, position.getY()+2);
        if(getBoard().positionExists(p) && canMove(p)){
            assist[p.getX()][p.getY()] = true;
        }

        p.setPosition(position.getX()+1, position.getY()-2);
        if(getBoard().positionExists(p) && canMove(p)){
            assist[p.getX()][p.getY()] = true;
        }

        p.setPosition(position.getX()+1, position.getY()+2);
        if(getBoard().positionExists(p) && canMove(p)){
            assist[p.getX()][p.getY()] = true;
        }

        p.setPosition(position.getX() -2, position.getY()-1);
        if(getBoard().positionExists(p) && canMove(p)){
            assist[p.getX()][p.getY()] = true;
        }

        p.setPosition(position.getX() -2, position.getY()+1);
        if(getBoard().positionExists(p) && canMove(p)){
            assist[p.getX()][p.getY()] = true;
        }

        p.setPosition(position.getX() +2, position.getY()-1);
        if(getBoard().positionExists(p) && canMove(p)){
            assist[p.getX()][p.getY()] = true;
        }

        p.setPosition(position.getX() +2, position.getY()+1);
        if(getBoard().positionExists(p) && canMove(p)){
            assist[p.getX()][p.getY()] = true;
        }

        return assist;
    }
}
