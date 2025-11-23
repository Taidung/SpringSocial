package vn.taidung.springsocial.model.response;

public class RegisterUserResponse {
    private UserResponse user;
    private String invitationToken;

    public RegisterUserResponse() {
    }

    public RegisterUserResponse(UserResponse user, String invitationToken) {
        this.user = user;
        this.invitationToken = invitationToken;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public String getInvitationToken() {
        return invitationToken;
    }

    public void setInvitationToken(String invitationToken) {
        this.invitationToken = invitationToken;
    }
}
