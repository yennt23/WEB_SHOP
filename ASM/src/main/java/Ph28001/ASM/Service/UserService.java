package Ph28001.ASM.Service;

import Ph28001.ASM.Dto.UserDto;
import Ph28001.ASM.Entity.User;


import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}
