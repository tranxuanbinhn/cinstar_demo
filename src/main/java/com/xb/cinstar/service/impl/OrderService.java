package com.xb.cinstar.service.impl;


import com.xb.cinstar.dto.OrderDTO;
import com.xb.cinstar.dto.TicketOrderDTO;
import com.xb.cinstar.dto.TicketResponse;
import com.xb.cinstar.exception.ResourceNotFoundException;

import com.xb.cinstar.models.*;
import com.xb.cinstar.repository.*;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

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
    private IPaymentRepository iPaymentRepository;

    @Autowired
    private IPromotionRespository promotionRespository;

    @Autowired
    private ITicketRespository iTicketRespository;

    @Autowired
    private IFoodRelationRepository foodRelationRepository;

    @Autowired
    private ITicketRelationRepository iTicketRelationRepository;



    @Autowired
    private ModelMapper mapper;

    public OrderDTO save(OrderDTO orderDTO) {

        OrderModel orderModel = new OrderModel();
        orderModel.setOrdercode(String.valueOf(System.currentTimeMillis()));
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

            orderModel.setTicketorder(ticketOrderModel);
            orderModel.setTotalPrice(orderDTO.getTotalPrice());
            orderModel.setStatus(false);
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
        result.setOrdercode(orderModel.getOrdercode());
        result.setId(orderModel.getId());
            return result;

        }

    public  Long count()
    {
        return orderRepository.count();
    }
    public List<OrderDTO> findAll(Pageable pageable)
    {
        Page<OrderModel> orders = orderRepository.findAll(pageable);
        List<OrderDTO> result = new ArrayList<>();
        orders.getContent().stream().forEach(orderModel->{
                    OrderDTO orderDTO = new OrderDTO();
                    orderDTO = mapper.map(orderModel, OrderDTO.class);
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
            orderDTO.setPaymentId(orderModel.getPayment().getId());
                    result.add(orderDTO);
                }
        );
        return result;
    }

    public TicketResponse findTicketResponseByOrderid(Long id)
    {
        Optional<OrderModel> orderModel = orderRepository.findById(id);
        if(!orderModel.isPresent())
        {
            throw new ResourceNotFoundException("Not found this ordermodel");

        }
       TicketOrderModel ticketOrderModel = orderModel.get().getTicketorder();
        List<FoodRelation>foodRelations = ticketOrderModel.getFoods();
        List<String> foods = new ArrayList<>();
        if(foodRelations.size()>0)
        {
            foods = foodRelations.stream().map(FoodRelation->FoodRelation.getFood().getName()).collect(Collectors.toList());
        }
        List<TicketRelation> ticketRelations= ticketOrderModel.getTickets();
       List<String> numberSeats = ticketRelations.stream().map(TicketRelation->TicketRelation.getSeat().getName()).collect(Collectors.toList());
        TicketModel ticketModel = ticketRelations.get(0).getTicket();
        ShowTimeModel showTimeModel = ticketRelations.get(0).getShowtime();
        TheaterModel theaterModel = showTimeModel.getTheater();
        MovieModel movieModel = showTimeModel.getMovie();
        ScreenModel screenModel = showTimeModel.getScreen();
        TicketResponse ticketResponse = new TicketResponse(showTimeModel.getDate(),showTimeModel.getTime(),
                movieModel.getTitle(), theaterModel.getName(), theaterModel.getAddress(),
                numberSeats, foods, orderModel.get().getTotalPrice(), orderModel.get().getOrdercode(),
                movieModel.getPosterPath(),screenModel.getName());
        return ticketResponse;

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
            orderDTO.setPaymentId(orderModel.getPayment().getId());
                    orderDTO.setTicketorderId(orderModel.getTicketorder().getId());
                    result.add(orderDTO);
                }
        );
        return result;
    }
    @Transactional
    public boolean delete(Long id) {
        try{
            OrderModel orderModel = orderRepository.findById(id).get();
           CustomerModel customerModel = orderModel.getCustomer();

            TicketOrderModel ticketOrderModel = orderModel.getTicketorder();
            List<FoodRelation> foodRelations = new ArrayList<>();
            if(ticketOrderModel.getFoods().size()>0)
            {
                foodRelations = ticketOrderModel.getFoods();
                foodRelationRepository.deleteAll(foodRelations);
            }
           List<TicketRelation> ticketRelations = ticketOrderModel.getTickets();
           customerRespository.delete(customerModel);
           iTicketRelationRepository.deleteAll(ticketRelations);



            return  true;
        }
        catch (ResourceNotFoundException e)
        {
            throw new ResourceNotFoundException("Not found this Order");

        }

    }
    public  List<OrderDTO> findAllByUsername (String username) {
        Optional<UserModel> userModel = userRepository.findByUserName(username);
        if(!userModel.isPresent())
        {
            throw  new ResourceNotFoundException("Not found this user");
        }
        List<OrderModel> orderModels = orderRepository.findAllByUserId(userModel.get().getId());
        return  orderModels.stream().map(orderModel -> (mapper.map(orderModel, OrderDTO.class)
        )
        ).collect(Collectors.toList());

    }

    }

