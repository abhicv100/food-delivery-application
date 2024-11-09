package com.bits.pilani.delivery_service.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import com.bits.pilani.delivery_service.constant.DeliveryConstants;
import com.bits.pilani.delivery_service.controller.DeliveryServiceController;
import com.bits.pilani.delivery_service.dao.DeliveryDao;
import com.bits.pilani.delivery_service.entity.DeliveryDetailsEntity;
import com.bits.pilani.delivery_service.service.DeliveryService;
import com.bits.pilani.delivery_service.to.DeliveryTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters=false)
@WebMvcTest(DeliveryServiceController.class)
public class DeliveryServiceTest {
	
	@Autowired
	protected MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private DeliveryDao deliveryDao;
	
	@SpyBean
	private DeliveryService service;
	
	@Test
	@Order(1)
    void testGetDeliveryByOrderId_Success() throws Exception {
        int orderId = 1;
        DeliveryDetailsEntity mockEntity = new DeliveryDetailsEntity();
        mockEntity.setDelivered(true);
        mockEntity.setDelivery_accepted(true);
        mockEntity.setDelivery_message("Delivered Successfully");
        mockEntity.setDelivery_person_id(1001);
        mockEntity.setOrder_id(orderId);
        when(deliveryDao.findById(orderId)).thenReturn(Optional.of(mockEntity));

        DeliveryTO result = service.getDeliveryByOrderId(orderId);

        assertNotNull(result);
        verify(service, times(1)).getDeliveryByOrderId(Mockito.anyInt());
    }

    @Test
    @Order(2)
    void testGetDeliveryByOrderId_NoSuchElementException() throws Exception {
        int orderId = 1;

        when(deliveryDao.findById(orderId)).thenThrow(new NoSuchElementException());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
        	service.getDeliveryByOrderId(orderId);
        });
        assertEquals("Invalid request: " + DeliveryConstants.INVALID_CONFIG, exception.getReason());
        verify(service, times(1)).getDeliveryByOrderId(Mockito.anyInt());
    }
    
    @Test
	@Order(3)
    void testgetAllDeliveryDetails() {
        int orderId = 1;
        DeliveryDetailsEntity mockEntity = new DeliveryDetailsEntity();
        mockEntity.setDelivered(true);
        mockEntity.setDelivery_accepted(true);
        mockEntity.setDelivery_message("Delivered Successfully");
        mockEntity.setDelivery_person_id(1001);
        mockEntity.setOrder_id(orderId);
        when(deliveryDao.findAll()).thenReturn(List.of(mockEntity));

        List<DeliveryTO> result = service.getAllDeliveryDetails();

        assertNotNull(result);
        verify(service, times(1)).getAllDeliveryDetails();
    }
    
    @Test
  	@Order(4)
      void testNewDeliveryDetails() {
          int orderId = 1;
          DeliveryDetailsEntity mockEntity = new DeliveryDetailsEntity();
          mockEntity.setDelivered(true);
          mockEntity.setDelivery_accepted(true);
          mockEntity.setDelivery_message("Delivered Successfully");
          mockEntity.setDelivery_person_id(1001);
          mockEntity.setOrder_id(orderId);          
          doReturn(mockEntity).when(deliveryDao).save(Mockito.any());

          DeliveryTO mockDeliveryTO = new DeliveryTO();
          mockDeliveryTO.setOrder_id(orderId);
          mockDeliveryTO.setDelivered(true);
          mockDeliveryTO.setDelivery_accepted(true);
          mockDeliveryTO.setDelivery_person_id(1001);
          mockDeliveryTO.setDelivery_message("Delivered Successfully"); 
          DeliveryTO result = service.newDeliveryDetails(mockDeliveryTO);

          assertNotNull(result);
          verify(service, times(1)).newDeliveryDetails(Mockito.any());
      }
    
    @Test
  	@Order(5)
      void testUpdateDeliveryByOrderId() throws Exception {
          int orderId = 1;
          DeliveryDetailsEntity mockEntity = new DeliveryDetailsEntity();
          mockEntity.setDelivered(true);
          mockEntity.setDelivery_accepted(true);
          mockEntity.setDelivery_message("Delivered Successfully");
          mockEntity.setDelivery_person_id(1001);
          mockEntity.setOrder_id(orderId);    
          when(deliveryDao.findById(orderId)).thenReturn(Optional.of(mockEntity));
          doReturn(mockEntity).when(deliveryDao).save(Mockito.any());

          DeliveryTO mockDeliveryTO = new DeliveryTO();
          mockDeliveryTO.setOrder_id(orderId);
          mockDeliveryTO.setDelivered(true);
          mockDeliveryTO.setDelivery_accepted(true);
          mockDeliveryTO.setDelivery_person_id(1001);
          mockDeliveryTO.setDelivery_message("Delivered Successfully"); 
          DeliveryTO result = service.updateDeliveryByOrderId(mockDeliveryTO,orderId);

          assertNotNull(result);
          verify(service, times(1)).updateDeliveryByOrderId(Mockito.any(),Mockito.anyInt());
      }


    @Test
    @Order(6)
    void testUpdateDeliveryByOrderId_NoSuchElementException() throws Exception {
        int orderId = 1;
        DeliveryTO mockDeliveryTO = new DeliveryTO();
        mockDeliveryTO.setOrder_id(orderId);
        mockDeliveryTO.setDelivered(true);
        mockDeliveryTO.setDelivery_accepted(true);
        mockDeliveryTO.setDelivery_person_id(1001);
        mockDeliveryTO.setDelivery_message("Delivered Successfully"); 

        when(deliveryDao.findById(orderId)).thenThrow(new DataAccessException("Database error") {});
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
        	service.updateDeliveryByOrderId(mockDeliveryTO,orderId);
        });
        assertEquals("Invalid request: " + DeliveryConstants.INVALID_CONFIG, exception.getReason());
        verify(service, times(1)).updateDeliveryByOrderId(Mockito.any(),Mockito.anyInt());
    }

}
