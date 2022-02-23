package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.*;

import java.nio.file.attribute.PosixFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {
    private Board board;
    private int turn;
    private Color currentPlayer;
    private boolean check;
    private boolean checkMate;
    private List<Piece> piecesOnTheBoard;
    private List<Piece> capturedPieces;

    public ChessMatch(){
        piecesOnTheBoard = new ArrayList<>();
        capturedPieces = new ArrayList<>();
        turn = 1;
        currentPlayer = Color.WHITE;
        this.board = new Board(8,8);
        initialSetup();
    }

    public boolean getCheckMate(){
        return checkMate;
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


        if(testCheckMate(opponent(currentPlayer))){
            checkMate = true;
        }
        else{
            nextTurn();
        }

        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position origin, Position destiny){
        ChessPiece p = (ChessPiece)board.removePiece(origin);
        p.increaseMoveCount();
        Piece capturedPiece = board.removePiece(destiny);
        board.placePiece(p, destiny);

        if (capturedPiece != null){
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        // #specialmove castling kingside rook
        if (p instanceof King && destiny.getY() == origin.getY() + 2) {
            Position sourceT = new Position(origin.getX(), origin.getY() + 3);
            Position targetT = new Position(origin.getX(), origin.getY() + 1);
            ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
            board.placePiece(rook, targetT);
            rook.increaseMoveCount();
        }

        // #specialmove castling queenside rook
        if (p instanceof King && destiny.getY() == origin.getY() - 2) {
            Position sourceT = new Position(origin.getX(), origin.getY() - 4);
            Position targetT = new Position(origin.getX(), origin.getY() - 1);
            ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
            board.placePiece(rook, targetT);
            rook.increaseMoveCount();
        }

        return capturedPiece;
    }

    private void undoMove(Position origin, Position destiny, Piece capturedPiece){
        ChessPiece p = (ChessPiece)board.removePiece(destiny);
        p.decreaseMoveCount();
        board.placePiece(p, origin);
        if(capturedPiece != null){
            board.placePiece(capturedPiece, destiny);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }

        if (p instanceof King && destiny.getY() == origin.getY() + 2) {
            Position sourceT = new Position(origin.getX(), origin.getY() + 3);
            Position targetT = new Position(origin.getX(), origin.getY() + 1);
            ChessPiece rook = (ChessPiece)board.removePiece(targetT);
            board.placePiece(rook, sourceT);
            rook.decreaseMoveCount();
        }

        // #specialmove castling queenside rook
        if (p instanceof King && destiny.getY() == origin.getY() - 2) {
            Position sourceT = new Position(origin.getX(), origin.getY() - 4);
            Position targetT = new Position(origin.getX(), origin.getY() - 1);
            ChessPiece rook = (ChessPiece)board.removePiece(targetT);
            board.placePiece(rook, sourceT);
            rook.decreaseMoveCount();
        }

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

    private boolean testCheckMate (Color color){
        if(!testCheck(color)){
            return false;
        }
        List<Piece> assist = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for (Piece p : assist){
            boolean[][] test = p.possibleMoves();
            for(int i = 0; i < board.getRows(); i++){
                for(int j = 0; j < board.getColumns(); j++){
                    if(test[i][j]){
                        Position origin = ((ChessPiece)p).getChessPosition().toPosition();
                        Position destiny = new Position(i, j);
                        Piece capturedPiece = makeMove(origin, destiny);
                        boolean testCheck = testCheck(color);
                        undoMove(origin, destiny, capturedPiece);
                        if(!testCheck){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
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

        placePieceOnBoard('a', 1, new Rook(board, Color.WHITE));
        placePieceOnBoard('b', 1, new Knight(board, Color.WHITE));
        placePieceOnBoard('c', 1, new Bishop(board, Color.WHITE));
        placePieceOnBoard('d', 1, new Queen(board, Color.WHITE));
        placePieceOnBoard('e', 1, new King(board, Color.WHITE, this));
        placePieceOnBoard('f', 1, new Bishop(board, Color.WHITE));
        placePieceOnBoard('g', 1, new Knight(board, Color.WHITE));
        placePieceOnBoard('h', 1, new Rook(board, Color.WHITE));
        placePieceOnBoard('a', 2, new Pawn(board, Color.WHITE));
        placePieceOnBoard('b', 2, new Pawn(board, Color.WHITE));
        placePieceOnBoard('c', 2, new Pawn(board, Color.WHITE));
        placePieceOnBoard('d', 2, new Pawn(board, Color.WHITE));
        placePieceOnBoard('e', 2, new Pawn(board, Color.WHITE));
        placePieceOnBoard('f', 2, new Pawn(board, Color.WHITE));
        placePieceOnBoard('g', 2, new Pawn(board, Color.WHITE));
        placePieceOnBoard('h', 2, new Pawn(board, Color.WHITE));

        placePieceOnBoard('a', 8, new Rook(board, Color.BLACK));
        placePieceOnBoard('b', 8, new Knight(board, Color.BLACK));
        placePieceOnBoard('c', 8, new Bishop(board, Color.BLACK));
        placePieceOnBoard('d', 8, new Queen(board, Color.BLACK));
        placePieceOnBoard('e', 8, new King(board, Color.BLACK, this));
        placePieceOnBoard('f', 8, new Bishop(board, Color.BLACK));
        placePieceOnBoard('g', 8, new Knight(board, Color.BLACK));
        placePieceOnBoard('h', 8, new Rook(board, Color.BLACK));
        placePieceOnBoard('a', 7, new Pawn(board, Color.BLACK));
        placePieceOnBoard('b', 7, new Pawn(board, Color.BLACK));
        placePieceOnBoard('c', 7, new Pawn(board, Color.BLACK));
        placePieceOnBoard('d', 7, new Pawn(board, Color.BLACK));
        placePieceOnBoard('e', 7, new Pawn(board, Color.BLACK));
        placePieceOnBoard('f', 7, new Pawn(board, Color.BLACK));
        placePieceOnBoard('g', 7, new Pawn(board, Color.BLACK));
        placePieceOnBoard('h', 7, new Pawn(board, Color.BLACK));



    }


}
