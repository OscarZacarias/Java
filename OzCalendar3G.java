// Programmer: Oscar Zacarias
// Date: 08/17/23
// Class: CS &141
// Assignment: Calendar part 3
/* Purpose: This program will display an interactive menu that will prompt if they have a file with events to enter.
After that will be prompt with a menu that contains multiple commands to do. The user will be able to add an event 
to the calendar(ev), print out a month to a text file(fp), print a calendar for a specific date they want to see(e) 
or print a calendar with the current day(t). The user will also have the option to see the previous(p) 
or next month(n) (after selecting first "e" or "t") */ 

import java.io.*;                    
import java.util.*;
public class OzCalendar3G {
   static String [][] eventArray = new String[12][];
   public static void main(String[] args)  throws FileNotFoundException{
      Scanner scan = new Scanner(System.in);
      String command = "";
      String date = "";
      String eventDate ="";
      for(int i = 0; i < eventArray.length; i++){ // if month less than 9 add a 0 to make it mm
         if (i < 9){
            eventDate = "0" + Integer.toString(i+1)+ "/01"; // i = 0:. 0+1 = Jan(01)
         } else {
            eventDate = Integer.toString(i+1)+ "/01"; // "01" is starting day of month
         }
         eventArray[i] = new String [daysInMonth(eventDate)]; // deciding the size of the second dimension of the array
      }
      System.out.print("Do you want to retreive data from an event's file?(Yes/No): ");  
      String retreive = scan.next().toLowerCase();
      if( retreive.startsWith("y")){
         getData(); 
      } 
      do {
         command = menu();
         date = doChoice(command, date);
      }      
      while(!command.equals("q"));
   } //end of main method
   
       // drawMonth method displays the month and the graphical calendar
   public static void drawMonth(int month, String date, int today) { // int today is for printing "Day" on the row that has the day of the date, it will be either "day / currentDay or 0"
      System.out.printf("%35d%n%n", month); // Prints the current month at the top of the calendar
      System.out.println("   SUN          MON        TUE        WED        THU        FRI        SAT");
      int startingDay = getStartingDay(month); //get the startingDay for month
      int numberDay = daysInMonth(date);
      int rows = 4; // printing only 5 rows 7+5 =35
      if((numberDay + startingDay) > 36) //numDay=31 + startingDay=7 :. 38 > 35 
         rows = 5;  // print one more row to store the remaing days after the 35+7 values(days)
      for(int i = 0; i <= rows; i++){
         drawRow(i, date, startingDay, today);
      }
      for (int j = 0; j <= 76; j++){
         System.out.print("=");
      }
      System.out.println();
   } // End of drawMonth
   
    //This method displays the weeks on the calendar when it's called from "drawMonth"
   public static void drawRow(int row, String date, int startingDay, int today) {
      for (int i = 0; i <= 76; i++){
         System.out.print("=");
      }
      System.out.println();
      System.out.print("|");
      int numberDay= daysInMonth(date) + startingDay;
      
      for (int i = (7*row)+1; i <= (7*row)+7; i++) { //columns 2nd
         if(i<numberDay && i >= startingDay){
            if ((i-startingDay + 1) == today) {// today is the day from date
               System.out.printf("%3d  %-2s  |", (i - startingDay + 1), "Day");
            } else {
               String event = eventArray[monthFromDate(date)- 1][i - startingDay]; // Get event for the day
               
               
               if (event != null) {
                  if(event.length() == 7)
                     System.out.printf("%2d %-2s|", (i- startingDay+1), event); // Display event title
                  else if(event.length() == 6)
                     System.out.printf("%2d %-2s |", (i- startingDay+1), event); // Display event title
                  else if(event.length() == 5)
                     System.out.printf("%2d %-2s  |", (i- startingDay+1), event); // Display event title
                  else if(event.length() == 4)
                     System.out.printf("%2d %-2s   |", (i- startingDay+1), event); // Display event title
                  else if(event.length() == 3)
                     System.out.printf("%2d %-2s    |", (i- startingDay+1), event); // Display event title
                  else if(event.length() == 2)
                     System.out.printf("%2d %-2s     |", (i- startingDay+1), event); // Display event title
                  else if(event.length() == 1)
                     System.out.printf("%2d %-2s      |", (i- startingDay+1), event); // Display event title
                  ;                  } 
               else {
                  System.out.printf("%8d  |", (i - startingDay + 1));
               }
            }
         } else {
            System.out.printf("%11s", "|");
         }
         if (i % 7 == 0){ // from here start printing pipes for spacing
            for(int k = 0; k < 4; k++){
               System.out.println();
               System.out.print("|");
               for(int j = 0; j < 49; j++){
                  if(j%8 == 0){
                     System.out.printf("%11s", "|");   
                  }
               }
            } 
         } // end of if from printing pipes
      } // end of second for loop
      System.out.println();
   } // End of drawRow
   
   // Receives the int (mm, dd) and displays their values at the bottom of the calendar
   public static void displayDate(int month, int day) {
      System.out.println("Month: " + month );
      System.out.println("Day: " + day); 
      System.out.println();
   } // end of displayDate
   
   // Pulls out the month from the input String and return it's value as an int mm
   public static int monthFromDate(String date) {
      String[] day = date.split("/"); // divide date into [mm/dd] 
      // parseInt convert string to int
      int mm = Integer.parseInt(day[0]); // String [mm] array
      return mm;
   } // end of monthFromDate
   
    // Pulls out the dat from the input String and return it's value as an int dd
   public static int dayFromDate(String date) {
      String[] day = date.split("/"); // divide date into [mm/dd]
      int dd =  Integer.parseInt(day[1]); // String [dd] array(dayd)
      return dd;
   } // end of dayFromDate
   
   // Method for getting and printing current day and month, returns current month
   public static int currentDate() {
      Calendar cal = Calendar.getInstance();
      int currentDay = cal.get(Calendar.DATE);
      int currentMonth = cal.get(Calendar.MONTH);
      System.out.println("Month: " + (currentMonth + 1) );
      System.out.println("Day: " + currentDay );
      return (currentMonth + 1) ;
   } // End of currentMonth
    
    
   //Method to get the starting day of Month, returns starting day for each month
   public static int getStartingDay(int month){
        
      if(month == 2 || month == 3 || month == 11){ 
         return 4;  
      } else if(month == 4 || month == 7){
         return 7;
      } else if(month == 6) {
         return 5;
      } else if(month == 8) {
         return 3;
      } else if(month == 9 || month == 12) {
         return 6;
      } else if(month == 1 || month == 10) {
         return 1;
      } else if (month == 5 ){
         return 2; 
      } else {
         return 0;
      }
   } // End of getStartingDay 
    
   // Method that will display a menu so that user can choose what they want to see and returns the chosen command
   public static String menu(){
      Scanner scan = new Scanner (System.in);
      System.out.println();
      System.out.println("Type \"ev\" to create a new event.");
      System.out.println("Type \"fp\" to print out a month to a text file.");
      System.out.println("Type \"e\" to enter a date and display the corresponding calendar.");
      System.out.println("Type \"t\" to get today's date and display the today's calendar.");
      System.out.println("Type \"n\" to display the next month.");
      System.out.println("Type \"p\" to display the previous month.");
      System.out.println("Type \"q\" to quit the program.");
      System.out.print("Please type a command: ");
      String command = scan.next();
      System.out.println();
      return command.toLowerCase();   // make it lower case if the user types in Upper Case and make it easier to be passed to other method
   } // End of menu

   // Method to take user input File with events
   public static void getData()  throws FileNotFoundException {
      Scanner scan = new Scanner(System.in);
      String inputFileName = "";
      do {
         System.out.print("Input file name? ");
         inputFileName = scan.nextLine();
            // checkIfExists -> false means it doesn't exist, ! makes it true hence runs the loop again
            // checkIfExists -> true means it exists, ! makes it false and stop the loop 
      } while (!checkIfExists(inputFileName)); // keep prompting until the file exists   
      Scanner fileScanner = new Scanner(new File(inputFileName)); // Scanner&File object to read the input file
      while(fileScanner.hasNextLine()){
         String event = fileScanner.nextLine();
         String eventDate = event.substring(0,5); // gets event's date 
         String eventTitle = event.substring(6, 13); // gets event's title
         addEvent(eventDate, eventTitle); 
      }
   }
     
   // This metod will decide what to do based on the type in command from the user and returns date to be updated in main
   public static String doChoice(String choice, String date) throws FileNotFoundException {
      Calendar cal = Calendar.getInstance(); //calendar object
      Scanner scan = new Scanner (System.in);
      switch (choice){
         case "ev":
            String eventInput = "";
            System.out.printf("%22s","New event\n");
            System.out.print("\nPlease type in one event at a time and check the calendar.");
            do{
               System.out.println("\nPlease type in new event[mm/dd (Event_title in atmost 7 characters)] "); 
               eventInput = scan.nextLine();
            }while (!(eventInput.length()> 5)|| !(eventInput.contains(" ")) || eventInput.length()>14); 
              
            String eventDate = eventInput.substring(0,5);
            String eventTitle = eventInput.substring(6);
            addEvent(eventDate, eventTitle);                
            break;    
         case "fp":    
            String outputFileName ="";
            System.out.print("What month would you like to print?(mm): ");
            int monthOut  = scan.nextInt();
            while(monthOut < 1  || monthOut > 12){
               System.out.print("Invalid month. Please enter month to pritnt(mm): ");
               monthOut = scan.nextInt();
            }
            System.out.print("Output file name? ");
            scan.nextLine(); // this because compiler is skipping the input for file name 
            outputFileName = scan.nextLine();
            PrintStream out = new PrintStream(new File(outputFileName)); // Write output to a file 
            System.setOut(out);
            printFigure();
            if (monthOut < 9){
               drawMonth(monthOut, "0"+monthOut+"/01", 0);
            }
            else{
               drawMonth(monthOut, monthOut+"/01", 0);
            }
            out.close(); // closes the PrintStream
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
            break;
         case "e":
            System.out.print("What date would you like to look at? (mm/dd): ");
            date = scan.next(); // this because input is a String 
            int length = date.length();
            while (length != 5 ){ // condition to get the correct date format of 00/00 (5chars)
               System.out.print("Wrong format, please enter date as mm/dd: ");
               date = scan.next();
               length = date.length(); // updates the date length
            } 
            int month = monthFromDate(date);
            int day = dayFromDate(date);
            while (month > 12 || month < 1 || day > 31 || day < 1) { // conditions to get the correct format date from the user
               System.out.print("Invalid date, please enter date as mm/dd: ");
               date = scan.next();
               month = monthFromDate(date);
               day = dayFromDate(date);
            }
            System.out.println(); 
            printFigure();
            drawMonth(month,date, day);
            displayDate(month, day);
            break; // end of case "e"
         case "t":
            int currentDay = cal.get(Calendar.DATE);
            int currentMonth =  cal.get(Calendar.MONTH);
            if(currentMonth <= 9){ // for date format purpose, if month is less than 10, a "0" will be added with the int 
               date = "0" + Integer.toString(currentMonth+1) + "/"+ Integer.toString(currentDay);  
            } else{ // update of date above needed for getting the date as 0 + 7 = 07
               date = Integer.toString(currentMonth+1) + "/" + Integer.toString(currentDay);
            } // one above needs the +"/" bc currentDay not includes "/" as in date.substring(2) 
            printFigure();
            drawMonth(currentMonth+1, date, currentDay);
            currentDate();
            break;
         case "n":
            if (date.isEmpty()){ // if user does not enter "e" or "t" first date will be empty and this will not be executed
               System.out.println("Please enter a valid command.");
            } 
            else{
               currentDay = cal.get(Calendar.DATE);
               month = monthFromDate(date);  // extracts the month from the date
               if (month < 12){
                  if(month < 9){
                     date = "0"+ (month+1)+ date.substring(2);  // ex. month = 8 < 9:."0"+ (8+1)= "09/01"
                  } 
                  else {
                     date = (month+1)+ date.substring(2);
                  }
                  printFigure();
                  drawMonth(month+1, date,0); 
               } 
               else{
                  date = "01" + date.substring(2);
                  printFigure();
                  drawMonth(1, date, 0);
               }
            }
            break;
         case "p":
            if (date.isEmpty()){
               System.out.println("Please enter a valid command.");
            } else { 
               month = monthFromDate(date); 
               day = dayFromDate(date); 
               if (month >= 2){
                  if(month < 10){
                     date = "0" + (month-1)+ date.substring(2); 
                  }else if (month == 10){
                     date = "0" + (month-1)+ date.substring(2);
                  } else { // if month is 
                     date = (month-1)+ date.substring(2);
                  }
                  printFigure();
                  drawMonth(month-1,date, 0);
               } else{
                  date = (12)+ date.substring(2); 
                  printFigure(); 
                  drawMonth(12, date, 0); // if month = 12
               } // end of last else
            } // end of first else
            break;
         case "q":
            System.out.println("Thank you for using calendar program, have a good day! :)");
            break;
      } // end of switch
      return date;
   }  // end of doChoice method

   // Method to get the maximum number of days in each month and returns the day on each month
   public static int daysInMonth(String date) {
      int returnedMonth = monthFromDate(date);
      if (returnedMonth==2) {
         return 28;
      } else if(returnedMonth==4 || returnedMonth==6|| returnedMonth==9|| returnedMonth==11) {
         return 30;
      } else {
         return 31;
      }
   }// end method days in month
   
   // Method to add an event
   public static void addEvent(String date, String eventTitle) {
      int month = monthFromDate(date);
      int day = dayFromDate(date);
   
      if (month >= 1 && month <= 12 && day >= 1 && day <= daysInMonth(date)) {
         eventArray[month - 1][day - 1] = eventTitle;
         System.out.println("Event added: " + eventTitle);
      } else {
         System.out.println("Invalid date for event.");
      }
   }   
  
   // Method to print out different ASCII 
   public static void printFigure() {
      Random random = new Random();
      int choice = random.nextInt(6)+1;  // #  is for number of cases/ figures, start at 1, max = 3
      switch (choice) {
         case 1:
            printTree();
            break;
         case 2:
            printRobot();
            break;
         case 3:
            printBall();
            break;
         case 4:
            printTree();
            break;
         case 5:
            printBall();
            break;
         case 6:
            printRobot();
            break;
      } // end of switch
   } //  end of printFigure

   // Method to print a tree 
   public static void printTree() {
      System.out.println("                                 * ");
      System.out.println("                                * * ");
      System.out.println("                               * * * ");
      System.out.println("                              * * * * ");
      System.out.println("                             * * * * * ");
      System.out.println("                            * * * * * * ");
      System.out.println("                                |  |  ");
      System.out.println("                                |__|  ");
      System.out.println();
   } // end of method to print a tree
   
   // Method to print a robotface
   public static void printRobot() {
      System.out.println("                              _______");
      System.out.println("                             | |_| |_| ");
      System.out.println("                             |    +  |");
      System.out.println("                             |_-_-_-_|");
      System.out.println("                             |_______|");
      System.out.println();
   } // end of method to print robot face
   
   // Method to print a ball
   public static void printBall() {
      System.out.println("                             * * * * * * *");
      System.out.println("                           * * * * * * * * *");
      System.out.println("                           * * * * * * * * *");
      System.out.println("                             * * * * * * *");
      System.out.println();
   } // end of method to print a ball
   
   // Method to check if Inputfile exists.
   public static boolean checkIfExists(String fileName) {
      File inputFile = new File(fileName); // File object creation. Will be used to check if file exists.
      if (!inputFile.exists()) {
         System.out.println("File not found. Try again.");
         return false;
      }
      return true;
   } // end of checkIfExists


}// end of class

