package booking.system.controller;

import booking.system.dto.CategoryDTO;
import booking.system.dto.SalonDTO;
import booking.system.dto.ServiceDTO;
import booking.system.modal.ServiceOffering;
import booking.system.service.ServiceOfferingService;
import booking.system.service.client.CategoryFeignClient;
import booking.system.service.client.SalonFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/service-offering/salon-owner")
@RequiredArgsConstructor
public class SalonServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;
    private final SalonFeignClient salonFeignClient;
    private final CategoryFeignClient categoryFeignClient;

    @PostMapping
    public ResponseEntity <ServiceOffering> createService(
            @RequestBody ServiceDTO serviceDTO,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        SalonDTO salonDTO = salonFeignClient.getSalonByOwnerId(jwt).getBody();

        CategoryDTO categoryDTO = categoryFeignClient.getCategoriesByIdAndSalon(serviceDTO.getCategory(),salonDTO.getId()).getBody();

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
