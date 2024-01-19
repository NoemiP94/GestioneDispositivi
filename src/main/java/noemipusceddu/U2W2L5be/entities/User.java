package noemipusceddu.U2W2L5be.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name= "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    private UUID idUser;
    private String name;
    private String surname;
    private String username;
    private String email;
    private String avatar;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Device> devicesList;
}
