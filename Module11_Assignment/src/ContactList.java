import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;


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
   
   /** intro() gets the file path and name from the user. 
    * 
    * @param input
    * @return fileName
    * 
    */
   public String intro()
   {
      Scanner input = new Scanner(System.in);
      System.out.println( "Welcome to the contact list.");
      System.out.print( "File path and name: "); //get user input for file path and name
      String fileName = input.next();
      
      return fileName;
   } //end intro()
   
   /** userSelection() lets user select the operation to be executed. 
    * 
    * @param input
    * @return selection
    */
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
   
   /** loadTreeMap() reads a file and loads a TreeMap with the last name (key) and details (value). 
    * 
    * @param contactDetails
    * @param addContact
    * @param record
    * @param strLastName
    * @param lastName
    * @param strFirstName
    * @param strPhoneNum
    * @param strEmail
    * @return person
    */
   public TreeMap loadTreeMap(File file) throws IOException
   {
      String[] contactDetails = new String[3];
      
      TreeMap<String, String[]> person = new TreeMap<String, String[]>();
      
      BufferedReader addContact = null;
      String record = null;
      
      addContact = new BufferedReader( new FileReader( file ) );
      
      while( ( record = addContact.readLine() ) !=  null)
      {
         String strLastName = record.substring(0, 16);
         String lastName = strLastName.trim();
         
         String strFirstName = record.substring(16, 33);
         contactDetails[0] = strFirstName.trim();
         
         String strPhoneNum = record.substring(33, 54);
         contactDetails[1] = strPhoneNum.trim();
         
         String strEmail = record.substring(54, 80);
         contactDetails[2] = strEmail.trim();
         
         person.put(lastName, contactDetails);
      } //end while
      
      return person;
      
   } //end loadTreeMap()
   
   /** add() gets user input for new contact details and adds the new contact to the TreeMap
    * then prints the full TreeMap to a file. 
    * 
    * @param input
    * @param newPerson
    * @param newLastName
    * @param newFirstName
    * @param newPhoneNum
    * @param newEmail
    * @param addNewPerson
    * @param newContactList
    * @param newList
    * @param printList
    */
   public void add( File readFile, TreeMap addNewPerson ) throws IOException
   {
      //get user input for new last name, first name, phone, email
      Scanner input = new Scanner(System.in);
      String[] newPerson = new String[3];
      
      System.out.print( "\nLast Name: ");
      String newLastName = input.next();
      System.out.print( "\nFirst Name: ");
      String newFirstName = input.next();
      newPerson[0] = newFirstName;
      System.out.print( "\nPhone Number: ");
      String newPhoneNum = input.next();
      newPerson[1] = newPhoneNum;
      System.out.print( "\nEmail: ");
      String newEmail = input.next();
      newPerson[2] = newEmail;
      
      addNewPerson.put(newLastName, newPerson);

      //create output file object
      PrintWriter newContactList = new PrintWriter( new BufferedWriter( new FileWriter( readFile, false) ) );
      
      Set<Map.Entry<String, String[]>> newList = addNewPerson.entrySet();
      
      for( Map.Entry<String, String[]> printList : newList )
      {
         //newContactList.printf( "%-15s%-17s%-22s%-26s", printList.getKey(), printList.getValue() );
         //newContactList.println( printList.getKey() + printList.getValue() );
         newContactList.println( addNewPerson );
      }
         
      newContactList.close(); //close output file

   } //end add()
   
   /** delete() gets user input for a contact to delete, removes that key and value from the TreeMap
    * then prints the full TreeMap to a file. 
    * 
    * @param input
    * @param deleteLastName
    * @param deletePerson
    * @param newContactList
    * @param newList
    * @param printList
    */
   public void delete( File readFile, TreeMap deletePerson ) throws IOException
   {
      Scanner input = new Scanner(System.in);
      
      System.out.println( "Delete Contact");
      System.out.print( "\nLast Name: ");
      String deleteLastName = input.next(); //user input for key
      
      deletePerson.remove(deleteLastName); //remove values for selected key

      //create output file object
      PrintWriter newContactList = new PrintWriter( new BufferedWriter( new FileWriter( readFile, false) ) );
      
      Set<Map.Entry<String, String[]>> newList = deletePerson.entrySet();
      
      for( Map.Entry<String, String[]> printList : newList )
      {
         //newContactList.printf( "%-15s%-17s%-22s%-26s", printList.getKey(), printList.getValue() );
         newContactList.println( printList.getKey() + printList.getValue() );
      }
         
      newContactList.close(); //close output file
   } //end delete()
   
   /** display() displays the file contents. 
    * 
    * @param readFile
    * @param displayList
    * @param record
    */
   public void display(File readFile) throws IOException
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
   } //end display()
   
   /** main() has a switch statement to call the appropriate method based on the user selection. 
    * 
    * @param readFile
    * @param displayList
    * @param record
    */
   public static void main(String[] args) throws IOException 
   {
      ContactList list = new ContactList();

      File file = new File( list.intro() ); //create new File object
      
      switch( list.userSelection() )
      {
         case 1:
            TreeMap addPerson = list.loadTreeMap( file );
            list.add( file, addPerson );
            break;
         case 2:
            TreeMap deletePerson = list.loadTreeMap( file );
            list.delete( file, deletePerson );
            break;
         case 3:
            list.display( file );
            break;
         default:
            System.out.println( "Invalid response.");
      }      
 
      //fileOutput.close();
      
   } //end main()
   
} //end ContactList
