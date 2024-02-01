package sol.UserService.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseDto<T> {
    private Result result;
    private String message;
    private T response;
}
