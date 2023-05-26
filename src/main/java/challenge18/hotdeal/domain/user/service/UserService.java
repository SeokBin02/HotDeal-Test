package challenge18.hotdeal.domain.user.service;

import challenge18.hotdeal.common.Enum.UserRole;
import challenge18.hotdeal.common.util.JwtUtil;
import challenge18.hotdeal.common.util.Message;
import challenge18.hotdeal.domain.user.dto.LoginRequest;
import challenge18.hotdeal.domain.user.dto.SignupRequest;
import challenge18.hotdeal.domain.user.entity.User;
import challenge18.hotdeal.domain.user.repository.UserRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private static String ADMINTOKEN = "E7F839DAC8507A74B9D1F3F287A2640076BF5CB38F44F5E9449E13FD5ED650AC";


    // 회원가입
    public ResponseEntity<Message> signup(SignupRequest request) {
        UserRole role = UserRole.ROLE_USER;

        // 중복 회원 체크
        Optional<User> user = checkUserExist(request.getUserId());
        if(user.isPresent()){
            throw new DuplicateRequestException("중복된 회원이 이미 존재합니다.");
        }

        // 관리자 회원가입 체크
        if(request.isAdmin()){
            if(!request.getAdminToken().equals(ADMINTOKEN)){
                throw new IllegalArgumentException("ADMINTOKEN이 유효하지 않습니다.");
            }
            role = UserRole.ROLE_ADMIN;
        }

        userRepository.save(new User(request.getUserId(), request.getPassword(), role));
        return new ResponseEntity<>(new Message("회원가입 성공"), HttpStatus.CREATED);
    }

    // 로그인
    @Transactional(readOnly = true)
    public ResponseEntity<Message> login(LoginRequest request, HttpServletResponse response) {
        // 회원정보 존재 유무 체크
        Optional<User> user = checkUserExist(request.getUserId());
        if(!user.isPresent()){
            throw new NullPointerException("입력하신 회원정보가 존재하지 않습니다.");
        }
        // 비밀번호 체크
        if(!user.get().getPassword().equals(request.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.get().getUserId(), user.get().getRole()));
        return new ResponseEntity<>(new Message("로그인 성공"), HttpStatus.OK);
    }

    private Optional<User> checkUserExist(String userId){
        return userRepository.findByUserId(userId);
    }
}
