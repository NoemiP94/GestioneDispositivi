package noemipusceddu.U2W2L5be.services;

import noemipusceddu.U2W2L5be.entities.User;
import noemipusceddu.U2W2L5be.exceptions.BadRequestException;
import noemipusceddu.U2W2L5be.exceptions.NotFoundException;
import noemipusceddu.U2W2L5be.payloads.user.UserDTO;
import noemipusceddu.U2W2L5be.repositories.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public User save(UserDTO body){

        userDAO.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("L'email " + user.getEmail() + " già presente! Scegline un'altra!");
        });
       //salva solo se l'email non è già presente
       User user = new User();
       user.setName(body.name());
       user.setSurname(body.surname());
       user.setUsername(body.username());
       user.setEmail(body.email());
       return userDAO.save(user);
    }

    public User findById(UUID id){
        return userDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public Page<User> findAllUsers(int page, int size, String orderBy){
        if(size >= 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return userDAO.findAll(pageable);
    }

    public User findByIdAndUpdate(UUID id, UserDTO body){
        User found = this.findById(id);
        found.setName(body.name());
        found.setSurname(body.surname());
        found.setUsername(body.username());
        found.setEmail(body.email());
        return userDAO.save(found);

    }

    public void findByIdAndDelete(UUID id){
        User found = this.findById(id);
        userDAO.delete(found);
    }
}
