package sol.UserService.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sol.UserService.user.UserRepository;
import sol.UserService.user.UserService;
import sol.UserService.user.dto.UserLoginDto;
import sol.UserService.util.Result;
import sol.UserService.validation.ApiException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final UserService userService;
    private final JwtHelper jwtHelper;
    private final UserRepository userRepository;

    public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, JwtHelper jwtHelper, UserRepository userRepository) {
        super(authenticationManager);
        this.userService = userService;
        this.jwtHelper = jwtHelper;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserLoginDto userLoginDto = new ObjectMapper().readValue(request.getInputStream(), UserLoginDto.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDto.getEmail(),
                            userLoginDto.getPassword(),
                            new ArrayList<>()
                    )
            );
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        String username = ((User)authResult.getPrincipal()).getUsername();
        sol.UserService.user.User user = userService.findByEmail(username);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        ResponseToken token = jwtHelper.createToken(username);

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*"); //크로스오리진
        response.setHeader("Authorization", "Bearer " + token.getAccessToken());
        response.getWriter().write(new ObjectMapper().writeValueAsString(token));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        //super.unsuccessfulAuthentication(request, response, failed);
        ApiException apiException = new ApiException(Result.FAIL, failed.getMessage().substring(15), HttpStatus.NOT_FOUND);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*"); //크로스오리진
        response.getWriter().write(new ObjectMapper().writeValueAsString(apiException));
    }
}
