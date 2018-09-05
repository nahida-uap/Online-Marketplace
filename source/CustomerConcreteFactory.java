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
public class CustomerConcreteFactory implements GUIAbstractFactory {
	AbcLogin abcLogin = new AbcLogin();

	@Override
	public MarketplaceCustomerLogin execute() {
        	return new MarketplaceCustomerLogin(abcLogin);
    }
}