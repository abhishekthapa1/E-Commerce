package project.ecommerce.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import project.ecommerce.model.UserInfo;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByName(String username);
}
