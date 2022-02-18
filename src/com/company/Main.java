package com.company;

import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ChessMatch chessMatch = new ChessMatch();

        Scanner scanner = new Scanner(System.in);

        while (true){
            UI.printBoard(chessMatch.getPieces());
            System.out.println();
            System.out.println("origin: ");
            ChessPosition origin = UI.readChessPosition(scanner);

            System.out.println();
            System.out.println("destiny: ");
            ChessPosition destiny = UI.readChessPosition(scanner);

            ChessPiece capturedPiece = chessMatch.movePiece(origin, destiny);
        }
    }
}
