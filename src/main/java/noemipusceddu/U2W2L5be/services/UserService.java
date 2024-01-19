package noemipusceddu.U2W2L5be.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private Cloudinary imagesUploader;

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
       user.setAvatar("https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());
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

    public String uploadImage(MultipartFile file) throws IOException {
        String url = (String) imagesUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        return url;
    }
}
