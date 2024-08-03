package com.authserver.service;

import com.authserver.entity.Otp;
import com.authserver.entity.User;
import com.authserver.repository.OtpRepository;
import com.authserver.repository.UserRepository;
import com.authserver.utils.GenerateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OtpRepository otpRepository;

    /*
     * SecurityConfig.passwordEncoder(BCryptPasswordEncoder) 방식으로 패스워드를 암호화해서 저장한다.
     */
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    /*
     * 사용자가 존재할 시 OTP 인증 코드를 발급한다.
     * (인증 코드는 DB에 저장한다. 메시징 Agent가 수신자 단말기로 인증 코드를 발송했다고 가정한다.)
     */
    public void auth(User user) {
        Optional<User> o = userRepository.findUserByUsername(user.getUsername());

        if(o.isPresent()) {
            User u = o.get();
            if (passwordEncoder.matches(user.getPassword(), u.getPassword())) {
                renewOtp(u);
            } else {
                throw new BadCredentialsException("Bad credentials.");
            }
        } else {
            throw new BadCredentialsException("Bad credentials.");
        }
    }

    /*
     * OTP 인증번호 일치 여부 판단 로직
     */
    public boolean check(Otp otpToValidate) {
        Optional<Otp> userOtp = otpRepository.findOtpByUsername(otpToValidate.getUsername());
        if (userOtp.isPresent()) {
            Otp otp = userOtp.get();
            if (otpToValidate.getCode().equals(otp.getCode())) {
                return true;
            }
        }

        return false;
    }

    private void renewOtp(User u) {
        String code = GenerateCodeUtil.generateCode();

        Optional<Otp> userOtp = otpRepository.findOtpByUsername(u.getUsername());
        if (userOtp.isPresent()) {
            Otp otp = userOtp.get();
            otp.setCode(code);
        } else {
            Otp otp = new Otp();
            otp.setUsername(u.getUsername());
            otp.setCode(code);
            otpRepository.save(otp);
        }
    }

}
