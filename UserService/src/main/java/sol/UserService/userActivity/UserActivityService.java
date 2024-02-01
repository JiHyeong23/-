package sol.UserService.userActivity;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sol.UserService.user.User;
import sol.UserService.util.ResponseDto;
import sol.UserService.util.UtilMethods;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserActivityService {
    private UtilMethods utilMethods;
    private UserActivityRepository userActivityRepository;

    public ResponseDto getNews(User user) {
        List<User> following = utilMethods.getFollowing(user);
        List<UserActivity> activities = userActivityRepository.findTop10ByUserInOrderByCreatedAtDesc(following);
        List<UserNewsDto> newsFeed = new ArrayList<>();
        for (UserActivity activity : activities) {
            if (activity.getContentedBy() == user) {
                newsFeed.add(new UserNewsDto(
                        activity.getUser().getName(), activity.getActivity().toString(), activity.getActivityId(), activity.getCreatedAt(),
                        user.getName(), "My")
                );
            } else {
                newsFeed.add(new UserNewsDto(
                        activity.getUser().getName(), activity.getActivity().toString(), activity.getActivityId(), activity.getCreatedAt(),
                        activity.getContentedBy().getName(), "Follower")
                );
            }
        }
        return utilMethods.makeSuccessResponseDto("Successfully loads", newsFeed);
    }
}