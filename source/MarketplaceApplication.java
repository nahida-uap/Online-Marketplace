// Honor Pledge:
//
// I pledge that I have neither given nor 
// received any help on this assignment.
//
// nschowdh

/**
 * Factory users don't care which concrete factory they use since they work with
 * factories and products through abstract interfaces.
 */

/* Doesn't matter admin or customer login until the role type is confirm
 * by server and then based on role type login view will execute
 */

public class MarketplaceApplication {
    private Login login;

    public MarketplaceApplication (GUIAbstractFactory factory) {
        login = factory.execute();
    }

    public void roleBasedLogin() {
        login.roleBasedLogin();
    }
}