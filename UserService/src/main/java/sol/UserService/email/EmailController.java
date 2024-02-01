package sol.UserService.email;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sol.UserService.util.ResponseDto;
import sol.UserService.util.UtilMethods;

import javax.validation.Valid;

@RequestMapping("/mail")
@RestController
@AllArgsConstructor
public class EmailController {
    private EmailService emailService;
    private UtilMethods utilMethods;
    @PostMapping("/valid")
    public ResponseEntity sendMail(@RequestBody @Valid EmailDto emailDto) {
        String athentNum = emailService.sendMail(emailDto);

        ResponseDto responseDto = utilMethods.makeSuccessResponseDto("Successfully send", athentNum);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}