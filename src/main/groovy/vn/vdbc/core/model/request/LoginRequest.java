package vn.vdbc.core.model.request;

import lombok.Data;

@Data
public class LoginRequest {
     String username;
     String password;
     String token;
     String authorizationCode;
}
