package sol.NewsFeedService.redis;

import lombok.AllArgsConstructor;

import javax.persistence.Id;

@AllArgsConstructor
public class Token {
    @Id
    private String username;
    private String accessToken;
    private String refreshToken;
}
