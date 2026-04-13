package com.apex.ecommerce.service.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apex.ecommerce.exception.ResourceNotFoundException;
import com.apex.ecommerce.model.Address;
import com.apex.ecommerce.model.User;
import com.apex.ecommerce.payload.AddressDTO;
import com.apex.ecommerce.repositories.AddressRepo;
import com.apex.ecommerce.repositories.UserRepo;
import com.apex.ecommerce.service.AddressService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepo addressRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    UserRepo userRepository;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO, User user) {

        // ✅ Re-fetch user inside the transaction so address collection can be lazily loaded
        User managedUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", user.getUserId()));
        
        Address address = modelMapper.map(addressDTO, Address.class);
        address.setUser(managedUser);
        List<Address> addressesList = managedUser.getAddress();
        addressesList.add(address);
        managedUser.setAddress(addressesList);
        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .toList();
    }

    @Override
    public AddressDTO getAddressesById(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));
        return modelMapper.map(address, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getUserAddresses(User user) {
    	
    	// ✅ Re-fetch user inside the transaction so address collection can be lazily loaded
        User managedUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", user.getUserId()));

        List<Address> addresses = managedUser.getAddress();
        return addresses.stream()
                .map(address -> modelMapper.map(address, AddressDTO.class))
                .toList();
    }

    @Override
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
        Address addressFromDatabase = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        addressFromDatabase.setCity(addressDTO.getCity());
        addressFromDatabase.setPincode(addressDTO.getPincode());
        addressFromDatabase.setState(addressDTO.getState());
        addressFromDatabase.setCountry(addressDTO.getCountry());
        addressFromDatabase.setStreet(addressDTO.getStreet());
        addressFromDatabase.setBuilding(addressDTO.getBuilding());

        Address updatedAddress = addressRepository.save(addressFromDatabase);

        User user = addressFromDatabase.getUser();
        user.getAddress().removeIf(address -> address.getAddressId().equals(addressId));
        user.getAddress().add(updatedAddress);
        userRepository.save(user);

        return modelMapper.map(updatedAddress, AddressDTO.class);
    }

    @Override
    public String deleteAddress(Long addressId) {
        Address addressFromDatabase = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        User user = addressFromDatabase.getUser();
        user.getAddress().removeIf(address -> address.getAddressId().equals(addressId));
        userRepository.save(user);

        addressRepository.delete(addressFromDatabase);

        return "Address deleted successfully with addressId: " + addressId;
    }
}
