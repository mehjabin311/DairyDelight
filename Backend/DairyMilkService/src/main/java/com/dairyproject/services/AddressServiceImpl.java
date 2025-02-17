package com.dairyproject.services;

import java.util.List;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dairyproject.entities.AddressDetails;
import com.dairyproject.repositories.AddressRepository;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addRepo;

    @Override
    public AddressDetails getAddressDetailsByPincode(String pincode) {
        return addRepo.findAddressDetailsByPincode(pincode);
    }

    @Override
    public List<AddressDetails> getAddressDetailsByDistrict(String district) {
        return addRepo.findAddressDetailsByDistrict(district);
    }

    @Override
    public AddressDetails getAddressDetailsByTown(String town) {
        return addRepo.findAddressDetailsByTown(town);
    }
    
    @Override
    public String deleteAddress(int aId) {
    	String msg = "Address deleted failed";
     	int count = addRepo.deleteAddressById(aId);
     	if(count == 1) {
     		System.out.println("deleted address of id " + aId);
     	}
     	msg = "deleted address of id";
		return msg;
    }
}
