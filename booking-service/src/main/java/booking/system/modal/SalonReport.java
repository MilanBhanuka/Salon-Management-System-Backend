package booking.system.modal;

import lombok.Data;

@Data
public class SalonReport {
    private Long salonId;
    private String salonName;
    private int totalEarnings;
    private Integer totalBookings;
    private Integer cancelledBooking;
    private Double totalRefund;
}
