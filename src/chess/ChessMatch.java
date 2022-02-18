package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.Rook;

public class ChessMatch {
    private Board board;

    public ChessMatch(){
        this.board = new Board(8,8);
    }

    public ChessPiece[][] getPieces(){
        ChessPiece[][] assist = new ChessPiece[board.getRows()][board.getColumns()];
        initialSetup();
        for (int i = 0 ; i < board.getRows(); i++)
        {
            for (int j = 0; j < board.getColumns(); j++)
            {
                assist[i][j] = (ChessPiece) board.getPiece(i, j);
            }
        }
        return assist;
    }

    private void placePieceOnBoard(char column, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }

    private void initialSetup(){
        placePieceOnBoard('e', 8 , new Rook(board, Color.WHITE));
    }

}
