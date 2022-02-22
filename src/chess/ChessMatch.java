package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Rook;

import java.nio.file.attribute.PosixFileAttributes;

public class ChessMatch {
    private Board board;

    public ChessMatch(){
        this.board = new Board(8,8);
        initialSetup();
    }

    public ChessPiece[][] getPieces(){
        ChessPiece[][] assist = new ChessPiece[board.getRows()][board.getColumns()];

        for (int i = 0 ; i < board.getRows(); i++)
        {
            for (int j = 0; j < board.getColumns(); j++)
            {
                assist[i][j] = (ChessPiece) board.getPiece(i, j);
            }
        }
        return assist;
    }

    public ChessPiece movePiece(ChessPosition origin, ChessPosition destiny){
        Position matrixOrigin = origin.toPosition();
        Position matrixDestiny = destiny.toPosition();
        validateOriginPosition(matrixOrigin);
        validateDestinyPosition(matrixOrigin, matrixDestiny);
        Piece capturedPiece = makeMove(matrixOrigin, matrixDestiny);
        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position origin, Position destiny){
        Piece p = board.removePiece(origin);
        Piece capturedPiece = board.removePiece(destiny);
        board.placePiece(p, destiny);
        return capturedPiece;
    }

    public void validateOriginPosition(Position position){
        if(!board.thereIsAPiece(position)){
            throw new ChessException("there is no piece on origin position");
        }
        if(!board.getPiece(position).isThereAnyPossibleMove()){
            throw new ChessException("the piece cannot move");
        }
    }

    private void validateDestinyPosition(Position origin, Position destiny){
        if(!board.getPiece(origin).posibleMove(destiny)){
            throw new ChessException("this piece cannot move to the selected destiny");
        }
    }

    private void placePieceOnBoard(char column, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }

    private void initialSetup(){
        placePieceOnBoard('e', 8 , new Rook(board, Color.WHITE));
    }


}
