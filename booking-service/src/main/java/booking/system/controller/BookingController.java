package booking.system.controller;

import booking.system.domain.BookingStatus;
import booking.system.domain.PaymentMethod;
import booking.system.dto.*;
import booking.system.mapper.BookingMapper;
import booking.system.modal.Booking;
import booking.system.modal.SalonReport;
import booking.system.service.BookingService;
import booking.system.service.client.PaymentFeignClient;
import booking.system.service.client.SalonFeignClient;
import booking.system.service.client.ServiceOfferingFeignClient;
import booking.system.service.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final SalonFeignClient salonFeignClient;
    private final UserFeignClient userFeignClient;
    private final ServiceOfferingFeignClient serviceOfferingFeignClient;
    private final PaymentFeignClient paymentFeignClient;

    @PostMapping
    public ResponseEntity<PaymentLinkResponse> createBooking(
            @RequestParam Long salonId,
            @RequestParam PaymentMethod paymentMethod,
            @RequestBody BookingRequest bookingRequest,
            @RequestHeader("Authorization") String jwt
            ) throws Exception {
        if (!jwt.startsWith("Bearer ")) {
            throw new Exception("Invalid token format. Token must start with 'Bearer '");
        }

        ResponseEntity<UserDTO> userResponse = userFeignClient.getUserProfile(jwt);
        if (userResponse == null || userResponse.getBody() == null) {
            throw new Exception("Failed to fetch user profile");
        }

        UserDTO user = userResponse.getBody();
        if (user.getId() == null) {
            throw new Exception("User ID is missing from the profile. User: " + user);
        }

        ResponseEntity<SalonDTO> salonResponse = salonFeignClient.getSalonById(salonId);
        if (salonResponse == null || salonResponse.getBody() == null) {
            throw new Exception("Salon not found with ID: " + salonId);
        }
        SalonDTO salon = salonResponse.getBody();
        Set<ServiceDTO> serviceDTOSet = serviceOfferingFeignClient.getServicesBySalonIds(bookingRequest.getServiceIds()).getBody();

        if(serviceDTOSet.isEmpty()){
            throw new Exception("No services found for the given service IDs");
        }

        Booking booking = bookingService.createBooking(bookingRequest,user,salon,serviceDTOSet);
        Set<ServiceDTO> services = serviceOfferingFeignClient.getServicesBySalonIds(booking.getServiceIds()).getBody();
        UserDTO customer = userFeignClient.getUserById(booking.getCustomerId()).getBody();

        BookingDTO bookingDTO = BookingMapper.toDTO(booking, services, salon, customer);

        PaymentLinkResponse res = paymentFeignClient.createPaymentLink(bookingDTO,paymentMethod,jwt).getBody();

        return ResponseEntity.ok(res);
    }

    @GetMapping("/customer")
    public ResponseEntity<Set<BookingDTO>> getBookingsByCustomer(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();
        if(user == null){
            throw new Exception("User not found from JWT");
        }
        List<Booking> bookings = bookingService.getBookingsByCustomer(user.getId());
        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    @GetMapping("/salon")
    public ResponseEntity<Set<BookingDTO>> getBookingsBySalon(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        SalonDTO salonDTO =salonFeignClient.getSalonByOwnerId(jwt).getBody();
        List<Booking> bookings = bookingService.getBookingsBySalon(salonDTO.getId());

        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    private Set<BookingDTO> getBookingDTOs(List<Booking> bookings){
        return bookings.stream().map(booking -> {
            Set<ServiceDTO> services = serviceOfferingFeignClient.getServicesBySalonIds(booking.getServiceIds()).getBody();
            SalonDTO salon;
            UserDTO user;
            try {
                salon = salonFeignClient.getSalonById(booking.getSalonId()).getBody();
                user = userFeignClient.getUserById(booking.getCustomerId()).getBody();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return BookingMapper.toDTO(booking, services,salon, user);
        }).collect(Collectors.toSet());
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity <BookingDTO> getBookingsById(
            @PathVariable Long bookingId
    ) throws Exception {
        Booking booking = bookingService.getBookingById(bookingId);
        Set<ServiceDTO> services = serviceOfferingFeignClient.getServicesBySalonIds(booking.getServiceIds()).getBody();
        UserDTO customer = userFeignClient.getUserById(booking.getCustomerId()).getBody();
        SalonDTO salon = salonFeignClient.getSalonById(booking.getSalonId()).getBody();

        return ResponseEntity.ok(BookingMapper.toDTO(booking,services,salon, customer));
    }

    @PutMapping("/{bookingId}/status")
    public ResponseEntity <BookingDTO> getBookingsById(
            @PathVariable Long bookingId,
            @RequestParam BookingStatus status
            ) throws Exception {
        Booking booking = bookingService.updateBooking(bookingId,status);
        Set<ServiceDTO> services = serviceOfferingFeignClient.getServicesBySalonIds(booking.getServiceIds()).getBody();
        UserDTO customer = userFeignClient.getUserById(booking.getCustomerId()).getBody();
        SalonDTO salon = salonFeignClient.getSalonById(booking.getSalonId()).getBody();
        return ResponseEntity.ok(BookingMapper.toDTO(booking, services, salon, customer));
    }

    @GetMapping("/slot/salon/{salonId}/date/{date}")
    public ResponseEntity <List<BookingSlotDTO>> getBookedSlot(
            @PathVariable Long salonId,
            @RequestParam(required = false) LocalDate date
    ) throws Exception {
        List<Booking> bookings = bookingService.getBookingsByDate(date,salonId);

        List<BookingSlotDTO> slotDTOS = bookings.stream()
                .map(booking -> {
                    BookingSlotDTO slotDTO = new BookingSlotDTO();
                    slotDTO.setStartTime(booking.getStartTime());
                    slotDTO.setEndTime(booking.getEndTime());
                    return slotDTO;
                }).collect(Collectors.toList());

        return ResponseEntity.ok(slotDTOS);
    }

    @GetMapping("/report")
    public ResponseEntity <SalonReport> getSalonReport(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        SalonDTO salonDTO =salonFeignClient.getSalonByOwnerId(jwt).getBody();
        SalonReport report = bookingService.getSalonReport(salonDTO.getId());

        return ResponseEntity.ok(report);
    }

}
