//CREATED BY JADEYN FINCHER
//jadeyn.fincher@gmail.com


import java.util.Scanner;



class Scratch {
    static int win = 3; // win condition 0=O win, 1=X win,2 = Tie
    static int debug = 0; // A Value of 1 enables debug mode, a Value of 0 disables it.
    static char Board_Player[][]; //The board the player will see
    static int Board_Comp[][]; // The board the Computer will use
    static int playerValue = 4; // messing with this value will start logical errors
    static char playerLetter = 'O'; // Can change this to any random Letter - won't change AI's X value
    static int who_goes_first = 0; // val of 1 equals player first - handled by menu

    static void gameRun() {
        if (win == 3) {
            if(who_goes_first==1){
                playerTurn();
            }else {

                computerTurn();
            }
            if (win == 3) {
                if(who_goes_first==1){
                    computerTurn();
                }else {

                    playerTurn();
                }

            }
            gameRun();
        } else if (win == 0) {
            printBoard();
            System.out.println("\n\nYou have won!");
        } else if (win == 1) {
            printBoard();
            System.out.println("\n\nThe Computer has bested you!");

        } else if (win == 2) {
            printBoard();
            System.out.println("\n\nA Tie!");
        }

    }

    static void winChecker() {
        Board_Comp[1][4] = 0; // total
        Board_Comp[2][4] = 0;
        Board_Comp[3][4] = 0;
        for (int i = 0; i < 3; i++) {
            Board_Comp[i][3] = 0;
            Board_Comp[3][i] = 0;

        }


        /*
            |	0	0	0	1   0	|
            |	0   0	0	1   4   |
            |	0	0	0	1   3	|
            |	1	1	1	0   3	|

            A value of X = 1, Value of O =4
            3 in a row of X = 3, 3 in a row of O = 12,

        */

        // Totals Rows
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 3; k++) {

                Board_Comp[i][3] = Board_Comp[i][3] + Board_Comp[i][k];
            }
        }
        // Totals Cols
        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 3; k++) {
                Board_Comp[3][i] = Board_Comp[3][i] + Board_Comp[k][i];

            }
        }

        //Totals Diags
        for (int i = 0; i < 3; i++) {

            Board_Comp[2][4] = Board_Comp[2][4] + Board_Comp[i][i];
            Board_Comp[3][4] = Board_Comp[3][4] + Board_Comp[i][2 - i];
            //Checks Slots for X and O Wins
            if (Board_Comp[3][4] == 12 || Board_Comp[2][4] == 12) {
                win = 0;
            }
            if (Board_Comp[3][4] == 3 || Board_Comp[2][4] == 3) {
                win = 1;
            }


        }
        // totals board
        for (int i = 0; i < 3; i++) {
            Board_Comp[1][4] = Board_Comp[i][3] + Board_Comp[1][4];
        }
        for (int i = 0; i < 3; i++) {
            // Checks Collumns and Rows for X wins
            if (Board_Comp[3][i] == 3 || Board_Comp[i][3] == 3) {
                win = 1;
            }
            // Checks Collumns and Rows for O wins
            if (Board_Comp[i][3] == 12 || Board_Comp[3][i] == 12) {
                win = 0;
            }
        }
        if (Board_Comp[1][4] == 24 || Board_Comp[1][4] == 21) {
            if (win == 3) { // Incase a win condition was met on the last move
                win = 2;
            }

        }
    }

    static void resetBoard() {


        Board_Player = new char[][]{{'-', '-', '-'}, {'-', '-', '-'}, {'-', '-', '-'}}; // Sets all values of players board to -
        Board_Comp = new int[][]{{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}}; // Clears Array


    }

    static void printBoard() {
        //Prints players board first
        System.out.print("_________________");
        for (int i = 0; i < 3; i++) {
            System.out.print("\t\n|");
            for (int k = 0; k < 3; k++) {

                System.out.print("\t" + Board_Player[i][k]);
            }
            System.out.print("\t|");
        }
        System.out.print("\n_________________");
        if (debug == 1) {
            System.out.println();
            for (int i = 0; i < 4; i++) {
                System.out.print("\t\n|");
                for (int k = 0; k < 5; k++) {

                    System.out.print("\t" + Board_Comp[i][k]);
                }
                System.out.print("\t|");
            }
            System.out.print("\n_________________");

            /*
            Below shows a diagram of where the totals are stored for calculating who has won. 1's Represent where Row/Column
            totals will be stored, while the 3's show where the diagnal totals will go. The 4 is the total of the whole board.
            |	0	0	0	1   0	|
            |	0   0	0	1   4   |
            |	0	0	0	1   3	|
            |	1	1	1	0   3	|



             */
        }
    }


    static void printPlacementBoard() {
        System.out.println("\n_________________\t\n" +
                "|\t1\t2\t3\t|\t\n" +
                "|\t4\t5\t6\t|\t\n" +
                "|\t7\t8\t9\t|\n" +
                "_________________");
    }

    static void playerTurn() {

        System.out.println("-----------------------------\n\n");
        printBoard();
        printPlacementBoard();
        System.out.println("\nWhere would you like to place your piece?\nUse the numbered chart below the board to find placement.");
        grabPlacement();


    }

    static void verifyPlacement(int place[]) {

        if (Board_Comp[place[0]][place[1]] == 0) {

            Board_Comp[place[0]][place[1]] = playerValue;
            Board_Player[place[0]][place[1]] = playerLetter;
            winChecker();
        } else {
            System.out.println("This position is filled, select a different spot");
            grabPlacement();
        }
    }

    static int grabPlacement() {
        int placement = 0;
        try {
            int[] place;
            Scanner myScan = new Scanner(System.in);
            placement = myScan.nextInt();
            switch (placement) {
                case 1:
                    place = new int[]{0, 0};
                    verifyPlacement(place);
                    break;
                case 2:
                    place = new int[]{0, 1};
                    verifyPlacement(place);
                    break;
                case 3:
                    place = new int[]{0, 2};
                    verifyPlacement(place);
                    break;
                case 4:
                    place = new int[]{1, 0};
                    verifyPlacement(place);
                    break;
                case 5:
                    place = new int[]{1, 1};
                    verifyPlacement(place);
                    break;
                case 6:
                    place = new int[]{1, 2};
                    verifyPlacement(place);
                    break;
                case 7:
                    place = new int[]{2, 0};
                    verifyPlacement(place);
                    break;
                case 8:
                    place = new int[]{2, 1};
                    verifyPlacement(place);
                    break;
                case 9:
                    place = new int[]{2, 2};
                    verifyPlacement(place);
                    break;
                default:
                    System.out.println("Value Not accepted!");
                    playerTurn();
                    break;

            }
        } catch (Exception e) {
            System.out.println("Unknown option");
            playerTurn();
        }
        // System.out.println(placement);
        return placement;
    }



    static void computerTurn(){
        /* LOGIC BEHIND AI

        I designed this AI to play offensively only when no defensive moves are required. 
        It evaluates priority rows and columns by checking if the Board_Comp array contains a total of 8 in any line—indicating that the opponent is one move away from winning.
        In such cases, the AI switches to a defensive move to block.
        The only exception is if the AI itself can win in the next move—for example, if a row, column, or diagonal totals 2, meaning the final slot would secure a win. 
        The same logic applies to diagonals as well.




         */

        int priorityRowValue =-1;
        int priorityColValue =-1;
        int priorityDiag1Value = -1;
        int priorityDiag2Value =-1;
        int[] placement = new int[]{-1,-1};
        for(int i=0;i<3;i++){

            if(Board_Comp[i][3]==8) {
                priorityRowValue = i;
            }if(Board_Comp[3][i]==8) {
                priorityColValue = i;
            }if(Board_Comp[3][4]==8){
                priorityDiag2Value=2;
            }
            if(Board_Comp[2][4]==8){
                priorityDiag2Value=2;
            }
        }
        if (placement[0]==-1){
            //Sees if there are two X's in a row meaning that it can win if it places the piece in the next slot

            for(int i=0;i<3;i++){


                if(Board_Comp[i][3]==2) {

                    placement[0] = i;
                    for(int k=0;k<3;k++) {
                        if (Board_Comp[i][k] == 0) {
                            placement[1] = k;
                        }
                    }
                }if(Board_Comp[3][i]==2) {
                    placement[1] = i;
                    for(int k=0;k<3;k++) {
                        if(Board_Comp[i][k]==0) {
                            placement[0]=k;
                        }
                    }
                }

            }
            if(Board_Comp[3][4]==2){
                for(int i=0;i<3;i++){
                    if(Board_Comp[i][2-i]==0){
                        placement= new int[]{i,2-i};
                    }

                }
            }
            if(Board_Comp[2][4]==2){
                for(int i=0;i<3;i++){
                    if(Board_Comp[i][i]==0){
                        placement= new int[]{i,i};
                    }

                }
            }
        }
        if(debug==1) {
            System.out.println("priorities: " + priorityRowValue + "\t" + priorityColValue
                    + "\n highest: "
            );
        }
        // If no win was possible on this move it makes sure there aren't two O's in a row meaning it needs to defend
        if (placement[0]==-1) {
            if (priorityRowValue > -1) {
                for (int i = 0; i < 3; i++) {
                    if (Board_Comp[priorityRowValue][i] == 0) {
                        placement = new int[]{priorityRowValue, i};
                        if(debug==1) {
                            System.out.println("Placement PR: " + placement[0] + " " + placement[1]);
                        }
                    }
                }
            }
            if (priorityColValue > -1) {
                for (int i = 0; i < 3; i++) {
                    if (Board_Comp[i][priorityColValue] == 0) {
                        placement = new int[]{i, priorityColValue};
                        if(debug==1) {
                            System.out.println("Placement PC: " + placement[0] + " " + placement[1]);
                        }
                    }
                }

            } if(priorityDiag1Value>-1){
                for(int i=0;i<3;i++){
                    if(Board_Comp[i][i]==0){
                        placement= new int[]{i,i};
                    }

                }
            }if(priorityDiag2Value>-1) {
                for(int i=0;i<3;i++){
                    if(Board_Comp[i][2-i]==0){
                        placement= new int[]{i,2-i};
                    }

                }

            }
        }
        //Priority grabbing middle spot if there defend/attack conditions weren't met
        if (placement[0]==-1){
            if(Board_Comp[1][1]==0) {
                placement= new int[]{1,1};
            }
            else if(Board_Comp[0][0]==0) {
                placement= new int[]{0,0};
            }else if(Board_Comp[2][0]==0) {
                placement= new int[]{2,0};
            }

        }

        //Iterates through to find open slot if there defend/attack conditions weren't met and priority slots aren't avail
        if (placement[0]==-1){
            for(int i=0;i<3;i++){
                for(int k=0;k<3;k++){
                    if(Board_Comp[i][k]==0){
                        placement= new int[]{i,k};
                    }
                }
            }
        }
        //Places piece
        if(debug==1) {
            System.out.println("Placements: " + placement[0] + " " + placement[1]);
        }

        Board_Comp[placement[0]][placement[1]]=1;
        Board_Player[placement[0]][placement[1]]='X';
        //checks for win after piece is placed
        winChecker();

    }

    static void menu(){
        try{
        System.out.println("Would you like to be the first placer or the second? Please enter 1 or 2");
        Scanner myScan = new Scanner(System.in);
        int option_for_sequence = myScan.nextInt();
        if(option_for_sequence==1){
            who_goes_first =1;
        }
        } catch(Exception e){
            System.out.println("Unknown Option please renter!");
            menu();
            }
    }
    public static void main(String[] args) {
        resetBoard();
        menu();
        gameRun();
    }
}
