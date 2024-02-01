package sol.UserService.postLike;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sol.UserService.postLike.dto.PostLikeDto;
import sol.UserService.user.User;
import sol.UserService.util.ResponseDto;
import sol.UserService.util.UtilMethods;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/postLikes")
@AllArgsConstructor
public class PostLikeController {
    private UtilMethods utilMethods;
    private PostLikeService postLikeService;

    @PostMapping
    public ResponseEntity likePost(@RequestBody PostLikeDto postLikeDto, HttpServletRequest request) {
        User user = utilMethods.parseTokenForUser(request);
        ResponseDto responseDto = postLikeService.savePostLike(postLikeDto, user);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
