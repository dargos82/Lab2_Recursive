import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


/** class ContactList opens a file, and can add/delete data to that file, and print
 * the file. 
 * 
 * @authors George Haralampopulos and David Blossom
 */
public class ContactList 
{
   public enum options
   {
      ADD,
      DELETE,
      DISPLAY
   }
   
   public String intro()
   {
      Scanner input = new Scanner(System.in);
      System.out.println( "Welcome to the contact list.");
      System.out.print( "File path and name: "); //get user input for file path and name
      String fileName = input.next();
      
      return fileName;
   } //end intro()
   
   public int userSelection() //user input to add, delete, or display contacts
   {
      Scanner input = new Scanner(System.in);
      
      System.out.println( "\n1: Add contact" );
      System.out.println( "2: Delete contact" );
      System.out.println( "3: Display contact list" );
      System.out.print( "Selection: ");
      int selection = input.nextInt();
      
      return selection;
   } //end userSelection()
   
   public void add()
   {
      
   }
   
   public void delete()
   {
      
   }
   
   public void display(String readFile) throws IOException
   {
      BufferedReader displayList = null;
      String record = null;
      
      try
      {
         displayList = new BufferedReader( new FileReader( readFile ) );
         
         while( ( record = displayList.readLine() ) !=  null)
            System.out.println( record ); //print data from file
         
      } catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }
   }
   

   public static void main(String[] args) throws IOException 
   {
      ContactList list = new ContactList();
      String storedFile = list.intro();
      
      
      File file = new File( storedFile ); //create new File object
      
      switch( list.userSelection() )
      {
         case 1:
            list.add();
         case 2:
            list.delete();
         case 3:
            list.display( storedFile ); 
         //default:
            //System.out.println( "Invalid response.");
      }      
      
      //fileOutput.close();
      
   } //end main()
} //end ContactList
