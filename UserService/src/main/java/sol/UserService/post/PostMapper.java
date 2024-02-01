package sol.UserService.post;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import sol.UserService.post.dto.PostCreationDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
    Post creationDtoToPost(PostCreationDto postCreationDto);
}
