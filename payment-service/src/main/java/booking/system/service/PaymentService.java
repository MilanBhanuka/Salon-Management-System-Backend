package booking.system.service;

import booking.system.domain.PaymentMethod;
import booking.system.modal.PaymentOrder;
import booking.system.payload.dto.BookingDTO;
import booking.system.payload.dto.UserDTO;
import booking.system.payload.response.PaymentLinkResponse;
import com.razorpay.PaymentLink;
import com.stripe.exception.StripeException;


public interface PaymentService {
    PaymentLinkResponse createOrder(UserDTO user,
                                    BookingDTO booking,
                                    PaymentMethod paymentMethod) throws StripeException;

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    PaymentOrder getPaymentOrderByPaymentId(String paymentId);

//    PaymentLink createRazorpayPaymentLink(UserDTO user,
//                                          Long amount,
//                                          Long orderId);

    String createStripePaymentLink(UserDTO user,
                                   Long amount,
                                   Long orderId) throws StripeException;

    Boolean proceedPayment(PaymentOrder paymentOrder,String paymentId,String paymentLinkId);
}
