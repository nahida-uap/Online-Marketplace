// Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment.
//
// nschowdh

import java.rmi.Naming;

/**
*  MarketplaceClient - Value in the 'name' variable should be the location
*  of the MarketplaceServer.
*
*/

public class MarketplaceClient {
	public static void main(String args[]){
		// RMI Security Manager
		System.setSecurityManager(new SecurityManager());
		
		try{
			String name = "//10.234.136.55:1986/MarketplaceServer"; 
			// Attempt to locate the MarketplaceServer...
			Marketplace myMarket = (Marketplace) Naming.lookup(name);
			
			MarketplaceClientFrontView view = new MarketplaceClientFrontView();
			MarketplaceClientController c = new MarketplaceClientController (myMarket, view);
			
			//will take the user inputs - id and password
			view.login_registration(c);

		} catch(Exception e){
			System.out.println("MarketplaceClient Exception: " +
			e.getMessage());
			e.printStackTrace();
		}
		
		System.exit(0);
	}
}