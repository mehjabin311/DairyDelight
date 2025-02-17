package com.dairyproject.services;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

import com.dairyproject.dto.ChangePasswordDTO;
import com.dairyproject.dto.LoginDTO;
import com.dairyproject.dto.SellerDTO;
import com.dairyproject.dto.SellerProductsDTO;
import com.dairyproject.entities.ProductDetails;
import com.dairyproject.entities.SellerDetails;
import com.dairyproject.exceptions.EmailAddressFoundException;
import com.dairyproject.exceptions.IncorrectPasswordException;
import com.dairyproject.exceptions.PhoneNumberFoundException;
import com.dairyproject.exceptions.ProductNotFoundException;
import com.dairyproject.exceptions.SellerNotFoundException;
import com.dairyproject.exceptions.UnmatchedPasswordException;
import com.dairyproject.exceptions.UsernameFoundException;

public interface SellerService {
    SellerDetails registerNewSeller(SellerDetails sellerDetails) 
            throws EmailAddressFoundException, UsernameFoundException, PhoneNumberFoundException;

    SellerDetails getSellerDetailsByEmailAndPassword(String emailId, String password) 
            throws UnsupportedEncodingException;

    SellerDetails getSellerDetailsByUsernameAndPassword(String username, String password);

    SellerDetails getSellerDetailsByPhoneNumberAndPassword(String phoneNumber, String password);

    SellerDetails getSellerDetailsByEmailId(String emailId);

    SellerDetails getSellerDetailsByUsername(String username);

    SellerDetails getSellerDetailsByPhoneNumer(String phoneNumber);

    String deleteSellerDetailsByEmailId(LoginDTO login) throws UnsupportedEncodingException;

    String deleteSellerDetailsBySellerId(int sellerId);
    
    
    List<SellerDetails> getAllSellerList();

    List<SellerDetails> getSellerDetailsByFirstName(String firstName);

    List<SellerDetails> getSellerListByPincode(String pincode);

    List<SellerDetails> getSellerListByDistrict(String district);

    List<SellerDetails> getSellerListByTown(String town);

    Set<SellerDetails> getSellerListByLocality(String emailId);

    Set<SellerDTO> getSellersByProductAndLocality(String emailId, String productName);

    SellerDetails updateSellerDetails(SellerDetails sellerDetails);

    String changeSellerPassword(ChangePasswordDTO changePassword) throws UnsupportedEncodingException, IncorrectPasswordException, UnmatchedPasswordException;

    Set<ProductDetails> addProducts(SellerProductsDTO sellerProducts)throws ProductNotFoundException, SellerNotFoundException;

    Set<ProductDetails> getSellerAllProductDetails(String emailId)throws SellerNotFoundException;

    List<SellerDetails> getSellersByProductName(String name);

    String removeProductFromList(String emailId, Integer pid) throws ProductNotFoundException;

	int deleteSellerDetailsByEmailIdOnly(String emailId);
}
