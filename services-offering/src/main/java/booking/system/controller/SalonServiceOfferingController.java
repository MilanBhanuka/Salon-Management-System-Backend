package booking.system.controller;

import booking.system.dto.CategoryDTO;
import booking.system.dto.SalonDTO;
import booking.system.dto.ServiceDTO;
import booking.system.modal.ServiceOffering;
import booking.system.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/service-offering/salon-owner")
@RequiredArgsConstructor
public class SalonServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @PostMapping
    public ResponseEntity <ServiceOffering> createService(
            @RequestBody ServiceDTO serviceDTO
            ){
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(serviceDTO.getCategory());

        ServiceOffering serviceOfferings = serviceOfferingService.createService(salonDTO,serviceDTO,categoryDTO);
        return ResponseEntity.ok(serviceOfferings);
    }

    @PostMapping("/{id}")
    public ResponseEntity <ServiceOffering> updateService(
           @PathVariable Long id,
           @RequestBody ServiceOffering serviceOffering
    ) throws Exception {
        ServiceOffering serviceOfferings = serviceOfferingService.updateService(id, serviceOffering);
        return ResponseEntity.ok(serviceOfferings);
    }
}
