package sol.NewsFeedService.user;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import sol.NewsFeedService.user.dto.UserModifyDto;
import sol.NewsFeedService.user.dto.UserPassUpdateDto;
import sol.NewsFeedService.user.dto.UserSignUpDto;
import sol.NewsFeedService.util.FileUploader;
import sol.NewsFeedService.util.ResponseDto;
import sol.NewsFeedService.util.UtilMethods;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private UtilMethods utilMethods;
    private FileUploader fileUploader;
    private BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByEmail(username);
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(), user.getPassword(), true, true, true, true, new ArrayList<>());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다");
        }
    }

    public ResponseDto saveUser(UserSignUpDto userSignUpDto) {
        userSignUpDto.setPassword(encoder.encode(userSignUpDto.getPassword()));
        User user = userMapper.UserSignUpDtoToUser(userSignUpDto);
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);

        return utilMethods.makeSuccessResponseDto("Successfully saved", user.getName());
    }

    public ResponseDto updateUserInfo(UserModifyDto userModifyDto, User user) {
        if (user.getDescription() == null) {
            user.setDescription("");
        }
        user.updateUser(userModifyDto.getName(), userModifyDto.getDescription());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        return utilMethods.makeSuccessResponseDto("Successfully updated", user.getUpdatedAt());
    }

    public ResponseDto updateProfileImage(MultipartFile profileImage, User user) {
        String beforeImage = user.getProfileImage();
        String imagePath = fileUploader.saveImage(profileImage, beforeImage);
        user.setProfileImage(imagePath);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        return utilMethods.makeSuccessResponseDto("Successfully updated", user.getUpdatedAt());
    }

    public ResponseDto updatePassword(UserPassUpdateDto userPassUpdateDto, User user) {
        ResponseDto responseDto;
        if (encoder.matches(userPassUpdateDto.getPassword(), user.getPassword())) {
            user.setNewPassword(encoder.encode(userPassUpdateDto.getNewPassword()));
            userRepository.save(user);
            return responseDto = utilMethods.makeSuccessResponseDto("Successfully updated", user.getName());
        }
        else {
            return responseDto = utilMethods.makeFailResponseDto("비밀번호가 틀립니다", user.getName());
        }
    }

    public User findByEmail(String username) {
        return userRepository.findByEmail(username);
    }
}
