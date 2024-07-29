package com.xb.cinstar.service.impl;

import com.xb.cinstar.dto.PaymentDTO;
import com.xb.cinstar.exception.ResourceNotFoundException;
import com.xb.cinstar.models.OrderModel;
import com.xb.cinstar.models.PaymentModel;
import com.xb.cinstar.repository.IOrderRepository;
import com.xb.cinstar.repository.IPaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PaymentService {
    @Autowired
    private IPaymentRepository paymentRepository;
    private IOrderRepository orderRepository;

    @Autowired private ModelMapper mapper;

    public PaymentDTO save(PaymentDTO paymentDTO)
    {
        try{
            PaymentModel paymentModel = new PaymentModel();

            paymentModel = mapper.map(paymentDTO, PaymentModel.class);
            OrderModel orderModel = orderRepository.findById(paymentDTO.getOrderId())
                    .orElseThrow(()->new ResourceNotFoundException("Not found this order"));
            paymentModel.setOrder(orderModel);
            paymentModel = paymentRepository.save(paymentModel);
            PaymentDTO result = mapper.map(paymentModel, PaymentDTO.class);
            result.setOrderId(paymentModel.getOrder().getId());
            return result;
        }
        catch (ResourceNotFoundException e)
        {
            throw new  ResourceNotFoundException("Not found order");
        }
    }


    public boolean delete(Long id) {
        try{
            paymentRepository.deleteById(id);
            return  true;
        }
        catch (ResourceNotFoundException e)
        {
            throw new ResourceNotFoundException("Not found this Theater");

        }

    }

    public List<PaymentDTO> findAll() throws ResourceNotFoundException{
        List<PaymentModel> theaters = paymentRepository.findAll();
        List<PaymentDTO> result = new ArrayList<>();
        theaters.stream().forEach(payment->{
                    PaymentDTO paymentDTO = new PaymentDTO();
                    paymentDTO = mapper.map(payment, PaymentDTO.class);
                    paymentDTO.setOrderId(payment.getOrder().getId());
                    result.add(paymentDTO);
                }
        );
        return result;

    }

    public List<PaymentDTO> findAll(Pageable pageable)
    {
        Page<PaymentModel> theaters = paymentRepository.findAll(pageable);
        List<PaymentDTO> result = new ArrayList<>();
        theaters.getContent().stream().forEach(theater->{
                    PaymentDTO paymentDTO = new PaymentDTO();
                    paymentDTO = mapper.map(theater, PaymentDTO.class);
                    result.add(paymentDTO);
                }
        );
        return result;
    }

}
