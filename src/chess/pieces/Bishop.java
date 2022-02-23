package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

    public Bishop(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "B";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] assist = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

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