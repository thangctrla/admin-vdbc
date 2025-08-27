package vn.vdbc.core.service.dao



interface UserDao {

    def getOneUser(String id);

    def getAllUsers(String role);

    def deleteUser(String id);

    def assignUserToOrganiztion(Map body);

    def assignAppToOrganiztion(Map body);



}
