package filter.role_filter;

import util.enums.RoleName;

import javax.servlet.annotation.WebFilter;

/**
 * Filter all guest requests
 */
@WebFilter("/guest/*")
public class GuestFilter extends RoleFilter {
    public GuestFilter() {
        super(RoleName.guest);
    }
}
