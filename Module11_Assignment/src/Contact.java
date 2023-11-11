
public class Contact
{
   Contact( String firstName, String phoneNumber, String email )
   {
      this.firstName = firstName;
      this.phoneNumber = phoneNumber;
      this.email = email;
   }
   
   Contact()
   {
      
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

   private String firstName;
   private String phoneNumber;
   private String email;
   
}
