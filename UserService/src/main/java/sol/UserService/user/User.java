package sol.UserService.user;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    @Setter
    private String description;
    @Setter
    private String profileImage;
    @Setter
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Setter
    private LocalDateTime updatedAt;
    @Setter
    private LocalDateTime lastLogin;

    public void updateUser(String name, String description) {
        this.name = Objects.requireNonNullElse(name, this.name);
        this.description = Objects.requireNonNullElse(description, this.description);
    }

    public void setNewPassword(String password) {
        this.password = password;
    }
}
