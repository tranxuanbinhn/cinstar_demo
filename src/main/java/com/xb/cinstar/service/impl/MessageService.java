package com.xb.cinstar.service.impl;

import com.xb.cinstar.dto.Message;
import com.xb.cinstar.dto.UserDTO;
import com.xb.cinstar.exception.ResourceNotFoundException;
import com.xb.cinstar.models.MessageModel;
import com.xb.cinstar.models.UserModel;
import com.xb.cinstar.repository.IMessageRespository;
import com.xb.cinstar.repository.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired private IMessageRespository iMessageRespository;
    @Autowired private IUserRepository iUserRepository;
    @Autowired private ModelMapper mapper;

    public Message save (Message message)
    {
        if(!iUserRepository.existsByUserName(message.getReceiverName()) || !iUserRepository.existsByUserName(message.getSenderName()))
        {
            throw new ResourceNotFoundException("Not found this user");
        }
        MessageModel messageModel = mapper.map(message, MessageModel.class);
        UserModel sender = iUserRepository.findByUserName(message.getSenderName()).get();
        UserModel receiver = iUserRepository.findByUserName(message.getReceiverName()).get();

        messageModel.setSender(sender);
        messageModel.setReceiver(receiver);
       return mapper.map(iMessageRespository.save(messageModel), Message.class);

    }
    public List<Message> findAllBySenderAndReciever (String sender, String receiver)
    {
        if(!iUserRepository.existsByUserName(sender) || !iUserRepository.existsByUserName(receiver))
        {
            throw new ResourceNotFoundException("Not found this user");
        }

        UserModel senderUser = iUserRepository.findByUserName(sender).get();
        UserModel receiverUser = iUserRepository.findByUserName(receiver).get();
        List<MessageModel> messageModels = iMessageRespository.findAllByReceiverAndSender(senderUser.getId(),receiverUser.getId() );

        return messageModels.stream().map(MessageModel -> {
            Message message = mapper.map(MessageModel, Message.class);
            message.setSenderName(MessageModel.getSender().getUserName());
            message.setReceiverName(MessageModel.getReceiver().getUserName());
            message.setDate(MessageModel.getCreateDate());
            return message;
        }).collect(Collectors.toList());
    }
    public Set<UserDTO> findAlUserByMessage(String username)
    {
        if(!iUserRepository.existsByUserName(username))
        {
            throw new ResourceNotFoundException("Not found this user");
        }

        UserModel user = iUserRepository.findByUserName(username).get();

        List<MessageModel> messageModels = iMessageRespository.findAllByUser(user.getId());
        HashSet<UserDTO> result = new HashSet<>();
         messageModels.stream().forEach(MessageModel -> {

            UserModel userModel1= MessageModel.getSender();
            UserModel userModel2 = MessageModel.getReceiver();

                result.add(mapper.map(userModel1, UserDTO.class));
                result.add(mapper.map(userModel2, UserDTO.class));





        });
        result.removeIf(userDTO -> userDTO.getUserName().equals(username));
         return  result;
    }
}
