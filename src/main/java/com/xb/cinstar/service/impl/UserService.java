package com.xb.cinstar.service.impl;

import com.xb.cinstar.dto.UserDTO;
import com.xb.cinstar.exception.*;
import com.xb.cinstar.models.ERole;
import com.xb.cinstar.models.RoleModel;
import com.xb.cinstar.models.UserModel;
import com.xb.cinstar.payload.response.MessageResponse;
import com.xb.cinstar.repository.IRoleRepository;
import com.xb.cinstar.repository.IUserRepository;
import com.xb.cinstar.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service

public class UserService implements IUserService {
    @Autowired private IUserRepository userRepository;
    @Autowired private IRoleRepository roleRepository;
    @Autowired private ModelMapper mapper;
    @Autowired private PasswordEncoder passwordEncoder;
    @Override
    public UserDTO save(UserDTO userDTO) {
    UserModel userModel = new UserModel();
        Optional<RoleModel> roleModel = roleRepository.findByName(userDTO.getRole());
        if(!roleModel.isPresent())
        {
            throw new ResourceNotFoundException("Not found this role");
        }
        if(userDTO.getId()==null)
        {

            if (userRepository.existsByUserName(userDTO.getUserName())) {
                throw  new InvalidRequestException("Username exist");
            }

            if (userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())) {
                throw  new InvalidRequestException("phone number exist ");
            }

            if (userRepository.existsByCic(userDTO.getCic())) {
                throw  new InvalidRequestException("cic exist");
            }

            if (userRepository.existsByEmail(userDTO.getEmail())) {
                throw new InvalidRequestException("email exist");
            }




        }
        else
        {
            if(!userRepository.existsById(userDTO.getId()))
            {
                throw  new ResourceNotFoundException("Not found user");
            }
            userModel= userRepository.findById(userDTO.getId()).get();

            if(!userModel.getUserName().equals(userDTO.getUserName()))
            {
                if (userRepository.existsByUserName(userDTO.getUserName())) {
                    throw  new ResourceNotFoundException("Username exist");
                }
            }
            if(!userModel.getEmail().equals(userDTO.getEmail()))
            {
                if (userRepository.existsByEmail(userDTO.getEmail())) {
                    throw  new ResourceNotFoundException("email exist");
                }
            }
            if(!userModel.getCic().equals(userDTO.getCic()))
            {
                if (userRepository.existsByCic(userDTO.getCic())) {
                    throw  new ResourceNotFoundException("cic exist");
                }
            }
            if(!userModel.getPhoneNumber().equals(userDTO.getPhoneNumber()))
            {
                if (userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())) {
                    throw  new ResourceNotFoundException("phone number exist");
                }
            }

        }
        userModel = mapper.map(userDTO, UserModel.class);
        Set<RoleModel> roleModels = new HashSet<>();
        roleModels.add(roleModel.get());
        userModel.setRoles(roleModels);
        userModel = userRepository.save(userModel);
        UserDTO result = mapper.map(userModel, UserDTO.class);
        List<ERole> roleNames = userModel.getRoles().stream()
                .map(RoleModel::getName)
                .collect(Collectors.toList());
        result.setRole(roleNames.get(0));
        return result;
    }

    @Override
    public boolean delete(Long id) {


        try{
            Optional<UserModel> userModel = userRepository.findById(id);
            if(!userModel.isPresent())
            {
                throw new ResourceNotFoundException("Not found user");
}
            userRepository.delete(userModel.get());
            return  true;

        }catch (RuntimeException e)
        {

            return  false;
        }

    }

    public UserDTO update(UserDTO userDTO)
    {
        Optional<UserModel> userModel = userRepository.findByUserName(userDTO.getUserName());
        if(!userModel.isPresent())
        {
            throw new ResourceNotFoundException("not found user");

        }
        userModel.get().setBirthDay(userDTO.getBirthDay());
        if(!userModel.get().getEmail().equals(userDTO.getEmail()))
        {
            if (userRepository.existsByEmail(userDTO.getEmail())) {
                throw  new InvalidRequestException("email exist");
            }
        }
        userModel.get().setEmail(userDTO.getEmail());
        userModel.get().setFullName(userDTO.getFullName());
        if(!userModel.get().getPhoneNumber().equals(userDTO.getPhoneNumber()))
        {
            if (userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())) {
                throw  new InvalidRequestException("phone number exist");
            }
        }
        userModel.get().setPhoneNumber(userDTO.getPhoneNumber());
        UserModel userModel1 = userRepository.save(userModel.get());
        return mapper.map(userModel1, UserDTO.class);

    }
    public UserDTO changePassword(UserDTO userDTO)
    {
        Optional<UserModel> userModel = userRepository.findByUserName(userDTO.getUserName());
        if(!userModel.isPresent())
        {
            throw new ResourceNotFoundException("not found user");

        }
        if(!passwordEncoder.matches(userDTO.getOldPassword(), userModel.get().getPassword()))
        {
            throw  new InvalidRequestException("Invalid password");
        }
        userModel.get().setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserModel userModel1 = userRepository.save(userModel.get());

        return mapper.map(userModel1, UserDTO.class);

    }
    @Override
    public boolean update(Long id) {
        return false;
    }

    @Override
    public List<UserDTO> findAll() {
        return null;
    }

    public UserDTO findById(String userName) {

        Optional<UserModel> userModel = userRepository.findByUserName(userName);
        if(!userModel.isPresent())
        {
            throw new ResourceNotFoundException( "Not found user");
        }

        UserDTO userDTO = new UserDTO();
        userDTO = mapper.map(userModel.get(), UserDTO.class);
        List<ERole> role = userModel.get().getRoles().stream().map(RoleModel::getName).collect(Collectors.toList());
        userDTO.setRole(role.get(0));
        return userDTO;
    }
    public UserDTO findUser(Long id) {

        Optional<UserModel> userModel = userRepository.findById(id);
        if(!userModel.isPresent())
        {
            throw new ResourceNotFoundException( "Not found user");
        }

        UserDTO userDTO = new UserDTO();
        userDTO = mapper.map(userModel.get(), UserDTO.class);
        List<ERole> role = userModel.get().getRoles().stream().map(RoleModel::getName).collect(Collectors.toList());
        userDTO.setRole(role.get(0));
        return userDTO;
    }

    public long count() {
        return  userRepository.count();
    }
    @Override
    public List<UserDTO> findAll(Pageable pageable) {
        Page<UserModel> list = userRepository.findAll(pageable);
        List<UserDTO> result = new ArrayList<>();

        list.stream().forEach(user -> {
            UserDTO userDTO = new UserDTO();
            userDTO = mapper.map(user, UserDTO.class);
            List<ERole> role = user.getRoles().stream().map(RoleModel::getName).collect(Collectors.toList());
            userDTO.setRole(role.get(0));
            result.add(userDTO);
        });
        return result;
    }
}
