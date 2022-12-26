package filter.role_filter;

import util.enums.RoleName;

import javax.servlet.annotation.*;

/**
 * Filter all senior cashier requests
 */
@WebFilter("/senior-cashier/*")
public class SeniorCashierFilter extends RoleFilter {
    public SeniorCashierFilter() {
        super(RoleName.senior_cashier);
    }
}
