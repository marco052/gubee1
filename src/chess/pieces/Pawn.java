package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    private ChessMatch chessMatch;

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] assist = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        if(getColor() == Color.WHITE){
            p.setPosition(position.getX() - 1, position.getY());
            if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
                assist[p.getX()][p.getY()] = true;
            }
            p.setPosition(position.getX() - 2, position.getY());
            Position p2 = new Position(position.getX() - 1, position.getY());
            if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0){
                assist[p.getX()][p.getY()] = true;
            }
            p.setPosition(position.getX() - 1, position.getY()-1);
            if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
                assist[p.getX()][p.getY()] = true;
            }
            p.setPosition(position.getX() - 1, position.getY()+1);
            if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
                assist[p.getX()][p.getY()] = true;
            }

            if(position.getX() == 3){
                Position left = new Position(position.getX(), position.getY()-1);
                if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().getPiece(left) == chessMatch.getEnPassantVulnerable()){
                    assist[left.getX()-1][left.getY()] = true;
                }

                Position right = new Position(position.getX(), position.getY()+1);
                if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().getPiece(right) == chessMatch.getEnPassantVulnerable()){
                    assist[right.getX()-1][right.getY()] = true;
                }
            }

        }
        else{
            p.setPosition(position.getX() + 1, position.getY());
            if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
                assist[p.getX()][p.getY()] = true;
            }
            p.setPosition(position.getX() + 2, position.getY());
            Position p2 = new Position(position.getX() + 1, position.getY());
            if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0){
                assist[p.getX()][p.getY()] = true;
            }
            p.setPosition(position.getX() + 1, position.getY()-1);
            if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
                assist[p.getX()][p.getY()] = true;
            }
            p.setPosition(position.getX() + 1, position.getY()+1);
            if(getBoard().positionExists(p) && isThereOpponentPiece(p)){
                assist[p.getX()][p.getY()] = true;
            }

            if(position.getX() == 4){
                Position left = new Position(position.getX(), position.getY()-1);
                if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().getPiece(left) == chessMatch.getEnPassantVulnerable()){
                    assist[left.getX()+1][left.getY()] = true;
                }

                Position right = new Position(position.getX(), position.getY()+1);
                if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().getPiece(right) == chessMatch.getEnPassantVulnerable()){
                    assist[right.getX()+1][right.getY()] = true;
                }
            }
        }
        return assist;
    }

    @Override
    public String toString(){
        return "P";
    }

}
