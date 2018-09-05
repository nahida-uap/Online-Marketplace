// Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment.
//
// nschowdh

/**
* Concrete Command Class 1
* This is an ADMIN variant of a login command.
*/
public class MarketplaceAdminLogin implements Login {
	private AbcLogin abcLogin;
	/**
	 * Constructor
	 */
	public MarketplaceAdminLogin(AbcLogin abcLogin) {
		this.abcLogin = abcLogin;
	}
	/**
	 * roleBasedLogin Command Method
	 */
	public void roleBasedLogin() {
		abcLogin.adminLogin();
	}
}
