// Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment.
//
// nschowdh

/**
* Concrete Command Class 2
* This is a CUSTOMER variant of a Login command.
*/
public class MarketplaceCustomerLogin implements Login {
	private AbcLogin abcLogin;
	/**
	 * Constructor
	 */
	public MarketplaceCustomerLogin(AbcLogin abcLogin) {
		this.abcLogin = abcLogin;
	}
	/**
	 * roleBasedLogin Command Method
	 */
	public void roleBasedLogin() {
		abcLogin.customerLogin();
	}
}

