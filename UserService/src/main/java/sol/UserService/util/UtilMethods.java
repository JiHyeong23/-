package sol.UserService.util;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import sol.UserService.security.JwtHelper;
import sol.UserService.user.User;
import sol.UserService.user.UserRepository;

import javax.servlet.http.HttpServletRequest;

@Component
@AllArgsConstructor
public class UtilMethods {
    private JwtHelper jwtHelper;
    private UserRepository userRepository;

    public User parseTokenForUser(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization").substring(7);
            String email = jwtHelper.getEmailFromJwtToken(token);
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "토큰이 없습니다");
        }
    }

    public ResponseDto makeSuccessResponseDto(String message) {
        return ResponseDto.builder()
                .result(Result.SUCCESS).message(message)
                .build();
    }

    public <T>ResponseDto makeSuccessResponseDto(String message, T response) {
        return ResponseDto.builder()
                .result(Result.SUCCESS).message(message).response(response)
                .build();
    }

    public <T>ResponseDto makeFailResponseDto(String message, T response) {
        return ResponseDto.builder()
                .result(Result.FAIL).message(message).response(response)
                .build();
    }

}
