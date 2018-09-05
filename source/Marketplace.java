// Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment.
//
// nschowdh

import java.util.ArrayList;

/**
 * This interface serves as the proxy between the MarketplaceServer and the
 * MarketplaceClient. The MarketplaceServer must implement this method.
 *
 */
public interface Marketplace extends java.rmi.Remote {
	/**
	 * @return the notification(Success/Invalid) and role regarding login attempt 
	 * @throws java.rmi.RemoteException
	 */
	public String[] logIN(String userName,String pass) throws java.rmi.RemoteException;


	/**
	 * @return the notification(Success/already exists)  
	 * @throws java.rmi.RemoteException
	 */
	public String[] register(String fName,String lName, String userName, String pass) throws java.rmi.RemoteException;
	

	/**
	 * @return the inventory list
	 * @throws java.rmi.RemoteException
	 */

	public ArrayList<String[]> browseItem() throws java.rmi.RemoteException;
	
	/**
	 * @return the updated inventory list once the purchase operation is successfull
	 * @throws java.rmi.RemoteException
	 */
	@RequiresRole("customer")
	public ArrayList<String[]> purchaseItem(String roleType, String userName, String cartID) throws java.rmi.RemoteException;


	/**
	 * @return the corresponding cart list to the customer
	 * @throws java.rmi.RemoteException
	 */
	@RequiresRole("customer")
	public ArrayList<String[]> browseCart(String roleType, String userName) throws java.rmi.RemoteException;	


	/**
	 * @return the updated inventory list once the add item operation is successfull
	 * for admin role will be able to add new item in the inventory
	 * for customer role will be able to add new item in their corresponding shopping cart
	 * @throws java.rmi.RemoteException
	 */
	public ArrayList<String[]> addItem(String roleType, String userName, String itemID, String type, String description, String price, String quantity) throws java.rmi.RemoteException;

	
	@RequiresRole("admin")
	public ArrayList<String[]> updateItem(String roleType, String userName, String itemID, String type, String description, String price, String quantity) throws java.rmi.RemoteException;


	@RequiresRole("admin")
	public ArrayList<String[]> removeItem(String roleType, String userName, String itemID) throws java.rmi.RemoteException;




}

