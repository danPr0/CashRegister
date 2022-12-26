package filter.role_filter;

import util.enums.RoleName;

import javax.servlet.annotation.*;

/**
 * Filter all cashier requests
 */
@WebFilter("/cashier/*")
public class CashierFilter extends RoleFilter {
    public CashierFilter() {
        super(RoleName.cashier);
    }
}
