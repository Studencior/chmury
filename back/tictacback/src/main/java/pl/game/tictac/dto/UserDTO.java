package pl.game.tictac.dto;

public class UserDTO {
    private String username;
    private String password;
    // Możesz dodać więcej pól, na przykład email, jeśli jest używany

    // Konstruktor
    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Gettery i Settery
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
