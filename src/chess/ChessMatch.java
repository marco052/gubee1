package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

import java.nio.file.attribute.PosixFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {
    private Board board;
    private int turn;
    private Color currentPlayer;
    private boolean check;
    private List<Piece> piecesOnTheBoard;
    private List<Piece> capturedPpieces;

    public ChessMatch(){
        piecesOnTheBoard = new ArrayList<>();
        capturedPpieces = new ArrayList<>();
        turn = 1;
        currentPlayer = Color.WHITE;
        this.board = new Board(8,8);
        initialSetup();
    }

    public boolean getCheck(){
        return check;
    }

    public int getTurn(){
        return turn;
    }

    public Color getCurrentPlayer(){
        return currentPlayer;
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

    public boolean[][] possibleMoves(ChessPosition origin){
        Position assist = origin.toPosition();
        validateOriginPosition(assist);
        return board.getPiece(assist).possibleMoves();
    }


    public ChessPiece movePiece(ChessPosition origin, ChessPosition destiny){
        Position matrixOrigin = origin.toPosition();
        Position matrixDestiny = destiny.toPosition();
        validateOriginPosition(matrixOrigin);
        validateDestinyPosition(matrixOrigin, matrixDestiny);
        Piece capturedPiece = makeMove(matrixOrigin, matrixDestiny);
        if(testCheck(currentPlayer)){
            undoMove(matrixOrigin, matrixDestiny, capturedPiece);
            throw new ChessException("you can't put yourself in check");
        }

        check = (testCheck(opponent(currentPlayer))) ? true : false;
        nextTurn();
        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position origin, Position destiny){
        Piece p = board.removePiece(origin);
        Piece capturedPiece = board.removePiece(destiny);
        board.placePiece(p, destiny);

        if (capturedPiece != null){
            piecesOnTheBoard.remove(capturedPiece);
            capturedPpieces.add(capturedPiece);
        }
        return capturedPiece;
    }

    private void undoMove(Position origin, Position destiny, Piece capturedPiece){
        Piece p = board.removePiece(destiny);
        board.placePiece(p, origin);
        if(capturedPiece != null){
            board.placePiece(capturedPiece, destiny);
        }
        capturedPpieces.remove(capturedPiece);
        piecesOnTheBoard.add(capturedPiece);
    }

    public void validateOriginPosition(Position position){
        if(!board.thereIsAPiece(position)){
            throw new ChessException("there is no piece on origin position");
        }
        if(currentPlayer != ((ChessPiece)board.getPiece(position)).getColor()){
            throw new ChessException("this piece belongs to the other player");
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

    private Color opponent(Color color){
        return (color == Color.WHITE)? Color.BLACK : Color.WHITE;
    }

    private ChessPiece king(Color color){
        List<Piece> assist = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for(Piece p : assist){
            if(p instanceof King){
                return (ChessPiece) p;
            }
        }
        throw new IllegalStateException("There is no" + color + "on the board");
    }

    private boolean testCheck (Color color){
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent((color))).collect(Collectors.toList());
        for(Piece p : opponentPieces){
            boolean[][] assist = p.possibleMoves();
            if(assist[kingPosition.getX()][kingPosition.getY()]){
                return true;
            }
        }
        return false;
    }

    private void placePieceOnBoard(char column, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    private void nextTurn(){
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE)? Color.BLACK : Color.WHITE;
    }

    private void initialSetup(){
        placePieceOnBoard('c', 1, new Rook(board, Color.WHITE));
        placePieceOnBoard('c', 2, new Rook(board, Color.WHITE));
        placePieceOnBoard('d', 2, new Rook(board, Color.WHITE));
        placePieceOnBoard('e', 2, new Rook(board, Color.WHITE));
        placePieceOnBoard('e', 1, new Rook(board, Color.WHITE));
        placePieceOnBoard('d', 1, new King(board, Color.WHITE));

        placePieceOnBoard('c', 7, new Rook(board, Color.BLACK));
        placePieceOnBoard('c', 8, new Rook(board, Color.BLACK));
        placePieceOnBoard('d', 7, new Rook(board, Color.BLACK));
        placePieceOnBoard('e', 7, new Rook(board, Color.BLACK));
        placePieceOnBoard('e', 8, new Rook(board, Color.BLACK));
        placePieceOnBoard('d', 8, new King(board, Color.BLACK));

    }


}
