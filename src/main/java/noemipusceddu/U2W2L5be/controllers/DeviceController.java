package noemipusceddu.U2W2L5be.controllers;

import noemipusceddu.U2W2L5be.entities.Device;
import noemipusceddu.U2W2L5be.entities.User;
import noemipusceddu.U2W2L5be.exceptions.BadRequestException;
import noemipusceddu.U2W2L5be.payloads.device.DeviceDTO;
import noemipusceddu.U2W2L5be.payloads.device.DeviceResponseDTO;
import noemipusceddu.U2W2L5be.payloads.user.UserDTO;
import noemipusceddu.U2W2L5be.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceResponseDTO saveDevice(@RequestBody @Validated DeviceDTO deviceBody, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestException("Errori nel payload! Ricontrollare!");
        }else{
            Device device = deviceService.save(deviceBody);
            return new DeviceResponseDTO(device.getIdDevice());
        }
    }

    @GetMapping("/{id}")
    public Device findDeviceById(@PathVariable UUID id){
        return deviceService.findById(id);
    }

    @GetMapping
    public Page<Device> findAllDevices(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "idDevice") String orderBy){
        return deviceService.findAllDevices(page, size, orderBy);
    }

    @PutMapping("/{id}")
    public Device findDeviceAndUpdate(@PathVariable UUID id, @RequestBody DeviceDTO modifiedDevicePayload) {
        return deviceService.findByIdAndUpdate(id, modifiedDevicePayload);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findDeviceAndDelete(@PathVariable UUID id){
        deviceService.findByIdAndDelete(id);
    }
}
