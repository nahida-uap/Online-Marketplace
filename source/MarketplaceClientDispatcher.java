// Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment.
//
// nschowdh

//Dispatcher


public class MarketplaceClientDispatcher {	
	/**
	 * Based upon the request - dispatch the view.
	 *
	 * @param request
	 */
	public void dispatchRequest(String[] msg){
		/*new view instance*/
		AbcLogin abcLogin = new AbcLogin();

		/*concrete commands*/
		MarketplaceAdminLogin malog = new MarketplaceAdminLogin(abcLogin);
		MarketplaceCustomerLogin mclog= new MarketplaceCustomerLogin(abcLogin);
	
		MarketplaceApplication app;
		GUIAbstractFactory factory;

		// Admin or Customer roleBasedLogin Views		
		if (msg[0].equalsIgnoreCase("customer")){
			//invoker object
			factory = new CustomerConcreteFactory();
			app = new MarketplaceApplication (factory);
			app.roleBasedLogin();
		}
		else if (msg[0].equalsIgnoreCase("admin")) {
			//invoker object
			factory = new AdminConcreteFactory();
			app = new MarketplaceApplication (factory);
			app.roleBasedLogin();
		}
		else{
			System.out.println(msg[1]);
		}
	}
}
