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
import java.util.Scanner;

//Client Controller Layer

public class MarketplaceClientController {
	public String userName =null, pass = null, firstName =null, lastName = null;
	private String[] msg = new String[3];
	
	private Scanner sc=new Scanner(System.in);
	
	private Marketplace model;
	private MarketplaceClientFrontView view;

	//Model-View Glue
	public MarketplaceClientController (Marketplace model, MarketplaceClientFrontView view) {
		this.model = model;
		this.view = view;
	}
	
	public void logIncontroller(){
		// RMI Security Manager
		System.setSecurityManager(new SecurityManager());
		try{
			//get the user name
			userName = view.get_userName();			
			
			//get the user provided password
			pass = view.get_pass();

			//get the user first name
			firstName = view.get_firstName();			
			
			//get the user last Name
			lastName = view.get_lastName();
		
			MarketplaceClientFrontController fc = new MarketplaceClientFrontController();			
			
			if (fc.isAuthenticUser(userName, pass)){
				if(firstName != null && lastName != null){
					//invokes register remote method here
					msg = model.register(firstName, lastName, userName, pass);
				}
				else{
					//invokes login remote method here
					msg = model.logIN(userName, pass);
					System.out.println(msg[1]);						
				}	
				
				if(msg[0]!= null){ //successfull login or register
					fc.validityCheck(msg);
					/*
					 * If user is Customer then will be able to browse items, add items to the cart
					 * and also allow to purchase item from the cart; to add item Customer should 
					 * provide itemID and quantity; to purchase item Customer should provide cartID. 
					 */
					if(msg[0].equalsIgnoreCase("customer")){
						while(true){
							System.out.print("Press (1 or 2 or 3 or 4)\n1. Browse Items \n2. AddItem\n3. purchaseItem\n4. Exit\nchoice: ");
							String choice = sc.next();
							if (choice.equals("1")){
								browseItemController(userName);
							}
							
							else if (choice.equals("2")){
								System.out.print("Item ID = ");
								String itemID = sc.next();
								System.out.print("\nQuantity = ");
								String quantity = sc.next();
								addItemController(userName, itemID, null, null, "0", quantity);
							}
							else if (choice.equals("3")){
								browseCartController(userName);
								System.out.print("Provide the Cart ID to Purchase = ");
								String cartID = sc.next();
								purchaseItemController(userName, cartID);
							}
							else if (choice.equals("4")){
								break;
							}
							else {
								System.out.println("Wrong choice entered!!");
							}
						}//while(1)
					}//if (user is customer)

					/*
					 * If user is Admin then will be able to browse items, add new item, update existing
					 * item, remove item from the inventory. to add item Admin should provide Item type, 
					 * description, price and quantity; to update item Admin should provide itemID, type,
					 * description, price and quantity; to remove item only itemID is required.
					 */
					if(msg[0].equalsIgnoreCase("admin")){
						while(true){
							System.out.print("Press (1 or 2 or 3 or 4 or 5)\n1. Browse Items \n2. AddItem\n3. UpdateItem\n4. RemoveItem\n5. Exit\nchoice: ");
							String choice = sc.next();
							if (choice.equals("1")){
								browseItemController(userName);
							}
							else if (choice.equals("2")){							
								System.out.print("Type = ");
								String type = sc.next();
					
								System.out.print("Description = ");
								String description = sc.next();

								System.out.print("Price = ");
								String price = sc.next();

								System.out.print("Quantity = ");
								String quantity = sc.next();

								addItemController(userName, null, type, description, price, quantity);
							}
							else if (choice.equals("3")){
								System.out.println("Current Item list is:");
								browseItemController(userName);

								System.out.print("Item ID = ");
								String itemID = sc.next();
								
								System.out.print("Type = ");
								String type = sc.next();
					
								System.out.print("Description = ");
								String description = sc.next();

								System.out.print("Price = ");
								String price = sc.next();

								System.out.print("Quantity = ");
								String quantity = sc.next();

								updateItemController(userName, itemID, type, description, price, quantity);
							}
							else if (choice.equals("4")){
								System.out.println("Current Item list is:");
								browseItemController(userName);

								System.out.print("Provide the Item ID that want to remove = ");
								String itemID = sc.next();

								removeItemController(userName, itemID);
							}
							else if (choice.equals("5")){
								break;
							}
							else {
								System.out.println("Wrong choice entered!!");
							}
						}//while (1)
					}//if(user is admin)
				}//if(msg[0]!= null){
			}//if (validity check true)
			else{
				System.out.println("Validity check fails!!!");
			}
		} catch(Exception e){
			System.out.println("MarketplaceClientController Exception: " +e.getMessage());
		}
		System.exit(0);
	}

	/*Invoke remote method browseItem() here and display the all existine products*/
	public void browseItemController(String userName){
		try{
			ArrayList<String[]> data = new ArrayList<String[]>();
			data = model.browseItem();
			
			System.out.println("ItemID\tType\tDesc\tPrice\tQuantity");
			//Print out all data of inventory
			for(int i = 0; i < data.size(); i++)
			{
				for(int j = 0; j < data.get(i).length; j++)
				{
					System.out.print(data.get(i)[j] + "\t");
				}
				System.out.print("\n");
			}
			System.out.print("\n");
		} catch(Exception e){
			System.out.println("MarketplaceClientController Browse Item Exception: " +e.getMessage());
		}
	}
	/*Invoke remote method browseCart() here and display the shopping cart*/
	public void browseCartController(String userName){
		try{
			ArrayList<String[]> cart = new ArrayList<String[]>();
			cart = model.browseCart(msg[0], userName);
			
			System.out.println("CartID\t\tCustomerID\tItemID\t\tQuantity");
			//Print out all data of cart
			for(int i = 0; i < cart.size(); i++)
				{
					for(int j = 0; j < cart.get(i).length; j++)
					{
						System.out.print(cart.get(i)[j] + "	\t");
					}
					System.out.print("\n");
				}
			System.out.print("\n");
		} catch(Exception e){
			System.out.println("MarketplaceClientController Browse Item Exception: " +e.getMessage());
		}
	}
		
	/*Invoke remote method purchaseItem() here and once the purchase is successful then
	 * display the updated cart. Only Customer can place request for purchaseItem() method.
	 */
	public void purchaseItemController(String userName, String cartID){
		try{
			
			ArrayList<String[]> data = new ArrayList<String[]>();
			data = model.purchaseItem(msg[0], userName, cartID);
			if (data.isEmpty()){
				System.out.println ("Nothing left or Invalid quantity is requested (please see the availablity of the item)!!!");
				}
			else{
				System.out.println("CartID\t\tCustomerID\tItemID\t\tQuantity");
				//Print out all data of cart
				for(int i = 0; i < data.size(); i++)
				{
						for(int j = 0; j < data.get(i).length; j++)
						{
							System.out.print(data.get(i)[j] + "	\t");
						}
						System.out.print("\n");
				}//for
			}//else
			System.out.print("\n");
		} catch(Exception e){
		System.out.println("MarketplaceClientController Purchase Item Exception: " +e.getMessage());
		}
	}

	
	/* Invoke remote method addItem() here based on role that define the actions;
	 * if user is Admin then by invoking addItem() will be able to add item to the
	 * the inventory, once added successfully then will return the updated inventory;
	 * again if user is client then by invoking addItem() will be able to add item to Cart;
	 * after succfull add item to cart will be able to sees current cart items.
	 */
	public void addItemController(String userName, String itemID, String type, String description, String price, String quantity){
		try{
			ArrayList<String[]> data = new ArrayList<String[]>();
			
			data = model.addItem(msg[0], userName, itemID, type, description, price, quantity);
	
			if(msg[0].equals("admin")){
				System.out.println("Add Item successfull (new list):");				
				System.out.println("ItemID\tType\tDesc\tPrice\t\tQuantity");
			}
			else{
				if (data.isEmpty())	
					System.out.println ("No item in cart!!!");
				else	
					System.out.println("CartID\t\tCustomerID\tItemID\tQuantity");
			}
			//Print out all data
			for(int i = 0; i < data.size(); i++)
			{
				for(int j = 0; j < data.get(i).length; j++)
				{
					System.out.print(data.get(i)[j] + "\t\t");
				}
				System.out.print("\n");
			}
			System.out.print("\n");

		} catch(Exception e){
			System.out.println("MarketplaceClientController Add Item Exception: " +e.getMessage());
		}
	}
	
	
	/*Invoke remote method updateItem() here and once the item updated is successful then
	 *display the updated inventory's product list; only admin can place request to update
	 */
	public void updateItemController(String userName, String itemID, String type, String description, String price, String quantity){
		try{
			ArrayList<String[]> data = new ArrayList<String[]>();
			browseItemController(userName);
			data = model.updateItem(msg[0], userName, itemID, type, description, price, quantity);
			System.out.println("Updated Item fields:");				
			
			//Print out all data
			System.out.println("ItemID\tType\tDesc\tPrice\tQuantity");
			for(int i = 0; i < data.size(); i++)
			{
				for(int j = 0; j < data.get(i).length; j++)
				{
					System.out.print(data.get(i)[j] + "\t");
				}
				System.out.print("\n");
			}
			System.out.print("\n");			
		} catch(Exception e){
			System.out.println("MarketplaceClientController Update Item Exception: " +e.getMessage());
		}
	}
	
	/*
	 *remove the product from inventory; only admin can place request to remove items
	 */
	public void removeItemController(String userName, String itemID){
		try{
			ArrayList<String[]> data = new ArrayList<String[]>();

			data = model.removeItem(msg[0], userName, itemID);
			System.out.println("Remove Item successfull (new list):");				
			//Print out all data
			System.out.println("ItemID\tType\tDesc\tPrice\tQuantity");
			for(int i = 0; i < data.size(); i++)
			{
				for(int j = 0; j < data.get(i).length; j++)
				{
					System.out.print(data.get(i)[j] + "\t");
				}
				System.out.print("\n");
			}
			System.out.print("\n");			
		} catch(Exception e){
			System.out.println("MarketplaceClientController remove Item Exception: " +e.getMessage());
		}
	}
}