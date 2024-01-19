package noemipusceddu.U2W2L5be.payloads.device;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record DeviceDTO(@NotEmpty(message = "Questo campo è obbligatorio")
                        @NotBlank(message = "Questo campo non può contenere solo spazi vuoti")
                        String tipo,
                        @NotEmpty(message = "Questo campo è obbligatorio")
                        @NotBlank(message = "Questo campo non può contenere solo spazi vuoti")
                        String stato,
                        UUID userId
                       ) {
}
