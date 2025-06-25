package com.bookingsystem.mapper;


import com.bookingsystem.modal.Salon;
import com.bookingsystem.payload.dto.SalonDTO;

public class SalonMapper {

    public static SalonDTO mapToDTO(Salon salon){
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(salon.getId());

        salonDTO.setName(salon.getName());
        salonDTO.setAddress(salon.getAddress());
        salonDTO.setCity(salon.getCity());
        salonDTO.setEmail(salon.getEmail());
        salonDTO.setImages(salon.getImages());
        salonDTO.setOpenTime(salon.getOpenTime());
        salonDTO.setCloseTime(salon.getCloseTime());
        salonDTO.setPhoneNumber(salon.getPhoneNumber());
        salonDTO.setOwnerId(salon.getOwnerId());

        return salonDTO;
    }
}
