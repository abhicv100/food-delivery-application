package com.bits.pilani.delivery_service.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bits.pilani.delivery_service.constant.DeliveryConstants;
import com.bits.pilani.delivery_service.dao.DeliveryDao;
import com.bits.pilani.delivery_service.entity.DeliveryDetailsEntity;
import com.bits.pilani.delivery_service.to.DeliveryTO;
import com.bits.pilani.exception.CustomException;

@Service
public class DeliveryService {

	@Autowired
	DeliveryDao deliveryDao;

	public DeliveryTO getDeliveryByOrderId(int orderId) throws CustomException {

		DeliveryTO daoTO = new DeliveryTO();

		try {
			Optional<DeliveryDetailsEntity> deliveryDetail = deliveryDao.findById(orderId);

			if (deliveryDetail != null) {
				daoTO.setDelivered(deliveryDetail.get().getDelivered());
				daoTO.setDelivery_accepted(deliveryDetail.get().getDelivery_accepted());
				daoTO.setDelivery_message(deliveryDetail.get().getDelivery_message());
				daoTO.setDelivery_person_id(deliveryDetail.get().getDelivery_person_id());
				daoTO.setOrder_id(deliveryDetail.get().getOrder_id());
			}
		} catch (NoSuchElementException e) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid request: " + DeliveryConstants.INVALID_CONFIG);
		}

		return daoTO;
	}

	public List<DeliveryTO> getAllDeliveryDetails() throws CustomException {
		try {
			return deliveryDao.findAll().stream().map((deliveryEntity) -> {
				DeliveryTO deliveryTO = new DeliveryTO();
				BeanUtils.copyProperties(deliveryEntity, deliveryTO);
				return deliveryTO;
			}).toList();
		} catch (DataAccessException e) {
			throw CustomException.INTERNAL_SERVER_ERRROR;

		}
	}

	public DeliveryTO newDeliveryDetails(DeliveryTO deliveryTO) throws CustomException {
		try {
			DeliveryDetailsEntity deliveryEntity = new DeliveryDetailsEntity();
			BeanUtils.copyProperties(deliveryTO, deliveryEntity);
			deliveryEntity = deliveryDao.save(deliveryEntity);
			BeanUtils.copyProperties(deliveryEntity, deliveryTO);
		} catch (IllegalArgumentException  e) {
			throw CustomException.INTERNAL_SERVER_ERRROR;
		}
		return deliveryTO;
	}

	public DeliveryTO updateDeliveryByOrderId(DeliveryTO deliveryTO, int orderId) throws CustomException {

		try {
			Optional<DeliveryDetailsEntity> deliveryDetail = deliveryDao.findById(orderId);
			if (deliveryDetail != null) {
				DeliveryDetailsEntity deliveryEntity = new DeliveryDetailsEntity();
				BeanUtils.copyProperties(deliveryTO, deliveryEntity);
				deliveryEntity = deliveryDao.save(deliveryEntity);
				BeanUtils.copyProperties(deliveryEntity, deliveryTO);
			}

		} catch (DataAccessException e) {
			throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid request: " + DeliveryConstants.INVALID_CONFIG);
		}
		return deliveryTO;
	}

}
