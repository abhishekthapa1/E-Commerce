package project.ecommerce.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.ecommerce.model.UserInfo;
import project.ecommerce.repository.UserInfoRepository;

@Service
public class UserService {
    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public String addUser(UserInfo userInfo){
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "user added to system";
    }

}
