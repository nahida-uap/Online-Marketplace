
// Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment.
//
// nschowdh

/**
 * Its a concrete factory extends basic factory GUIAbstractFactory 
 * and responsible for creating different view for Customer.
 */

public class AdminConcreteFactory implements GUIAbstractFactory {
	
	private AbcLogin abcLogin = new AbcLogin();
	    @Override
	    public MarketplaceAdminLogin execute() {
		return new MarketplaceAdminLogin(abcLogin );
    	}
}