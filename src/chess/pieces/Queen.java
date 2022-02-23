package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Queen extends ChessPiece {
    public Queen(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString(){
        return "Q";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] assist = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        //up
        p.setPosition(position.getX() - 1, position.getY());
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            assist[p.getX()][p.getY()] = true;
            p.setX(p.getX()-1);
        }
        if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
            assist[p.getX()][p.getY()] = true;
        }

        //down
        p.setPosition(position.getX() + 1, position.getY());
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            assist[p.getX()][p.getY()] = true;
            p.setX(p.getX()+1);
        }
        if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
            assist[p.getX()][p.getY()] = true;
        }

        //right
        p.setPosition(position.getX(), position.getY()+1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            assist[p.getX()][p.getY()] = true;
            p.setY(p.getY()+1);
        }
        if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
            assist[p.getX()][p.getY()] = true;
        }

        //left
        p.setPosition(position.getX(), position.getY()-1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            assist[p.getX()][p.getY()] = true;
            p.setY(p.getY()-1);
        }
        if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
            assist[p.getX()][p.getY()] = true;
        }

        // up+left
        p.setPosition(position.getX() - 1, position.getY() - 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            assist[p.getX()][p.getY()] = true;
            p.setPosition(p.getX() - 1, p.getY() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            assist[p.getX()][p.getY()] = true;
        }

        // up+right
        p.setPosition(position.getX() - 1, position.getY() + 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            assist[p.getX()][p.getY()] = true;
            p.setPosition(p.getX() - 1, p.getY() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            assist[p.getX()][p.getY()] = true;
        }

        // down+right
        p.setPosition(position.getX() + 1, position.getY() + 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            assist[p.getX()][p.getY()] = true;
            p.setPosition(p.getX() + 1, p.getY() + 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            assist[p.getX()][p.getY()] = true;
        }

        // down+left
        p.setPosition(position.getX() + 1, position.getY() - 1);
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            assist[p.getX()][p.getY()] = true;
            p.setPosition(p.getX() + 1, p.getY() - 1);
        }
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            assist[p.getX()][p.getY()] = true;
        }

        return assist;
    }


}
