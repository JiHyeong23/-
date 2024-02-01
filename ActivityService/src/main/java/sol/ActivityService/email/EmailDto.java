package sol.ActivityService.email;

import lombok.Getter;

import javax.validation.constraints.Email;

@Getter
public class EmailDto {
    @Email
    private String email;
}
