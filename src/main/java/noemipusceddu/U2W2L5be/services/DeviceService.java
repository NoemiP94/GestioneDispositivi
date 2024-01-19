package noemipusceddu.U2W2L5be.services;

import noemipusceddu.U2W2L5be.entities.Device;
import noemipusceddu.U2W2L5be.entities.User;
import noemipusceddu.U2W2L5be.exceptions.NotFoundException;
import noemipusceddu.U2W2L5be.payloads.device.DeviceDTO;
import noemipusceddu.U2W2L5be.repositories.DeviceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeviceService {
    @Autowired
    private DeviceDAO deviceDAO;

    @Autowired
    private UserService userService;

    public Device save(DeviceDTO body){
        User user = userService.findById(body.userId());
        Device device = new Device();
        device.setTipo(body.tipo());
        device.setStato(body.stato());
        device.setUser(user);
        return deviceDAO.save(device);
    }

    public Device findById(UUID id){
        return deviceDAO.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

    public Page<Device> findAllDevices(int page, int size, String orderBy){
        if(size >= 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return deviceDAO.findAll(pageable);
    }

    public Device findByIdAndUpdate(UUID id, DeviceDTO body){
        Device found = this.findById(id);
        User newUser = userService.findById(body.userId());
        found.setTipo(body.tipo());
        found.setStato(body.stato());
        found.setUser(newUser);
        return deviceDAO.save(found);

    }

    public void findByIdAndDelete(UUID id){
        Device found = this.findById(id);
        deviceDAO.delete(found);
    }
}
