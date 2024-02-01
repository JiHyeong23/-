package sol.UserService.user;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import sol.UserService.user.dto.UserSignUpDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User UserSignUpDtoToUser(UserSignUpDto userSignUpDto);
}
