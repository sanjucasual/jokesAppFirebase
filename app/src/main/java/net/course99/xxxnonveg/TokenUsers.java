package net.course99.xxxnonveg;

public class TokenUsers {

   private  String id;
   private  String token;

    public TokenUsers(String id, String token) {
        this.id = id;
        this.token = token;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {

        return id;
    }

    public String getToken() {
        return token;
    }


}
