package com.dairyproject.services;

import java.util.List;
import com.dairyproject.entities.AddressDetails;

public interface AddressService {

    AddressDetails getAddressDetailsByPincode(String pincode);
    
    List<AddressDetails> getAddressDetailsByDistrict(String district);
    
    AddressDetails getAddressDetailsByTown(String town);
    
    String deleteAddress(int aId);
}
