package sol.UserService.userActivity;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sol.UserService.user.User;
import sol.UserService.util.ResponseDto;
import sol.UserService.util.UtilMethods;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/news")
@AllArgsConstructor
public class UserActivityController {
    private UtilMethods utilMethods;
    private UserActivityService userActivityService;

    @GetMapping
    public ResponseEntity getNews(HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);
        ResponseDto responseDto = userActivityService.getNews(user);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
