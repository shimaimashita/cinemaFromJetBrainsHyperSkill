//package cinema;
import java.util.Scanner;

public class Cinema {
    public static void main(String[] args) {
        Theatre theatre = getTheatre();
        boolean exFlag = true;
        while (exFlag) {
            int controlValue = executeMenu();
            switch(controlValue) {
                case 1:
                    theatre.printSeats();
                    break;
                case 2:
                    while (!theatre.executeBuy());
                    break;
                case 3:
                    theatre.getStatistics();
                    break;
                case 0:
                    exFlag = false;
                    break;
                default:
                    System.out.println("Wrong input!");
                    break;
            }
        }
    }
    
    public static Theatre getTheatre() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();
        return new Theatre(rows, seats);
    }
    
    public static int executeMenu() {
        System.out.print("1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit\n");
        Scanner scanner = new Scanner(System.in);
        int returnValue = scanner.nextInt();
        return returnValue;
    }
}

class Theatre {
    final int rows;
    final int seats;
    final int totalIncome;
    
    char[][] seatsArray;
    int numberOfTickets;
    int currentIncome;

    
    Theatre(int rows, int seats) {
        this.rows = rows;
        this.seats = seats;
        this.seatsArray = getCinema(rows, seats);
        this.numberOfTickets = 0;
        this.currentIncome = 0;
        this.totalIncome = getTotalIncome(rows, seats);
    }
    
    private static int getTotalIncome(int rows, int seats) {
        int totalIncome = 0;
        if (rows < 0 || seats < 0) {
            return 0;
        } else if (rows * seats <= 60) {
            totalIncome += 10 * rows * seats;
        } else {
            totalIncome += seats * (rows / 2 * 10 + (rows - rows / 2) * 8);
        }
        return totalIncome;
    }
    
    public void printSeats() {
        System.out.println("Cinema:");
        System.out.print(" ");
        for (int j = 0; j < this.seats; j++) {
            System.out.print(" " + (j + 1));
        }
        System.out.println();
        for (int i = 0; i < this.rows; i++) {
            System.out.print(i + 1);
            for (int j = 0; j < this.seats; j++) {
                System.out.print(" " + this.seatsArray[i][j]);
            }
            System.out.println();
        }
    }
    
    private int getTicketPrice(int row, int seat) {
        int ticketPrice = 0;
        if (this.rows < 0 || this.seats < 0) {
            return 0;
        } else if (this.rows * this.seats <= 60) {
            ticketPrice = 10;
        } else if (row <= this.rows / 2) {
            ticketPrice = 10;
        } else {
            ticketPrice = 8;
        }
        return ticketPrice;
    }
    
    public boolean executeBuy() {
        if (this.numberOfTickets >= this.rows * this.seats) {
            System.out.println("Not enough tikets");
            return true;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter a row number:");
        int row = sc.nextInt();
        System.out.println("Enter a seat number in that row:");
        int seat = sc.nextInt();
        
        if (row < 0 || row > this.rows || seat < 0 || seat > this.seats) {
            System.out.println("Wrong input!");
            return false;
        }
        
        int ticketPrice = getTicketPrice(row, seat);
        
        if (this.seatsArray[row-1][seat-1] == 'B') {
            System.out.println("That ticket has already been purchased!");
            return false;
        } else {
            this.numberOfTickets++;
            this.currentIncome += ticketPrice;
            System.out.println("\nTicket price: $" + ticketPrice + "\n");
            this.seatsArray[row-1][seat-1] = 'B';
            return true;
        }
    }
    
    public void getStatistics() {
        System.out.println("Number of purchased tickets: " + this.numberOfTickets);
        if (this.rows * this.seats != 0) {
            System.out.printf("Percentage: %.2f",
                (double) this.numberOfTickets /
                (this.rows * this.seats) * 100.0);
            System.out.println("%");
        } else {
            System.out.println("No seats");
        }
        System.out.println("Current income: $" + this.currentIncome);
        System.out.println("Total income: $" + this.totalIncome);
    }
    
    private static char[][] getCinema(int rows, int seats) {        
        char [][] seatsArray = new char[rows][seats];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < seats; j++) {
                seatsArray[i][j] = 'S';
            }
        }
        return seatsArray;
    }   
}
