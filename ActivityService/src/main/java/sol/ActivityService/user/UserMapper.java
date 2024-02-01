package sol.ActivityService.user;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import sol.ActivityService.user.dto.UserSignUpDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User UserSignUpDtoToUser(UserSignUpDto userSignUpDto);
}
