// Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment.
//
// nschowdh

//Front Controller Layer

public class MarketplaceClientFrontController {
	
	public boolean isAuthenticUser(String id, String pass) {	
			
		if(pass.length() >= 4){
			System.out.println("Invalid password entered (Password length can't be more than 4)");
			return false;
		}

		else{
			return true;
		}

	}

	public void validityCheck(String[] msg){
		try{
			//Dispatcher instance...
		        MarketplaceClientDispatcher dispatcher = new MarketplaceClientDispatcher();
			dispatcher.dispatchRequest(msg);
		} catch(Exception e){
			System.out.println("MarketplaceClientFrontController Exception: " +e.getMessage());
			e.printStackTrace();
		}
	}
}
