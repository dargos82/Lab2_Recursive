import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;


/** class ContactList opens a local file, can add/delete data to that file, and print
 * the contenst of the list . 
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
   } // end enum Option

   /** main()   
    *  Run the ContactList class.
    */
   public static void main(String[] args) throws IOException 
   {
      ContactList list = new ContactList();
      
      list.updateContactMap();
           
   } //end main()
   
   /** updateContactMap()
    * Contains the main loop for the ContactList class. Opens a local text file and 
    * prompt the user to Display, Add, or Delete entries from the list. 
    * @throws IOException
    */
   public void updateContactMap() throws IOException
   {
      if(contactFileName == null)
      {
         contactFileName = getFileName();
      }
      loadContactMap(this.contactFileName); //read file and load TreeMap with contact details
      
      Options selection = Options.DISPLAY;
      
      while (selection != Options.QUIT)
      {
         switch( userSelection() )
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
               updateMapFile();
               return;
            default:
               System.out.println( "Invalid response.");
         }
      }
      
        //call to updateMapFile() to write details to file
      
   } //end updateContactMap()
   
   
   /** getFileName() gets the file path and name from the user. 
    * 
    * @return <String>: 
    */
   public String getFileName()
   {
      
      System.out.println( "Welcome to the contact list.");
      System.out.print( "File path and name: "); //get user input for file path and name
      String fileName = input.nextLine();

      return fileName;
   } //end intro()
   
   /** userSelection() lets user select the operation to be executed. 
    * 
    * @return <Options> 
    */
   public Options userSelection() //user input to add, delete, or display contacts
   {
      
      System.out.println( "\n1: Add contact" );
      System.out.println( "2: Delete contact" );
      System.out.println( "3: Display contact list" );
      System.out.println( "4: Close Program." );
      System.out.print( "Selection: ");
      int selection = input.nextInt();
      input.nextLine();
      
      switch( selection )
      {
      case 1:
         return Options.ADD;
      case 2:
         return Options.DELETE;
      case 3:
         return Options.DISPLAY;
      case 4:
         return Options.QUIT;
      default: 
         System.out.println( "\nUnrecognized Input, Terminating" );
         return Options.QUIT;
      }  
      
   } //end userSelection()
   
   /** loadContactMap(String)
    * Takes a String of a local path and attempts to open a file at that location 
    * using FileReader. Reads each line and creates a Contact based to load into 
    * the ContactList TreeMap.
    * 
    * @param <String> filePath
    * @throws IOException
    */
   public void loadContactMap(String filePath) throws IOException
   {
      BufferedReader addContact = null;
      String record = null;
      
      addContact = new BufferedReader( new FileReader( filePath ) );
      
      
      while( ( record = addContact.readLine() ) !=  null)
      {
         String strLastName = record.substring(0, 15);
         String lastName = strLastName.trim();
         
         String strFirstName = record.substring(15, 32);
         String firstName = strFirstName.trim();
         
         String strPhoneNum = record.substring(32, 51);
         String phoneNumber = strPhoneNum.trim();
         
         String strEmail = record.substring(51, record.length());
         String email = strEmail.trim();
         
         Contact contact = new Contact(lastName, firstName, phoneNumber, email);
         
         this.contactMap.put(lastName, contact);
         
      } //end while
      
      addContact.close();
      
   } //end loadContactMap
   
   /** add() gets user input for new contact details and adds the new contact to
    *  the TreeMap. 
    */
   public void add()
   {
      //get user input for new last name, first name, phone, email
      
      System.out.print( "\nLast Name: ");
      String lastName = input.nextLine();
      System.out.print( "\nFirst Name: ");
      String firstName = input.nextLine();
      System.out.print( "\nPhone Number: ");
      String phoneNumber = input.nextLine();
      System.out.print( "\nEmail: ");
      String email = input.nextLine();
      
      Contact contact = new Contact(lastName, firstName, phoneNumber, email);
      
      this.contactMap.put(lastName, contact);

   } //end add()
   
   /** delete() gets user input for a contact to delete, removes that key and value
    *  from the TreeMap.
    */
   public void delete()
   {      
      int size = this.contactMap.size();
      System.out.print( "Last Name of Contact to delete (case sensitive): ");
      String lastName = input.nextLine(); //user input for key
      Contact removed = this.contactMap.remove(lastName); //remove values for selected key
      if(removed == null)
      {
         System.out.print( "Could not find entry: " + lastName);
      }

   } //end delete()

   /** display() displays the TreeMap contents. 
    * 
    */
   public void display()
   {
      for(Map.Entry<String, Contact> lookup : contactMap.entrySet())
      {
         System.out.println( lookup.getValue().toString() ); //print data from file
      }            
   } //end display()
   
   /** updateMapFile()
    *  Writes the TreeMap to the file at the location stored in contactFileName.
    */
   public void updateMapFile()
   {
      PrintWriter writer;
      try
      {
         writer = new PrintWriter( new BufferedWriter( new FileWriter( this.contactFileName, false) ) );
                  
         for( Map.Entry<String, Contact> printMap : this.contactMap.entrySet())
         {  
            writer.printf( "%-15s%-17s%-22s%-26s\n", printMap.getValue().getLastName(), printMap.getValue().getFirstName(),
                                    printMap.getValue().getPhoneNumber(), printMap.getValue().getEmail() );
         }
            
         writer.close(); //close output file
         
      } catch (IOException e)
      {

         e.printStackTrace();
      }

   } //end updateMapFile()

   private Scanner input = new Scanner(System.in);
   private TreeMap<String, Contact> contactMap = new TreeMap<String, Contact>();
   private String contactFileName = null;
   
} //end ContactList
