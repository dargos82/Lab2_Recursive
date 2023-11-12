
/** Contact()
 *  A container class to store properties about a phone contact such as 
 *  Last Name, First Name, Email, and Phone Number.
 */
public class Contact
{
   /** Contact(String, String, String, String)
    *  Constructor to create a full Contact with the supplied parameters.
    * @param lastName
    * @param firstName
    * @param phoneNumber
    * @param email
    */
   Contact( String lastName, String firstName, String phoneNumber, String email )
   {
      this.lastName = lastName;
      this.firstName = firstName;
      this.phoneNumber = phoneNumber;
      this.email = email;
   }
   
   public String getLastName()
   {
      return lastName;
   }

   public void setLastName(String lastName)
   {
      this.lastName = lastName;
   }
   
   public String getFirstName()
   {
      return firstName;
   }
   public void setFirstName(String firstName)
   {
      this.firstName = firstName;
   }
   public String getPhoneNumber()
   {
      return phoneNumber;
   }
   public void setPhoneNumber(String phoneNumber)
   {
      this.phoneNumber = phoneNumber;
   }
   public String getEmail()
   {
      return email;
   }
   public void setEmail(String email)
   {
      this.email = email;
   }
   
   /** toString()
    *  Helper function to correctly format the Contact member variable information.
    * 
    * @return String
    */
   public String toString()
   {
      return this.getLastName() + addTab(this.getLastName()) + 
             this.getFirstName() + addTab(this.getFirstName()) +
             this.getPhoneNumber() + addTab(this.getPhoneNumber()) + 
             this.getEmail();
   }
   
   /** addTab(String
    *  Change the tab ordering based on length of supplied string. Larger strings
    *  only get two tabs instead of three.  
    * @param lastString
    * @return
    */
   private String addTab(String lastString)
   {
      if(lastString.length() > 9)
      {
         return "\t\t";
      }
      
      return "\t\t\t";
   }
   
   private String lastName;
   private String firstName;
   private String phoneNumber;
   private String email;
   
}
