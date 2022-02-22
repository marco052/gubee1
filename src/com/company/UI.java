package com.company;

import boardgame.Piece;
import chess.ChessPiece;
import chess.ChessPosition;

import javax.swing.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {

    public static void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void printBoard(ChessPiece[][] pieces){
        for (int i = 0 ; i < pieces.length; i++) {
            System.out.print(8-i + " ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j]);
            }
            System.out.println();
        }
        System.out.println("  A B C D E F G H");
    }

    public static ChessPosition readChessPosition (Scanner scanner){
        try{
            String s = scanner.nextLine();
            char column = s.charAt(0);
            int row = Integer.parseInt(s.substring(1));
            return new ChessPosition(column, row);
        }
        catch(RuntimeException e){
            throw new InputMismatchException("Values are between 1 and 8");
        }
    }

    public static void printPiece(Piece piece){
        if(piece != null){
            System.out.print(piece);
        }
        else{
            System.out.print("-");
        }
        System.out.print(" ");
    }

}
