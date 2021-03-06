package com.company;

import boardgame.Position;
import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ChessMatch chessMatch = new ChessMatch();
        Scanner scanner = new Scanner(System.in);
        List<ChessPiece> captured = new ArrayList<>();

        while (!chessMatch.getCheckMate()){
            try {
                UI.clearScreen();
                UI.printMatch(chessMatch, captured);
                System.out.println();
                System.out.println("origin: ");
                ChessPosition origin = UI.readChessPosition(scanner);

                boolean[][] possibleMoves = chessMatch.possibleMoves(origin);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMoves);

                System.out.println();
                System.out.println("destiny: ");
                ChessPosition destiny = UI.readChessPosition(scanner);

                ChessPiece capturedPiece = chessMatch.movePiece(origin, destiny);

                if(capturedPiece != null){
                    captured.add(capturedPiece);
                }

                System.out.println(chessMatch.getPromoted());

                if(chessMatch.getPromoted() != null){
                    System.out.println("enter the piece for promotion: B/N/R/Q");
                    String type = scanner.nextLine().toUpperCase();
                    chessMatch.replacePromotedPiece(type);
                }
            }
            catch (ChessException e){
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
            catch (InputMismatchException e){
                System.out.println(e.getMessage());
                scanner.nextLine();
            }
        }
        UI.clearScreen();
        UI.printMatch(chessMatch, captured);
    }
}