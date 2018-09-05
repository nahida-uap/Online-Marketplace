// Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment.
//
// nschowdh

import java.io.BufferedReader; 
import java.io.File; 
import java.io.FileReader; 
import java.io.IOException; 
import java.util.ArrayList;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * MarketplaceServerModel - Must implement any and all methods found in the Marketplace
 * interface. The variable 'name' must include the location where the
 * MarketplaceServer is going to be registered with RMI to run.
 */
public class MarketplaceServerModel{

	//private static Connection conn;

	/*DB connection
	public void dbInitialization(){
		// Sample Database Connection
		String hostname = "localhost:3306";
		String dbName = "nschowdh_db";
		
		String url = "jdbc:mysql://" + hostname + "/" + dbName;
		String username = "nschowdh";
		String password = "nschowdh";

		System.out.println("Connecting database...");
		
		try {
			conn = (Connection) DriverManager.getConnection(url, username, password);
				
		    System.out.println("Database connected!");
		} catch (SQLException e) {
		    throw new IllegalStateException("Cannot connect the database!", e);
		}
	}*/
	
	private static int clientId = 0;  	
	
	/**
	 * Implemented wrapper function for remote method logIN from Marketplace interface.
	 */
	public String[] logIN(String userName, String pass, Connection conn) { 
		System.out.println(userName+" send request for login!!! ");
		//dbInitialization();
		clientId++;
		String[] session = new String[3];
		
		Statement stmt = null;
		ResultSet rs = null, rs2 = null;
		//try {
			//Thread.sleep(10000);

		if(conn != null) {
			try {
				stmt = (Statement) conn.createStatement();
				try {
					/*check if the user entry in Customer table or not to decide the user role*/
					synchronized(this) {
						rs = stmt.executeQuery( "SELECT customerID, userName, password FROM customer where userName = '"+userName+"' and password = '"+pass+"'" );
						if (!rs.next() ) { 
							System.out.println("no data in customer table!!!");
							/*check if the user entry in Admin table or not to decide the user role*/
							
							rs2 = stmt.executeQuery( "SELECT adminID, userName, password FROM admin where userName = '"+userName+"' and password = '"+pass+"'" );
							if (!rs2.next() ) { 
								session[0] = null;
								session[1] = "Invalid ID or password!!!";
								session[2] = null;
							}
							else{
								session[0] = "admin";
								session[1] = "Login Successfull!!!";
								int temp = rs2.getInt("adminID");
								session[2] = Integer.toString(temp);	
							}
						}
						else{
							session[0] = "customer";
							session[1] = "Login Successfull!!!";
							int temp = rs.getInt("customerID");
							session[2] = Integer.toString(temp);					
						}
					} //sync statement - critical section
				} catch (SQLException e) {
				System.err.println("Login: Unable execute query!");
				e.printStackTrace();
				}		
			stmt.close();
			} catch (SQLException e1) {
				System.err.println("Login: Unable to create SQL statement!");
				e1.printStackTrace();
			}
		}//if(conn != null)
		//} catch (InterruptedException e) {                
	    // }
		System.out.println(userName+" login verification completed!!! ");
		return session;
	}
	/**
	 * Implemented wrapper function for remote method register from Marketplace interface.
	 */
	public String[] register(String fName,String lName, String userName, String pass, Connection conn){
		//dbInitialization();
		Statement stmt = null;
		ResultSet rs = null;
		String[] session = new String[3];
		//try {
			//Thread.sleep(10000);

		if(conn != null) {
			try {
				stmt = (Statement) conn.createStatement();
				try {
				/*New user provided info put into customer table*/
				synchronized(this) {
					stmt.executeUpdate("INSERT INTO customer (firstName, lastName, userName, password)  "+"VALUES('"+fName+"', '"+lName+"', '"+userName+"', '"+pass+"')");
				}
				session[0] = "customer";
				session[1] = "Login Successfull!!!";
				int temp = clientId;
				session[2] = Integer.toString(temp);
				}catch (SQLException e) {
				System.err.println("Register: Registration Not complete!!!");
				e.printStackTrace();
				}		
				stmt.close();
			} catch (SQLException e1) {
			System.err.println("Register: Unable to create SQL statement to register new user!");
			e1.printStackTrace();
			}
		}
		//} catch (InterruptedException e) {                
	     //}
		System.out.println(userName+" Registration completed!!! ");
		return session;
	}

	/**
	 * Implemented wrapper function for remote method browseItem from Marketplace interface.
	 */
	public ArrayList<String[]> browseItemWrapper(Connection conn) {
		//System.out.println(id+" starts browse items: ");


		ArrayList<String[]> inventory = new ArrayList<String[]>();	

		Statement stmt = null;
		ResultSet rs = null;
		//try {
			//Thread.sleep(10000);

		if(conn != null) {
			try {
				stmt = (Statement) conn.createStatement();
				try {
					/*Fetch all the datas from item table*/
					synchronized(this) {
						rs = stmt.executeQuery( "SELECT * FROM items" );
					
						while (rs.next())  {
							int id = rs.getInt("itemID");
							String type = rs.getString("type");
							String desc = rs.getString("description");
							int price = rs.getInt("price");
							int quantity = rs.getInt("quantity");
							inventory.add(new String[]{Integer.toString(id), type, desc, Integer.toString(price), Integer.toString(quantity)});
						}
					} //sync statement - critical section
				} catch (SQLException e) {
				System.err.println("BrowseItem: Unable execute query!");
				e.printStackTrace();
				}		
			stmt.close();
				
			} catch (SQLException e1) {
				System.err.println("BrowseItem: Unable to create SQL statement for browse items!");
				e1.printStackTrace();
			}
		}
		//} catch (InterruptedException e) {                
	      //  }
		//System.out.println(id+" finish brwose items here");
		return inventory;
	}
	
	/**
	 * Implemented wrapper function for remote method browseCart from Marketplace interface.
	 */
	public ArrayList<String[]> browseCartWrapper(String roleType, String userName, Connection conn) {
		ArrayList<String[]> cart = new ArrayList<String[]>();
		Statement stmt = null;
		ResultSet rs = null;
		//try {
			//Thread.sleep(10000);

		if(conn != null) {
			try {
				int customerId =0;
				stmt = (Statement) conn.createStatement();
				try {
					synchronized(this) {
						rs = stmt.executeQuery( "SELECT customerID FROM customer where userName ='"+userName+"'");
					
						while (rs.next())  {
							customerId = rs.getInt("customerID");
						}
					
					/*Fetch all the datas from Cart*/
					
						rs = stmt.executeQuery( "SELECT * FROM cart where customerID ="+customerId);
						while (rs.next())  {
							cart.add(new String[]{Integer.toString(rs.getInt("cartID")),Integer.toString(rs.getInt("customerID")), Integer.toString(rs.getInt("itemID")), Integer.toString(rs.getInt("quantity"))});					
						}
					} //sync statement - critical section
				} catch (SQLException e) {
				System.err.println("BrowseCart: Unable execute query to display cart!");
				e.printStackTrace();
				}		
			stmt.close();
				
			} catch (SQLException e1) {
				System.err.println("BrowseCart: Unable to create SQL statement!");
				e1.printStackTrace();
			}
		}
		//} catch (InterruptedException e) {                
	     //}
		return cart;	
	}

	/**
	 * Implemented wrapper function for remote method addItem from Marketplace interface.
	 */
	public ArrayList<String[]> addItemWrapper(String roleType, String userName, String itemId, String type, String description, String price, String quantity, Connection conn) {		
		ArrayList<String[]> inventory = new ArrayList<String[]>();	
		System.out.println(userName+" starts add items: ");
			
		Statement stmt = null;
		ResultSet rs = null, rs2 = null;
		//try {
			//Thread.sleep(10000);

		if(conn != null) {
			try {
				stmt = (Statement) conn.createStatement();
				try {
					String type_cast = type;
					String desc_cast = description;
					int price_cast = Integer.parseInt(price);
					int quantity_cast = Integer.parseInt(quantity);

					/*Admin: add new items to Inventory*/
					if(roleType.equals("admin")){
						synchronized(this) {
							stmt.executeUpdate("INSERT INTO items (type, description, price, quantity) "+"VALUES ('"+type_cast+"', '"+desc_cast+"','"+price_cast+"', '"+quantity_cast+"')");
							rs = stmt.executeQuery( "SELECT * FROM items" );
							while (rs.next())  {
								inventory.add(new String[]{Integer.toString(rs.getInt("itemID")),rs.getString("type"), rs.getString("description"), Integer.toString(rs.getInt("price")), Integer.toString(rs.getInt("quantity"))});					
							}
						}//synch statement - critical section
					}//addItem by admin
					
					/*Customer: add items to Cart*/
					if(roleType.equals("customer")){
						int avail = 0, customerId = 0;
						/*Step 1: quantity check*/					
						synchronized(this) {
							rs = stmt.executeQuery( "SELECT quantity FROM items where itemID = "+itemId);
							while (rs.next())  {
								avail = rs.getInt("quantity");
							}
						
							//System.out.println("Available amount = "+avail);
							rs2 = stmt.executeQuery( "SELECT customerID FROM customer where userName ='"+userName+"'");
							while (rs2.next())  {
								customerId = rs2.getInt("customerID");
							}
							/*Step 2: Add to shopping cart*/
							if((avail - quantity_cast) >= 0){
								stmt.executeUpdate("INSERT INTO cart (customerID, itemID, quantity) "+"VALUES ('"+customerId+"', '"+itemId+"', '"+quantity_cast+"')");
							} //if items are available
						
							/*Display cart items to the user*/
							rs2 = stmt.executeQuery( "SELECT * FROM cart where customerID ="+customerId);
						
							while (rs2.next())  {
								inventory.add(new String[]{Integer.toString(rs2.getInt("cartID")),Integer.toString(rs2.getInt("customerID")), Integer.toString(rs2.getInt("itemID")), Integer.toString(rs2.getInt("quantity"))});					
							}
						} //sync statement - critical section
					}//addItem to shopping cart by customer
				} catch (SQLException e) {
				System.err.println("AddItem: Unable execute query to add item in inventory!");
				e.printStackTrace();
				}		
			stmt.close();
				
			} catch (SQLException e1) {
				System.err.println("AddItem: Unable to create SQL statement!");
				e1.printStackTrace();
			}
		}	
		//} catch (InterruptedException e) {                
	     //}
		System.out.println(userName+" finish add items here");
		return inventory;
	}

	/**
	 * Implemented wrapper function for remote method updateItem from Marketplace interface.
	 */
	public ArrayList<String[]> updateItemWrapper(String roleType, String userName, String itemID, String type, String description, String price, String quantity, Connection conn) {		
		ArrayList<String[]> inventory = new ArrayList<String[]>();	
		System.out.println(userName+" starts update items: ");
		//try {
			//Thread.sleep(10000);

		int itemId = Integer.parseInt(itemID);
		String type_cast = type;
		String desc_cast = description;
		int price_cast = Integer.parseInt(price);
		int quantity_cast = Integer.parseInt(quantity);

		Statement stmt = null;
		ResultSet rs = null;

		if(conn != null) {
			try {
				stmt = (Statement) conn.createStatement();
				try {
					/*For update Item user need  to provide all the coresponding fields of an Item as input*/
					synchronized(this) {
						stmt.executeUpdate("UPDATE items SET type = '"+type_cast+"', description = '"+desc_cast+"',  price = '"+price_cast+"', quantity = '"+quantity+"' where itemID = '"+itemId+"'");
						rs = stmt.executeQuery( "SELECT * FROM items where itemID = "+itemId+"" );
									
						while (rs.next())  {
							inventory.add(new String[]{Integer.toString(rs.getInt("itemID")),rs.getString("type"), rs.getString("description"), Integer.toString(rs.getInt("price")), Integer.toString(rs.getInt("quantity"))});					
						}
					}//synch statement - critical section
				} catch (SQLException e) {
				System.err.println("UpdateItem: Unable execute query to update item in inventory!");
				e.printStackTrace();
				}		
			stmt.close();
				
			} catch (SQLException e1) {
				System.err.println("UpdateItem: Unable to create SQL statement!");
				e1.printStackTrace();
			}
		}
				
		//} catch (InterruptedException e) {                
	      //  }
		System.out.println(userName+" finish update items here");
		return inventory;
	}


	/**
	 * Implemented wrapper function for remote method removeItem from Marketplace interface.
	 */
	public  ArrayList<String[]> removeItemWrapper(String roleType, String userName, String itemID, Connection conn) {		
		ArrayList<String[]> inventory = new ArrayList<String[]>();	
		System.out.println(userName+" starts remove items: ");
		//try {
			//Thread.sleep(10000);

		int itemId = Integer.parseInt(itemID);
		
		Statement stmt = null;
		ResultSet rs = null;

		if(conn != null) {
			try {
				stmt = (Statement) conn.createStatement();
				try {
					synchronized(this) {
						stmt.executeUpdate("DELETE FROM items where itemID = '"+itemId+"'");
						rs = stmt.executeQuery( "SELECT * FROM items" );
					
						while (rs.next())  {
							inventory.add(new String[]{Integer.toString(rs.getInt("itemID")),rs.getString("type"), rs.getString("description"), Integer.toString(rs.getInt("price")), Integer.toString(rs.getInt("quantity"))});					
						}
					}//synch statement - critical section
				} catch (SQLException e) {
				System.err.println("RemoveItem: Unable execute query to add item in inventory!");
				e.printStackTrace();
				}		
			stmt.close();
				
			} catch (SQLException e1) {
				System.err.println("RemoveItem: Unable to create SQL statement!");
				e1.printStackTrace();
			}
		}		
		//} catch (InterruptedException e) {                
	      //  }
		System.out.println(userName+" finish remove items here");
		return inventory;
	}


	/**
	 * Implemented wrapper function for remote method purchaseItem from Marketplace interface.
	 */
	public synchronized ArrayList<String[]> purchaseItemWrapper(String roleType, String userName, String cartID, Connection conn) {

		ArrayList<String[]> cart = new ArrayList<String[]>();

		System.out.println(userName+" starts purchase items: ");
		//try {
			//Thread.sleep(10000);
			
			int cartId = Integer.parseInt(cartID);
	
			Statement stmt = null;
			ResultSet rs = null;

			if(conn != null) {
				try {
					stmt = (Statement) conn.createStatement();
					try {
						int avail = 0, requested =0, itemId = 0, customerId =0;
						
						/*Step: quantity availability check; if requested quantity is available or not; 
						 * if requested quantity is not available then purchase will proceed otherwise 
						 * purchase will be successfull and will return the rest of the cart items to the customer
						 */					
						synchronized(this) {
							rs = stmt.executeQuery( "SELECT customerID, itemID, quantity FROM cart where cartID = "+cartId);
						
							while (rs.next())  {
								customerId = rs.getInt("customerID");
								itemId = rs.getInt("itemID");
								requested = rs.getInt("quantity");
							}
							rs = stmt.executeQuery( "SELECT quantity FROM items where itemID = "+itemId);
							while (rs.next())  {
								avail = rs.getInt("quantity");
							}
							if((avail - requested) >= 0){
								stmt.executeUpdate("DELETE FROM cart where cartID = "+cartId);
								stmt.executeUpdate("UPDATE items SET quantity = quantity - '"+requested+"' where itemID = "+itemId);
								rs = stmt.executeQuery( "SELECT * FROM cart where customerID = "+customerId);
								while (rs.next())  {
									cart.add(new String[]{Integer.toString(rs.getInt("cartID")),Integer.toString(rs.getInt("customerID")), Integer.toString(rs.getInt("itemID")), Integer.toString(rs.getInt("quantity"))});					
								}
							} //if items are available
						}//synch statement - critical section
						
					} catch (SQLException e) {
					System.err.println("PurchaseItem: Unable execute query!");
					e.printStackTrace();
					}		
				stmt.close();
				
				} catch (SQLException e1) {
				System.err.println("PurchaseItem: Unable to create SQL statement!");
				e1.printStackTrace();
				}
			}		
		//} catch (InterruptedException e) {                
	      //  }
		System.out.println(userName+" finish purchase items here");
		return cart;
	}
}