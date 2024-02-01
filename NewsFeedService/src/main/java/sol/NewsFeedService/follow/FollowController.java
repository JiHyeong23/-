package sol.NewsFeedService.follow;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sol.NewsFeedService.follow.dto.FollowUserDto;
import sol.NewsFeedService.user.User;
import sol.NewsFeedService.util.ResponseDto;
import sol.NewsFeedService.util.UtilMethods;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/follows")
@AllArgsConstructor
public class FollowController {
    private FollowService followService;
    private UtilMethods utilMethods;
    @PostMapping
    public ResponseEntity followUser(@RequestBody FollowUserDto followUserDto, HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);
        ResponseDto responseDto = followService.saveFollow(followUserDto, user);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
