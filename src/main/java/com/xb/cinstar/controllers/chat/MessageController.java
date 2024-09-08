package com.xb.cinstar.controllers.chat;

import com.xb.cinstar.dto.CustomerDTO;
import com.xb.cinstar.dto.Message;
import com.xb.cinstar.dto.UserDTO;
import com.xb.cinstar.service.impl.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin("${allowed.origin}")
@RestController
@RequestMapping("/api/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody Message message)
    {

        Message result = messageService.save(message);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/{sender}/{receiver}")
    public ResponseEntity<?> getMessageByReceiverAndSender(@PathVariable String sender,@PathVariable String receiver)
    {

        List<Message> result = messageService.findAllBySenderAndReciever(sender,receiver);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserMessage(@PathVariable String username)
    {

        Set<UserDTO> result = messageService.findAlUserByMessage(username);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
