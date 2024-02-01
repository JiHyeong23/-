package sol.NewsFeedService.post;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import sol.NewsFeedService.post.dto.PostCreationDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
    Post creationDtoToPost(PostCreationDto postCreationDto);
}
