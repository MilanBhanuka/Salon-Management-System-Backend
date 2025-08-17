package booking.system.service;

import booking.system.dto.CategoryDTO;
import booking.system.dto.SalonDTO;
import booking.system.dto.ServiceDTO;
import booking.system.modal.ServiceOffering;

import java.util.Set;

public interface ServiceOfferingService {

    ServiceOffering createService(
            SalonDTO salonDTO,
            ServiceDTO serviceDTO,
            CategoryDTO categoryDTO);

    ServiceOffering updateService(Long serviceId,ServiceOffering service) throws Exception;

    Set<ServiceOffering> getAllServiceBySalonId(Long salonId, Long categoryId);

    Set<ServiceOffering> getServicesByIds(Set<Long> ids);

    ServiceOffering getServiceById(long id) throws Exception;

    void deleteService(Long id, Long salonId) throws Exception;
}
