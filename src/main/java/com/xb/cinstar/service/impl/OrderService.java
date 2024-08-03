package com.xb.cinstar.service.impl;


import com.xb.cinstar.dto.OrderDTO;
import com.xb.cinstar.exception.ResourceNotFoundException;

import com.xb.cinstar.models.*;
import com.xb.cinstar.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IFoodRespository foodRespository;
    @Autowired
    private ITicketOrderRespository ticketOrderRespository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ICustomerRespository customerRespository;

    @Autowired
    private IPromotionRespository promotionRespository;



    @Autowired
    private ModelMapper mapper;

    public OrderDTO save(OrderDTO orderDTO) {

        OrderModel orderModel = new OrderModel();
        if (orderDTO.getUserId() != null) {
            UserModel userModel = userRepository.findById(orderDTO.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("Not found user"));
            orderModel.setUser(userModel);

        }
        if (orderDTO.getCustomerId() != null) {
            CustomerModel customerModel = customerRespository.findById(orderDTO.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Not found customer"));
            orderModel.setCustomer(customerModel);
        }

        TicketOrderModel ticketOrderModel = ticketOrderRespository.findById(orderDTO.getTicketorderId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found this ticket"));

        BigDecimal foodPrice = BigDecimal.ZERO;
        PromotionModel promotionModel =  new PromotionModel();
        BigDecimal totalTicketPrice = BigDecimal.ZERO;
        if(orderDTO.getPromationId()!=null)
        {
             promotionModel = promotionRespository.findById(orderDTO.getPromationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Not found this promotion"));
            for(TicketModel ticketModel:ticketOrderModel.getTickets())
            {
                totalTicketPrice = totalTicketPrice.add(promotionModel.getValue().multiply(BigDecimal.valueOf(ticketModel.getQuantity())));
            }
        }
        else
        {
            totalTicketPrice = ticketOrderModel.getTickets().stream()
                    .map(ticketModel -> ticketModel.getPrice().multiply(BigDecimal.valueOf(ticketModel.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }


        if (ticketOrderModel.getFoods() != null) {
           foodPrice = ticketOrderModel.getFoods().stream().map(foodModel ->
                   foodModel.getPrice().multiply(BigDecimal.valueOf(foodModel.getQuantity())))
                   .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
            BigDecimal total = totalTicketPrice.add(foodPrice);

            orderModel.setTicketorder(ticketOrderModel);
            orderModel.setTotalPrice(total);

            orderModel = orderRepository.save(orderModel);
            OrderDTO result = new OrderDTO();
            result.setTotalPrice(orderModel.getTotalPrice());
            if(orderModel.getCustomer()!=null)
            {
                result.setCustomerId(orderModel.getCustomer().getId());
            }
        if(orderModel.getUser()!=null)
        {
            result.setUserId(orderModel.getUser().getId());

        }

            result.setTicketorderId(orderModel.getTicketorder().getId());
            return result;

        }


    public List<OrderDTO> findAll(Pageable pageable)
    {
        Page<OrderModel> orders = orderRepository.findAll(pageable);
        List<OrderDTO> result = new ArrayList<>();
        orders.getContent().stream().forEach(orderModel->{
                    OrderDTO orderDTO = new OrderDTO();
            orderDTO.setTotalPrice(orderModel.getTotalPrice());
            if(orderModel.getCustomer()!=null)
            {
                orderDTO.setCustomerId(orderModel.getCustomer().getId());
            }
            if(orderModel.getUser()!=null)
            {
                orderDTO.setUserId(orderModel.getUser().getId());
            }

            orderDTO.setTicketorderId(orderModel.getTicketorder().getId());
                    result.add(orderDTO);
                }
        );
        return result;
    }


    public List<OrderDTO> findAllByUserId(Pageable pageable, String userName)
    {
       UserModel userModel = userRepository.findByUserName(userName)
               .orElseThrow(()->new ResourceNotFoundException("Not found user"));
        Page<OrderModel> orders = orderRepository.findAllByUserId(userModel.getId(),pageable);
        List<OrderDTO> result = new ArrayList<>();
        orders.getContent().stream().forEach(orderModel->{
                    OrderDTO orderDTO = new OrderDTO();
                    orderDTO.setTotalPrice(orderModel.getTotalPrice());
                    if(orderModel.getCustomer()!=null)
                    {
                        orderDTO.setCustomerId(orderModel.getCustomer().getId());
                    }
                    if(orderModel.getUser()!=null)
                    {
                        orderDTO.setUserId(orderModel.getUser().getId());
                    }

                    orderDTO.setTicketorderId(orderModel.getTicketorder().getId());
                    result.add(orderDTO);
                }
        );
        return result;
    }

    public boolean delete(Long id) {
        try{
            orderRepository.deleteById(id);
            return  true;
        }
        catch (ResourceNotFoundException e)
        {
            throw new ResourceNotFoundException("Not found this Theater");

        }

    }
    }

