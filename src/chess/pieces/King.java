package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    private ChessMatch match;
    public King(Board board, Color color, ChessMatch match) {
        super(board, color);
        this.match = match;
    }

    @Override
    public String toString(){
        return "K";
    }

    private boolean canMove(Position position){
        ChessPiece p = (ChessPiece) getBoard().getPiece(position);
        return  p == null || p.getColor() != getColor();
    }

    private boolean testRookCastling(Position position){
        ChessPiece p = (ChessPiece)getBoard().getPiece(position);
        if(p != null && p instanceof  Rook && p.getColor() == getColor() && p.getMoveCount() == 0){
            return true;
        }
        return false;
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

        // #specialmove castling
        if (getMoveCount() == 0 && !match.getCheck()) {
            // #specialmove castling kingside rook
            Position posT1 = new Position(position.getX(), position.getY() + 3);
            if (testRookCastling(posT1)) {
                Position p1 = new Position(position.getX(), position.getY() + 1);
                Position p2 = new Position(position.getX(), position.getY() + 2);
                if (getBoard().getPiece(p1) == null && getBoard().getPiece(p2) == null) {
                    assist[position.getX()][position.getY() + 2] = true;
                }
            }
            // #specialmove castling queenside rook
            Position posT2 = new Position(position.getX(), position.getY() - 4);
            if (testRookCastling(posT2)) {
                Position p1 = new Position(position.getX(), position.getY() - 1);
                Position p2 = new Position(position.getX(), position.getY() - 2);
                Position p3 = new Position(position.getX(), position.getY() - 3);
                if (getBoard().getPiece(p1) == null && getBoard().getPiece(p2) == null && getBoard().getPiece(p3) == null) {
                    assist[position.getX()][position.getY() - 2] = true;
                }
            }
        }

        return assist;
    }
}
