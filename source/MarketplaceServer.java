// Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment.
//
// nschowdh

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.lang.reflect.Proxy;
import java.rmi.Naming;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;


/**
 * MarketplaceServer - Must implement any and all methods found in the Marketplace
 * interface. The variable 'name' must include the location where the
 * MarketplaceServer is going to be registered with RMI to run.
 */
public class MarketplaceServer extends UnicastRemoteObject implements Marketplace{
	private String name;
	
	private static Connection c;

	//private MarketplaceServerModel model;
	public MarketplaceServer (String name) throws RemoteException {
		super(); 
		this.name = name;
	}


	/**
	 * Implemented wrapper function for remote method logIN from Marketplace interface.
	 */
	public String[] logIN(String userName, String pass) throws RemoteException { 
		MarketplaceServerModel model = new MarketplaceServerModel();
		return model.logIN(userName, pass, c);
	}

	/**
	 * Implemented wrapper function for remote method register from Marketplace interface.
	 */
	public String[] register(String fName,String lName, String userName, String pass) throws RemoteException { 
		MarketplaceServerModel model = new MarketplaceServerModel();
		return model.register(fName, lName, userName, pass, c);
	}


	/**
	 * Implemented wrapper function for remote method updateItem from Marketplace interface.
	 */
	public ArrayList<String[]> updateItem(String roleType, String userName, String itemID, String type, String description, String price, String quantity) throws RemoteException {
		MarketplaceServerModel model = new MarketplaceServerModel();
		return model.updateItemWrapper(roleType, userName, itemID, type, description, price, quantity, c);
	}

	/**
	 * Implemented wrapper function for remote method removeItem from Marketplace interface.
	 */
	public ArrayList<String[]> removeItem(String roleType, String userName, String itemID) throws RemoteException {
		MarketplaceServerModel model = new MarketplaceServerModel();
		return model.removeItemWrapper(roleType, userName, itemID, c);
	}
	
	/**
	 * Implemented wrapper function for remote method addItem from Marketplace interface.
	 */
	public  ArrayList<String[]> addItem(String roleType, String userName, String itemID, String type, String description, String price, String quantity) throws RemoteException {
		MarketplaceServerModel model = new MarketplaceServerModel();
		//System.out.println(itemID+" - "+type+" - "+description+" - "+price+" - "+quantity);
		return model.addItemWrapper(roleType, userName, itemID, type, description, price, quantity, c);
	}
	
	/**
	 * Implemented wrapper function for remote method purchaseItem from Marketplace interface.
	 */
	public ArrayList<String[]> purchaseItem(String roleType, String userName, String cartID) throws RemoteException {
		MarketplaceServerModel model = new MarketplaceServerModel();
		return model.purchaseItemWrapper(roleType, userName, cartID, c);
	}

	/**
	 * Implemented wrapper function for remote method browseItem from Marketplace interface.
	 */
	public ArrayList<String[]> browseItem() throws RemoteException {
		MarketplaceServerModel model = new MarketplaceServerModel();
		return model.browseItemWrapper(c);
	}

	/**
	 * Implemented wrapper function for remote method browseCart from Marketplace interface.
	 */
	public ArrayList<String[]> browseCart(String roleType, String userName) throws RemoteException{
		MarketplaceServerModel model = new MarketplaceServerModel();
		return model.browseCartWrapper(roleType, userName, c);
	}	
	
		
	public static void main(String args[]) {
		// Set the RMI Security Manager...
		System.setSecurityManager(new SecurityManager());

		/*Database connection established here*/
		MarketplaceDataBaseConnection db = new MarketplaceDataBaseConnection();
		db.dbInit();
		c = db.get_Connection();
		
		try {
			System.out.println("Creating a Marketplace Server!");
			
			// Location of MarketplaceServer
			String name = "//10.234.136.55:1986/MarketplaceServer";

					
			// Create a new instance of a MarketplaceServer.
			Marketplace marketplace = (Marketplace)Proxy.newProxyInstance(Marketplace.class.getClassLoader(),
	                new Class<?>[] {Marketplace.class},
	                new AuthorizationInvocationHandler(new MarketplaceServer ( name)));
			
			System.out.println("MarketplaceServer: binding it to name: " + name);
			
			// Binds the MarketplaceServer to the RMI Service.
			Naming.rebind(name, marketplace);
			
			System.out.println("Marketplace Server Ready!");
		} catch (Exception e){
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}