package booking.system.mapper;

import booking.system.dto.BookingDTO;
import booking.system.dto.SalonDTO;
import booking.system.dto.ServiceDTO;
import booking.system.dto.UserDTO;
import booking.system.modal.Booking;

import java.util.Set;

public class BookingMapper {
    public static BookingDTO toDTO(Booking booking,
                                   Set<ServiceDTO> services,
                                   SalonDTO salon,
                                   UserDTO user
    ) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setCustomerId(booking.getCustomerId());
        bookingDTO.setStatus(booking.getStatus());
        bookingDTO.setEndTime(booking.getEndTime());
        bookingDTO.setStartTime(booking.getStartTime());
        bookingDTO.setSalonId(booking.getSalonId());
        bookingDTO.setServiceIds(booking.getServiceIds());
        bookingDTO.setTotalPrice(booking.getTotalPrice());

        bookingDTO.setServices(services);
        bookingDTO.setUser(user);
        bookingDTO.setSalon(salon);


        return bookingDTO;
    }
}
