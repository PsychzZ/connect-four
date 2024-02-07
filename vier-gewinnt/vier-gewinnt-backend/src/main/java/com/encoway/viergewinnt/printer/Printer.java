package com.encoway.viergewinnt.printer;

import com.encoway.viergewinnt.game.GameState;
import com.encoway.viergewinnt.dto.Player;

import java.util.Objects;

public class Printer {

    public void greetingMessage() {
        System.out.println("""
                Welcome To Connect 4!!:
                The Game uses X and O as Symbols for the Player
                                
                X Starts!!!
                """);

    }

    public void printColumnSelectMessage() {
        System.out.println("Choose a Column with typing 0-6");
    }

    public void displayField(GameState gameState) {
        int rows = gameState.getField().length;
        int cols = gameState.getField()[0].length;
        int cellWidth = 5; // Width of each cell

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Player cellValue = gameState.getField()[i][j];
                String paddedValue = padCellValue(String.valueOf(cellValue), cellWidth);
                System.out.print("|" + paddedValue + "|");
            }
            System.out.println();
        }
    }

    private String padCellValue(String cellValue, int cellWidth) {
        String paddedValue;

        paddedValue = Objects.requireNonNullElse(cellValue, "");

        int padding = cellWidth - paddedValue.length();
        int leftPadding = padding / 2;
        int rightPadding = padding - leftPadding;

        return " ".repeat(Math.max(0, leftPadding)) +
                paddedValue +
                " ".repeat(Math.max(0, rightPadding));
    }


    public void printWinner(Player player) {
        System.out.println(player + " has won the Connect 4 Game!!!\n");
    }

    public void printDraw() {
        System.out.println(" This Game ended as an Draw!!!\n");
    }

    public void printAnotherGame() {
        System.out.println("Are you willing to play another Game?(true or false)");
    }

    public void printInputCorrectColumnNumber() {
        System.out.println("ERROR: Please type a correct number");
    }

    public void printInputCorrectBoolean() {
        System.out.println("ERROR Please Type true or False");
    }
}
