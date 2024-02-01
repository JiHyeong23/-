package sol.NewsFeedService.validation;

import lombok.*;
import org.springframework.http.HttpStatus;
import sol.NewsFeedService.util.Result;

@Getter
@Setter
@AllArgsConstructor
public class ApiException {
    private Result result;
    private String message;
    private HttpStatus status;
}
