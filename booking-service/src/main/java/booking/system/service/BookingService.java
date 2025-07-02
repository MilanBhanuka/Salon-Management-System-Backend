package booking.system.service;

import booking.system.domain.BookingStatus;
import booking.system.dto.BookingRequest;
import booking.system.dto.SalonDTO;
import booking.system.dto.ServiceDTO;
import booking.system.dto.UserDTO;
import booking.system.modal.Booking;
import booking.system.modal.PaymentOrder;
import booking.system.modal.SalonReport;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface BookingService {

    Booking createBooking(BookingRequest booking,
                          UserDTO userDTO,
                          SalonDTO salonDTO,
                          Set<ServiceDTO> serviceDTOSet) throws Exception;

    List<Booking> getBookingsByCustomer(Long customerId);

    List<Booking> getBookingsBySalon(Long salonId);

    Booking getBookingById(Long id) throws Exception;

    Booking updateBooking(Long bookingId, BookingStatus status) throws Exception;

    List<Booking> getBookingsByDate(LocalDate date, Long salonId);

    SalonReport getSalonReport(Long salonId);


    Booking bookingSuccess(PaymentOrder order) throws Exception;

}
