package id.adyatma.dicodingbfaafinal.model;

/*This App Created By ADYATMA LAKATJINDA To Dicoding Indonesia*/
public class Favorite {
    private String login;
    private String avatar;

    public Favorite() {
    }

    public Favorite(String login, String avatar) {
        this.login = login;
        this.avatar = avatar;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
