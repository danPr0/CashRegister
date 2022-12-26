package filter.role_filter;

import util.enums.RoleName;

import javax.servlet.annotation.WebFilter;

/**
 * Filter all admin requests
 */
@WebFilter("/admin/*")
public class AdminFilter extends RoleFilter {
    public AdminFilter() {
        super(RoleName.admin);
    }
}
