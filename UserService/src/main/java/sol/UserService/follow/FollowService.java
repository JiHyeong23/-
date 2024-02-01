package sol.UserService.follow;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sol.UserService.follow.dto.FollowUserDto;
import sol.UserService.user.User;
import sol.UserService.userActivity.Activity;
import sol.UserService.util.ResponseDto;
import sol.UserService.util.UtilMethods;

@Service
@AllArgsConstructor
public class FollowService {
    private UtilMethods utilMethods;
    private FollowRepository followRepository;

    public ResponseDto saveFollow(FollowUserDto followUserDto, User follower) {
        User following = utilMethods.findUser(followUserDto.getEmail());

        Follow result = followRepository.findByFollowerAndFollowing(follower, following);
        if (result != null) {
            return utilMethods.makeFailResponseDto("이미 팔로우하고 있는 유저입니다", following.getName());
        }
        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);

        followRepository.save(follow);

        utilMethods.saveActivity(follower, Activity.FOLLOW, following.getId(), follower);

        return utilMethods.makeSuccessResponseDto("Successfully saved", follow.getFollowing().getName());
    }
}
