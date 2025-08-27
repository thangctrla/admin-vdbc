package vn.vdbc.core.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import vn.vdbc.core.model.request.MenuRequest
import vn.vdbc.core.model.request.RoleGrantRequest
import vn.vdbc.core.model.request.UserGrantRequest
import vn.vdbc.core.model.response.BaseResponse
import vn.vdbc.core.service.dao.MenuDao


@RestController
@RequestMapping("/menu")
@PreAuthorize("hasRole('ADMIN')")
class MenuController {
    @Autowired
    MenuDao menuDao

    @GetMapping
    ResponseEntity<BaseResponse> getAllMenus(HttpServletRequest httpServletRequest) {
        try {
            def result = menuDao.getAllMenus(httpServletRequest)
            return ResponseEntity.ok(new BaseResponse(result))
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse(error: e.getMessage()))
        }
    }

    @GetMapping("/by-roles")
    ResponseEntity<BaseResponse> getMenusByRoles(@RequestParam(name = "roleIds") List<Integer> roleIds, HttpServletRequest httpServletRequest) {
        try {
            def result = menuDao.getMenusById(roleIds, httpServletRequest)
            return ResponseEntity.ok(new BaseResponse(result))
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse(error: e.getMessage()))
        }
    }

    @GetMapping("/by-user/{userId}")
    ResponseEntity<BaseResponse> getMenusByUserId(@PathVariable(name = "userId") String userId, HttpServletRequest httpServletRequest) {
        try {
            def result = menuDao.getMenusByUserId(userId, httpServletRequest)
            return ResponseEntity.ok(new BaseResponse(result))
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse(error: e.getMessage()))
        }
    }

    @GetMapping("/my-menus")
    ResponseEntity<BaseResponse> getMyMenus(HttpServletRequest httpServletRequest) {
        try {
            def result = menuDao.getMenusById(httpServletRequest)
            return ResponseEntity.ok(new BaseResponse(result))
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse(error: e.getMessage()))
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<BaseResponse> getMenuById(@PathVariable(name = "id") String id, HttpServletRequest httpServletRequest) {
        try {
            def result = menuDao.getMenuById(id, httpServletRequest)
            return ResponseEntity.ok(new BaseResponse(result))
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse(error: e.getMessage()))
        }
    }

    @PostMapping
    ResponseEntity<BaseResponse> createMenu(@RequestBody MenuRequest request, HttpServletRequest httpServletRequest) {
        try {
            def result = menuDao.createMenu(request, httpServletRequest)
            return ResponseEntity.ok(new BaseResponse(result))
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse(error: e.getMessage()))
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<BaseResponse> updateMenu(@PathVariable(name = "id") String id, @RequestBody MenuRequest request, HttpServletRequest httpServletRequest) {
        try {
            def result = menuDao.updateMenu(id, request, httpServletRequest)
            return ResponseEntity.ok(new BaseResponse(result))
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse(error: e.getMessage()))
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<BaseResponse> deleteMenu(@PathVariable(name = "id") String id, HttpServletRequest httpServletRequest) {
        try {
            def result = menuDao.deleteMenu(id, httpServletRequest)
            return ResponseEntity.ok(new BaseResponse(result))
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse(error: e.getMessage()))
        }
    }

    @PostMapping("/{id}/role-grants")
    ResponseEntity<BaseResponse> updateMenuRoleGrants(
            @PathVariable(name = "id") String id,
            @RequestBody List<RoleGrantRequest> roleGrants,
            HttpServletRequest httpServletRequest) {
        try {
            def result = menuDao.updateMenuRoleGrants(id, roleGrants, httpServletRequest)
            return ResponseEntity.ok(new BaseResponse(result))
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse(error: e.getMessage()))
        }
    }

    @PostMapping("/{id}/user-grants")
    ResponseEntity<BaseResponse> updateMenuUserGrants(
            @PathVariable(name = "id") String id,
            @RequestBody List<UserGrantRequest> userGrants,
            HttpServletRequest httpServletRequest) {
        try {
            def result = menuDao.updateMenuUserGrants(id, userGrants, httpServletRequest)
            return ResponseEntity.ok(new BaseResponse(result))
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse(error: e.getMessage()))
        }
    }

    @PostMapping("parent")
    ResponseEntity<BaseResponse> parent(@RequestBody Map body, HttpServletRequest httpServletRequest) {
        try {
            def result = menuDao.createMenuParent(body, httpServletRequest)
            return ResponseEntity.ok(new BaseResponse(result))
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse(error: e.getMessage()))
        }
    }

    @PutMapping("parent/{id}")
    ResponseEntity<BaseResponse> updateParent(@PathVariable(name = "id") String id, @RequestBody Map body, HttpServletRequest httpServletRequest) {
        try {
            def result = menuDao.updateMenuParent(id, body, httpServletRequest)
            return ResponseEntity.ok(new BaseResponse(result))
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse(error: e.getMessage()))
        }
    }

    @GetMapping("parent/{id}")
    ResponseEntity<BaseResponse> getParent(@PathVariable(name = "id") String id, HttpServletRequest httpServletRequest) {
        try {
            def result = menuDao.getMenuParent(id, httpServletRequest)
            return ResponseEntity.ok(new BaseResponse(result))
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse(error: e.getMessage()))
        }
    }

    @GetMapping("parent")
    ResponseEntity<BaseResponse> getParent(
            @RequestParam(name = "page", defaultValue = "1") @Min(0L) @Max(100L) Integer page,
            @RequestParam(name = "size", defaultValue = "10") @Min(0L) @Max(20L) Integer size,
            HttpServletRequest httpServletRequest
    ) {
        try {
            def result = menuDao.getMenuParent(page, size, httpServletRequest)
            return ResponseEntity.ok(new BaseResponse(result))
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse(error: e.getMessage()))
        }
    }

    @DeleteMapping("parent/{id}")
    ResponseEntity<BaseResponse> deleteParent(@PathVariable(name = "id") String id, HttpServletRequest httpServletRequest) {
        try {
            def result = menuDao.deleteMenuParent(id, httpServletRequest)
            return ResponseEntity.ok(new BaseResponse(result))
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse(error: e.getMessage()))
        }
    }
}