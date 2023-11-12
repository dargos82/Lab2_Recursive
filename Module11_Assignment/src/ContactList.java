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
   public enum Options
   {
      ADD,
      DELETE,
      DISPLAY,
      QUIT
   }

   public ContactList() //constructor for ContactList object
   {
      this.contactFileName = getFileName();
      Scanner scanner = new Scanner( this.contactFileName );
      
   }
   
   /** main() has a switch statement to call the appropriate method based on the user selection. 
    * 
    * @param readFile
    * @param displayList
    * @param record
    */
   public static void main(String[] args) throws IOException 
   {
      ContactList list = new ContactList();
      
      list.updateContactMap();
           
   } //end main()
   
   public void updateContactMap() throws IOException
   {
      loadContactMap( new File ( getFileName() ) ); //read file and load TreeMap with contact details
      
      Options selection = userSelection();
      
      while (selection != Options.QUIT)
      {
         switch( selection )
         {
            case ADD:
               add();
               break;
            case DELETE:
               delete();
               break;
            case DISPLAY:
               display();
               break;
            case QUIT:
               //list.display( file );
               break;
            default:
               System.out.println( "Invalid response.");
         }
      }
      
      updateMapFile();  //call to updateMapFile() to write details to file
      
   } //end updateContactMap()
   
   
   /** intro() gets the file path and name from the user. 
    * 
    * @param input
    * @return fileName
    * 
    */
   public String getFileName()
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
   public Options userSelection() //user input to add, delete, or display contacts
   {
      Scanner input = new Scanner(System.in);
      Options option = Options.QUIT;
      
      System.out.println( "\n1: Add contact" );
      System.out.println( "2: Delete contact" );
      System.out.println( "3: Display contact list" );
      System.out.print( "Selection: ");
      int selection = input.nextInt();
      
      switch( selection )
      {
      case 1:
         option = Options.ADD;
      case 2:
         option = Options.DELETE;
      case 3:
         option = Options.DISPLAY;
      case 4:
         option = Options.QUIT;
      }  
      
      input.close();
      
      return option;
      
   } //end userSelection()
   
   /** loadTreeMap() reads a file and loads a TreeMap with the last name (key) and details (value). 
    * @param firstName 
    * @param phoneNumber 
    * @param email 
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
   public void loadContactMap(File file) throws IOException
   {
      BufferedReader addContact = null;
      String record = null;
      
      addContact = new BufferedReader( new FileReader( file ) );
      
      while( ( record = addContact.readLine() ) !=  null)
      {
         String strLastName = record.substring(0, 16);
         String lastName = strLastName.trim();
         
         String strFirstName = record.substring(16, 33);
         String firstName = strFirstName.trim();
         
         String strPhoneNum = record.substring(33, 54);
         String phoneNumber = strPhoneNum.trim();
         
         String strEmail = record.substring(54, 80);
         String email = strEmail.trim();
         
         Contact contact = new Contact(lastName, firstName, phoneNumber, email);
         
         this.contactMap.put(lastName, contact);
         
      } //end while
      
      addContact.close();
      
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
    * @param firstName 
    * @param phoneNumber 
    * @param email 
    * @param newContactList
    * @param newList
    * @param printList
    */
   public void add()
   {
      //get user input for new last name, first name, phone, email
      Scanner input = new Scanner(System.in);
      
      System.out.print( "\nLast Name: ");
      String lastName = input.next();
      System.out.print( "\nFirst Name: ");
      String firstName = input.next();
      System.out.print( "\nPhone Number: ");
      String phoneNumber = input.next();
      System.out.print( "\nEmail: ");
      String email = input.next();
      
      Contact contact = new Contact(lastName, firstName, phoneNumber, email);
      
      this.contactMap.put(lastName, contact);

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
   public void delete()
   {
      Scanner input = new Scanner(System.in);
      
      System.out.print( "Last Name of Contact to delete: ");
      String lastName = input.next(); //user input for key
      
      this.contactMap.remove(lastName); //remove values for selected key

   } //end delete()

   /** display() displays the file contents. 
    * 
    * @param readFile
    * @param displayList
    * @param record
    * @throws IOException 
    */
   public void display() throws IOException
   {
      BufferedReader displayList = null;
      String record = null;
      
      try
      {
         displayList = new BufferedReader( new FileReader( this.contactFileName ) );
         
         while( ( record = displayList.readLine() ) !=  null)
            System.out.println( record ); //print data from file
         
      } catch (FileNotFoundException e)
      {
         e.printStackTrace();
      }
   } //end display()
   
   public void updateMapFile()
   {
      PrintWriter writer;
      try
      {
         writer = new PrintWriter( new BufferedWriter( new FileWriter( this.contactFileName, false) ) );
         
         Set<Map.Entry<String, Contact>> newMap = this.contactMap.entrySet();
         
         for( Map.Entry<String, Contact> printMap : newMap )
         {  
            writer.printf( "%-15s%-17s%-22s%-26s", printMap.getValue().getLastName(), printMap.getValue().getFirstName(),
                                    printMap.getValue().getPhoneNumber(), printMap.getValue().getEmail() );
         }
            
         writer.close(); //close output file
         
      } catch (IOException e)
      {

         e.printStackTrace();
      }

   } //end updateMapFile()

   private Scanner scanner;
   private TreeMap<String, Contact> contactMap = new TreeMap<String, Contact>();
   private String contactFileName = null;
   
} //end ContactList
