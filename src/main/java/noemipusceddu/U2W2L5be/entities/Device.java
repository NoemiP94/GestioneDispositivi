package noemipusceddu.U2W2L5be.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name= "devices")
@Getter
@Setter
public class Device {
    @Id
    @GeneratedValue
    private UUID idDevice;
    private String tipo;
    private String stato;
    @ManyToOne
    @JoinColumn(name = "idUser")
    private User user;
}
