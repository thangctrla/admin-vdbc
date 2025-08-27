package vn.vdbc.core.service.dao

import jakarta.servlet.http.HttpServletRequest
import vn.vdbc.core.model.request.MenuConfigRequest
import vn.vdbc.core.model.request.MenuRequest
import vn.vdbc.core.model.request.RoleGrantRequest
import vn.vdbc.core.model.request.UserGrantRequest

interface MenuDao {
    def getAllMenus(HttpServletRequest request)

    def createMenuParent(Map body, HttpServletRequest request)

    def getMenuParent(int page, int size, HttpServletRequest request)

    def getMenuParent(String id, HttpServletRequest request)

    def updateMenuParent(String id, Map body, HttpServletRequest request)

    def deleteMenuParent(String id, HttpServletRequest request)

    def getMenusById(List<Integer> roleIds, HttpServletRequest request)

    def getMenusById(HttpServletRequest request)

    def getMenusByUserId(String userId, HttpServletRequest request)

    def getMenuById(String id, HttpServletRequest request)

    def createMenu(MenuRequest request, HttpServletRequest httpRequest)

    def updateMenu(String menuId, MenuRequest request, HttpServletRequest httpRequest)

    def deleteMenu(String menuId, HttpServletRequest request)

    def updateMenuRoleGrants(String menuId, List<RoleGrantRequest> roleGrants, HttpServletRequest request)

    def updateMenuUserGrants(String menuId, List<UserGrantRequest> userGrants, HttpServletRequest request)

    def getMenuConfigs(String menuId, HttpServletRequest request)

    def updateMenuConfig(MenuConfigRequest request, HttpServletRequest httpRequest)

    def deleteMenuConfig(String configId, HttpServletRequest request)
}