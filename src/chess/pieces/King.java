package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString(){
        return "K";
    }

    private boolean canMove(Position position){
        ChessPiece p = (ChessPiece) getBoard().getPiece(position);
        return  p == null || p.getColor() != getColor();
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] assist = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        //up
        p.setPosition(position.getX() -1, position.getY());
        if(getBoard().positionExists(p) && canMove(p)){
            assist[p.getX()][p.getY()] = true;
        }

        //down
        p.setPosition(position.getX() +1, position.getY());
        if(getBoard().positionExists(p) && canMove(p)){
            assist[p.getX()][p.getY()] = true;
        }

        //left
        p.setPosition(position.getX(), position.getY()-1);
        if(getBoard().positionExists(p) && canMove(p)){
            assist[p.getX()][p.getY()] = true;
        }

        //right
        p.setPosition(position.getX(), position.getY()+1);
        if(getBoard().positionExists(p) && canMove(p)){
            assist[p.getX()][p.getY()] = true;
        }

        //up+left
        p.setPosition(position.getX() -1, position.getY()-1);
        if(getBoard().positionExists(p) && canMove(p)){
            assist[p.getX()][p.getY()] = true;
        }

        //up+right
        p.setPosition(position.getX() -1, position.getY()+1);
        if(getBoard().positionExists(p) && canMove(p)){
            assist[p.getX()][p.getY()] = true;
        }

        //down+left
        p.setPosition(position.getX() +1, position.getY()-1);
        if(getBoard().positionExists(p) && canMove(p)){
            assist[p.getX()][p.getY()] = true;
        }

        //down+right
        p.setPosition(position.getX() +1, position.getY()+1);
        if(getBoard().positionExists(p) && canMove(p)){
            assist[p.getX()][p.getY()] = true;
        }

        return assist;
    }
}
