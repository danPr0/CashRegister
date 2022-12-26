package filter.role_filter;

import util.enums.RoleName;

import javax.servlet.annotation.*;

/**
 * Filter all commodity expert requests
 */
@WebFilter("/commodity-expert/*")
public class CommodityExpertFilter extends RoleFilter {
    public CommodityExpertFilter() {
        super(RoleName.commodity_expert);
    }
}
