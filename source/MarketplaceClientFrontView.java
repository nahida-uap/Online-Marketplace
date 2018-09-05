// Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment.
//
// nschowdh

// View Layer

import java.util.Scanner;

public class MarketplaceClientFrontView {
	private Scanner sc=new Scanner(System.in);
 
	private String userName =null, pass = null, firstName = null, lastName = null;
	
	//return the user provided id
	public String get_userName ()
	{
		return userName ;
	}
	
	//return the user provided password
	public String get_pass ()
	{
		return pass;
	}

	//return the user provided first name
	public String get_firstName ()
	{
		return firstName;
	}

	//return the user provided last name
	public String get_lastName ()
	{
		return lastName;
	}
	
	/* using scanner it take the inputs (id and password) from user
	 * and then trigger to controller to pass the value to the model end
	*/
	public void login_registration (MarketplaceClientController c)
	{	
		System.out.print("Press (1 or 2)\n1. Login \n2. Register (New User)oice: ");
		String choice = sc.next();
		if (choice.equals("1")){
			login();
			c.logIncontroller();
		}
		else{
			register();			
			c.logIncontroller();
		}
	}
	/* method that use to take login credentials from user (customer/admin) 
	 */
	public void login()
	{
		try{
			System.out.println("Enter user name : ");
			userName = sc.next();
			System.out.println("Enter password : ");
			pass = sc.next();	
		} catch(Exception e){
			System.out.println("MarketplaceClientView Login Exception: " +
			e.getMessage());
			e.printStackTrace();
		}
	}

	/* method that use to take inputs from user to register new user in the system 
	 * and after successfull registration it will automatically login to the system
	 */
	public void register()
	{
		try{
			System.out.println("Enter user first name : ");
			firstName = sc.next();
			System.out.println("Enter user last name : ");
			lastName = sc.next();
			System.out.println("Enter user name : ");
			userName = sc.next();
			System.out.println("Enter password : ");
			pass = sc.next();

		} catch(Exception e){
			System.out.println("MarketplaceClientView Register New User Exception: " +
			e.getMessage());
			e.printStackTrace();
		}
	}
}