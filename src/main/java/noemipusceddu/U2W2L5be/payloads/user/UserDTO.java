package noemipusceddu.U2W2L5be.payloads.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;


public record UserDTO(
        @NotEmpty(message = "Questo campo è obbligatorio")
        @NotBlank(message = "Questo campo non può contenere solo spazi vuoti")
        String name,
        @NotEmpty(message = "Questo campo è obbligatorio")
        @NotBlank(message = "Questo campo non può contenere solo spazi vuoti")
        String surname,
        @NotEmpty(message = "Questo campo è obbligatorio")
        @NotBlank(message = "Questo campo non può contenere solo spazi vuoti")
        String username,
        @NotEmpty(message = "Questo campo è obbligatorio")
        @Email(message = "Email non valida")
        String email) {
}
