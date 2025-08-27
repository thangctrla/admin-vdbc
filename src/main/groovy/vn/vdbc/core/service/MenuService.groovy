package vn.vdbc.core.service

import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import vn.vdbc.core.model.request.MenuConfig
import vn.vdbc.core.model.request.MenuConfigRequest
import vn.vdbc.core.model.request.MenuRequest
import vn.vdbc.core.model.request.RoleGrantRequest
import vn.vdbc.core.model.request.UserGrantRequest
import vn.vdbc.core.service.dao.MenuDao
import vn.vdbc.core.utils.Utils

import java.util.stream.Collectors

@Service
class MenuService implements MenuDao {
    @Autowired
    DatabaseService dbs

    @Autowired
    Utils utils

    @Override
    def getAllMenus(HttpServletRequest request) {
        String userId = utils.getUserId(request)
        def rows = dbs.rows("xcore", "select * from menu_parent where status <> 'Deleted'")
        rows.each {
            it.items = dbs.rows("xcore", """SELECT id, name, path, icon, 
                parent_id as "parentId", 
                order_index as "orderIndex", 
                is_visible as "isVisible" 
                FROM menu_item where status = 'Active' and parent_id = ? ORDER BY order_index ASC""", it.id)
        }
        return rows
    }

    @Override
    def createMenuParent(Map body, HttpServletRequest request) {
        String userId = utils.getUserId(request)
        def check = dbs.rows("xcore", "select * from menu_parent where id = ?", body.id)
        if (!check.isEmpty()) throw new RuntimeException("Trùng lặp key")
        def rows = dbs.executeUpdate("xcore", """
            INSERT INTO menu_parent (id, name, icon, order_index, is_visible, status) VALUES(?, ?, ?, ?, 0, 'Active')""",
                body.id, body.name, body.icon, body.orderIndex)
        return rows
    }

    @Override
    def getMenuParent(int page, int size, HttpServletRequest request) {
        String userId = utils.getUserId(request)
        def rows = dbs.rows("xcore", """
           SELECT id, name, icon, 
           order_index as "orderIndex", 
           is_visible as "isVisible" 
           FROM menu_parent where status <> 'Deleted' LIMIT ? OFFSET ?""", size, page)
        return rows
    }

    @Override
    def getMenuParent(String id, HttpServletRequest request) {
        String userId = utils.getUserId(request)
        def rows = dbs.rows("xcore", """
           SELECT id, name, icon, 
           order_index as "orderIndex", 
           is_visible as "isVisible" 
           FROM menu_parent where id = ?""", id)
        return rows
    }

    @Override
    def updateMenuParent(String id, Map body, HttpServletRequest request) {
        String userId = utils.getUserId(request)
        def rows = dbs.executeUpdate("xcore", """
          UPDATE menu_parent SET name = ?, icon = ?, order_index = ?, is_visible = ? WHERE id = ?""",
                body.name, body.icon, body.orderIndex, body.isVisible, id)
        return rows
    }

    @Override
    def deleteMenuParent(String id, HttpServletRequest request) {
        String userId = utils.getUserId(request)
        def rows = dbs.executeUpdate("xcore", """
          UPDATE menu_parent SET status = 'Deleted' WHERE id = ?""", id)
        return rows
    }

    def getMenusById(HttpServletRequest request) {
        String userId = utils.getUserId(request)
        return getMenusByUserId(userId, request)
    }

    @Override
    def getMenusById(List<Integer> roleIds, HttpServletRequest request) {
        String userId = utils.getUserId(request)
        String placeholders = String.join(",", Collections.nCopies(roleIds.size(), "?"))

        def rows = dbs.rows("xcore",
                String.format("""
                   SELECT DISTINCT
    mp.id as "parentId",
    mp.name as "parentName",
    mp.icon as "parentIcon",
    mp.order_index as "parentOrder",
    mi.id as "itemId",
    mi.name as "itemName",
    mi.path as "itemPath",
    mi.icon as "itemIcon",
    mi.order_index as "orderIndex"
FROM menu_parent mp
INNER JOIN menu_item mi ON mp.id = mi.parent_id
INNER JOIN menu_item_role mir ON mi.id = mir.menu_item_id
WHERE mp.is_visible is true  
AND mir.role_id IN (%s) AND mir.is_granted is true
ORDER BY mp.order_index ASC, mi.order_index ASC""",
                        placeholders), roleIds.toArray())

        def menuMap = [:]

        rows.each { row ->
            String parentId = row.parentId

            if (!menuMap.containsKey(parentId)) {
                menuMap[parentId] = [
                        id        : parentId,
                        name      : row.parentName,
                        icon      : row.parentIcon,
                        orderIndex: row.parentOrder,
                        items     : []
                ]
            }

            menuMap[parentId].items.add([
                    id        : row.itemId,
                    name      : row.itemName,
                    icon      : row.itemIcon,
                    path      : row.itemPath,
                    orderIndex: row.orderIndex
            ])
        }

        def result = menuMap.values().findAll { it.items.size() > 0 }
        return result
    }

    @Override
    def getMenusByUserId(String userId, HttpServletRequest request) {
        String currentUserId = utils.getUserId(request)
        def roleIds = dbs.rows("xcore", "SELECT r.id FROM roles r LEFT JOIN user_roles ur ON r.id = ur.role_id WHERE ur.user_id = ?", [userId]).collect { it.id } as List<Integer>
        String placeholders = String.join(",", Collections.nCopies(roleIds.size(), "?"))

        def rows = dbs.rows("xcore",
                String.format("""
                       SELECT DISTINCT
        mp.id as "parentId",
        mp.name as "parentName",
        mp.icon as "parentIcon",
        mp.order_index as "parentOrder",
        mi.id as "itemId",
        mi.name as "itemName",
        mi.path as "itemPath",
        mi.icon as "itemIcon",
        mi.order_index as "orderIndex"
    FROM menu_parent mp
    INNER JOIN menu_item mi ON mp.id = mi.parent_id
    INNER JOIN menu_item_role mir ON mi.id = mir.menu_item_id
    LEFT JOIN menu_item_users miu ON mi.id = miu.menu_item_id
    WHERE mi.is_visible is true and mi.status = 'Active' and mp.is_visible is true and mp.status = 'Active'
    AND ((mir.role_id in (%s) AND mir.is_granted is true) OR (miu.user_id = '%s' AND miu.is_granted is true))
    ORDER BY mp.order_index ASC, mi.order_index ASC""",
                        placeholders, userId), roleIds.toArray())

        def menuMap = [:]

        rows.each { row ->
            String parentId = row.parentId

            if (!menuMap.containsKey(parentId)) {
                menuMap[parentId] = [
                        id   : parentId,
                        name : row.parentName,
                        items: []
                ]
            }

            menuMap[parentId].items.add([
                    id        : row.itemId,
                    name      : row.itemName,
                    icon      : row.itemIcon,
                    path      : row.itemPath,
                    orderIndex: row.orderIndex
            ])
        }

        def result = menuMap.values().findAll { it.items.size() > 0 }
        return result
    }

    @Override
    def getMenuById(String id, HttpServletRequest request) {
        String userId = utils.getUserId(request)
        def rows = dbs.rows("xcore", """SELECT id, name, path, icon, 
            parent_id as "parentId", 
            order_index as "orderIndex", 
            is_visible as "isVisible" 
            FROM menu_item WHERE id = ?""", id)

        def menuItem = rows[0]
        def roles = dbs.rows("xcore", """SELECT DISTINCT 
            mir.role_id as "roleId", 
            mir.is_granted as "isGranted" 
            from user_roles ur left join menu_item_role mir on ur.role_id = mir.role_id 
            where mir.menu_item_id = ?""", id)

        menuItem.roles = roles
        return menuItem
    }

    @Override
    def createMenu(MenuRequest request, HttpServletRequest httpRequest) {
        String userId = utils.getUserId(httpRequest)
        String id = UUID.randomUUID().toString().replace("-", "").substring(0, 18)
        def rows = dbs.executeUpdate("xcore", "INSERT INTO menu_item (id, path, icon, parent_id, order_index, is_visible, name) VALUES(?, ?, ?, ?, ?, ?, ?)",
                id, request.getPath(), request.getIcon(), request.getParentId(), request.getOrderIndex(), request.getIsVisible(), request.name)
        return rows
    }

    @Override
    def updateMenu(String menuId, MenuRequest request, HttpServletRequest httpRequest) {
        String userId = utils.getUserId(httpRequest)
        dbs.executeUpdate("xcore",
                "UPDATE menu_item SET path = ?, icon = ?, parent_id = ?, order_index = ?, is_visible = ?, name = ? WHERE id = ?",
                request.getPath(), request.getIcon(), request.getParentId(),
                request.getOrderIndex(), request.getIsVisible(), request.name, menuId)
        return getMenuById(menuId, httpRequest)
    }

    @Override
    def deleteMenu(String menuId, HttpServletRequest request) {
        String userId = utils.getUserId(request)
        def deleteMenu = dbs.executeUpdate("xcore",
                "update menu_item set status = 'Deleted' WHERE id = ?", menuId)
        return deleteMenu
    }

    @Override
    def updateMenuRoleGrants(String menuId, List<RoleGrantRequest> roleGrants, HttpServletRequest request) {
        String userId = utils.getUserId(request)
        def insertOperations = roleGrants.stream()
                .map(grant -> dbs.executeUpdate("xcore",
                        "INSERT INTO menu_item_role (menu_item_id, role_id, is_granted) VALUES(?, ?, ?)",
                        menuId, grant.getRoleId(), grant.getIsGranted() ? 1 : 0))
                .collect(Collectors.toList())
        return insertOperations
    }

    @Override
    def updateMenuUserGrants(String menuId, List<UserGrantRequest> userGrants, HttpServletRequest request) {
        String userId = utils.getUserId(request)
        def deleteOldGrants = dbs.executeUpdate("xcore",
                "DELETE FROM menu_item_users WHERE menu_item_id = ?", menuId)

        def insertOperations = userGrants.stream()
                .map(grant -> dbs.executeUpdate("xcore",
                        "INSERT INTO menu_item_users (menu_item_id, user_id, is_granted) VALUES(?, ?, ?)",
                        menuId, grant.getUserId(), grant.getIsGranted() ? 1 : 0))
                .collect(Collectors.toList())
        return insertOperations
    }

    @Override
    def getMenuConfigs(String menuId, HttpServletRequest request) {
        String userId = utils.getUserId(request)
        return dbs.rows("xcore",
                """SELECT id, 
                menu_item_id as "menuItemId", 
                config_key as "configKey", 
                config_value as "configValue" 
                FROM menu_configs WHERE menu_item_id = ?""", menuId)
                .collect { row ->
                    MenuConfig.builder()
                            .id(row.getString("id"))
                            .menuItemId(row.getString("menuItemId"))
                            .configKey(row.getString("configKey"))
                            .configValue(row.getString("configValue"))
                            .build()
                }
    }

    @Override
    def updateMenuConfig(MenuConfigRequest request, HttpServletRequest httpRequest) {
        String userId = utils.getUserId(httpRequest)
        if (request.getId() != null) {
            def result = dbs.executeUpdate("xcore",
                    "UPDATE menu_configs SET config_key = ?, config_value = ? WHERE id = ?",
                    request.getConfigKey(), request.getConfigValue(), request.getId())
            return result
        } else {
            String id = UUID.randomUUID().toString()
            def result = dbs.executeUpdate("xcore",
                    "INSERT INTO menu_configs (id, menu_item_id, config_key, config_value) VALUES(?, ?, ?, ?)",
                    id, request.getMenuItemId(), request.getConfigKey(), request.getConfigValue())
            return result
        }
    }

    @Override
    def deleteMenuConfig(String configId, HttpServletRequest request) {
        String userId = utils.getUserId(request)
        return dbs.executeUpdate("xcore", "DELETE FROM menu_configs WHERE id = ?", configId)
    }
}