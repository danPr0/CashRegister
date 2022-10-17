package service_impl;

import dao.RoleDAO;
import dao_impl.RoleDAOImpl;
import entity.Role;
import service.RoleService;

public class RoleServiceImpl implements RoleService {
    private static RoleServiceImpl instance = null;
    private final RoleDAO roleDAO = RoleDAOImpl.getInstance();

    private RoleServiceImpl() {}

    public static RoleServiceImpl getInstance() {
        if (instance == null)
            instance = new RoleServiceImpl();
        return instance;
    }

    @Override
    public Role getRole(int id) {
        return roleDAO.getEntityById(id);
    }

    @Override
    public Role getRole(String name) {
        return roleDAO.getEntityByName(name);
    }
}
