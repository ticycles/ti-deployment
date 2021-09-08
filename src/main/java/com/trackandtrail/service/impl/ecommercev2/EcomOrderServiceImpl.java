package com.trackandtrail.service.impl.ecommercev2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackandtrail.common.dto.BaseResponseDTO;
import com.trackandtrail.dto.PointBadgeDto;
import com.trackandtrail.dto.ecommercev2.EcomOrderDto;
import com.trackandtrail.dto.ecommercev2.OrderRequestDto;
import com.trackandtrail.exception.ResourceNotFoundException;
import com.trackandtrail.mapper.ecommercev2.EcomOrderMapper;
import com.trackandtrail.model.configuration.BadgeConfiguration;
import com.trackandtrail.model.configuration.BadgeEarned;
import com.trackandtrail.model.configuration.BadgeRewardConf;
import com.trackandtrail.model.configuration.PointsEarnedHistory;
import com.trackandtrail.model.ecommercev2.EcomOrderItems;
import com.trackandtrail.model.ecommercev2.EcomOrders;
import com.trackandtrail.model.ecommercev2.EcomProductVariant;
import com.trackandtrail.model.user.UserModel;
import com.trackandtrail.repository.BadgeConfigurationRepository;
import com.trackandtrail.repository.BadgeEarnedRepository;
import com.trackandtrail.repository.BadgeRewardConfRepository;
import com.trackandtrail.repository.OrderPointRepository;
import com.trackandtrail.repository.PointsEarnedHistoryRepository;
import com.trackandtrail.repository.UserRepository;
import com.trackandtrail.repository.ecommercev2.EcomCartRepository;
import com.trackandtrail.repository.ecommercev2.EcomOrdersItemsRepository;
import com.trackandtrail.repository.ecommercev2.EcomOrdersRepository;
import com.trackandtrail.repository.ecommercev2.EcomProductVariantRepository;
import com.trackandtrail.service.ecommercev2.EcomOrderService;
import com.trackandtrail.util.GenericUtils;
import com.trackandtrail.util.MathUtil;
import com.trackandtrail.util.RecordStatusUtil;
import com.trackandtrail.util.RequestStatusUtil;
import com.trackandtrail.util.StringUtils;
import com.trackandtrail.view.OrderPointCount;

@Service
public class EcomOrderServiceImpl implements EcomOrderService {

	@Autowired
	private EcomOrdersRepository ecomOrdersRepository;

	@Autowired
	BadgeEarnedRepository badgeEarnedRepo;

	@Autowired
	private EcomOrdersItemsRepository ecomOrderItem;

	@Autowired
	PointsEarnedHistoryRepository PointsEarnedHistoryrepo;

	@Autowired
	BadgeEarnedRepository badgeEarnedRepository;

	@Autowired
	BadgeConfigurationRepository badgeConfigRepo;

	@Autowired
	BadgeRewardConfRepository badgeRepo;

	@Autowired
	private EcomCartRepository ecomCartRepo;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	OrderPointRepository orderPointRepo;

	@Autowired
	private EcomProductVariantRepository pvRepo;

	@Autowired
	private UserRepository userRepo;
	
	

	@Override
	public BaseResponseDTO getAll() throws Exception {
		return GenericUtils.wrapOrEmptyList(ecomOrdersRepository.findAll());

	}

	@Override
	public BaseResponseDTO getById(Long userId) throws Exception {
		return GenericUtils.wrapOrEmptyList(ecomOrdersRepository.findByUserId(userId));

	}

//	@Override
//	public BaseResponseDTO changeStatusById(Long id, Integer statusId) throws Exception {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	@Transactional
	public BaseResponseDTO save(OrderRequestDto orderRequestDto) throws Exception {

		BaseResponseDTO resp = new BaseResponseDTO();

		String module = "shops";
		String moduleSlug = "items_purchased_per_day";

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
		String todayDate = formatter.format(date);

		EcomOrders order = new EcomOrders();

		Optional<UserModel> user = userRepo.findById(orderRequestDto.getUser().getId());

		order.setUser(user.get());
		order.setFirstName(orderRequestDto.getFirstName());
		order.setLastName(orderRequestDto.getLastName());
		order.setAddressType(orderRequestDto.getAddressType());
		order.setMobile(orderRequestDto.getMobile());
		order.setState(orderRequestDto.getState());
		order.setCity(orderRequestDto.getCity());
		order.setPinCode(orderRequestDto.getPinCode());
		order.setStreet(orderRequestDto.getStreet());
		order.setDoorNo(orderRequestDto.getDoorNo());
		order.setPaymentMethod(orderRequestDto.getPaymentMethod());
		order.setPaymentTransactionId(orderRequestDto.getPaymentTransactionId());
		order.setPaymentStatus(orderRequestDto.getPaymentStatus());
		order.setPaymentDeductedAmount(orderRequestDto.getPaymentDeductedAmount());
		order.setTotalAmount(orderRequestDto.getTotalAmount());
		order.setTaxType(orderRequestDto.getTaxType());
		order.setTaxPercentage(orderRequestDto.getTaxPercentage());
		order.setTaxAmount(orderRequestDto.getTaxAmount());
		order.setDiscount(orderRequestDto.getDiscount());
		order.setDiscountAmount(orderRequestDto.getDiscountAmount());
		order.setOrderStatus(1);
		order.setLandMark(orderRequestDto.getLandMark());
		String uuid = StringUtils.getAlphaNumericString();
		order.setOrderNo(uuid);
		List<EcomOrderItems> variantList = new ArrayList<EcomOrderItems>();

		orderRequestDto.getItems().forEach(item -> {
			Optional<EcomProductVariant> ev = pvRepo.findById(item.getVariantId());
			if (ev.isPresent()) {
				EcomOrderItems newItem = new EcomOrderItems();
				newItem.setProductVariant(ev.get());
				newItem.setOrders(order);
				newItem.setQuantity(item.getQuantity());
				newItem.setPrice(item.getPrice());
				newItem.setSubTotal(item.getSubTotal());
				newItem.setPlacedOn(new Date());
				newItem.setItemStatus(1);
				variantList.add(newItem);
			}
		});

		order.setItems(variantList);
		ecomOrderItem.saveAll(variantList);

		Long insertedId = order.getItems().size() > 0 ? ecomOrdersRepository.save(order).getId() : 0;

		String msg = insertedId > 0 ? "Order Place Successfully" : "Could not create order";

		if (insertedId > 0) {
			ecomCartRepo.deleteByUserId(orderRequestDto.getUser().getId());
		}

		OrderPointCount orderPoint = orderPointRepo.getOrderUserCount(orderRequestDto.getUser().getId(), todayDate);

		Optional<BadgeRewardConf> badge = badgeRepo.findBadgeRewardConfModule("shops");
		if (badge.isPresent()) {
			if (orderPoint.getOrderUserCount() > badge.get().getShopItemPerDay()) {

				Integer pt = MathUtil.calculateRewardPoints(badge.get().getShopItemPerDay(),
						orderPoint.getOrderUserCount(), badge.get().getShopPoints());

				Integer currentPoint = null;

				Optional<PointsEarnedHistory> ptHistory = PointsEarnedHistoryrepo
						.findMyEarnedPoints(orderRequestDto.getUser().getId(), module, moduleSlug, todayDate);

				if (ptHistory.isPresent()) {

					// remove
					PointsEarnedHistoryrepo.removeByUserIdAndModuleSlug(orderRequestDto.getUser().getId(), module,
							moduleSlug, todayDate);
					currentPoint = ((pt) - (ptHistory.get().getEarnPoint()));
				} else {
					currentPoint = pt -  badge.get().getShopPoints();
				}

				PointBadgeDto pointBadge = new PointBadgeDto();
				
				BadgeEarned earnBatch = new BadgeEarned();


				PointsEarnedHistory pointHistory = new PointsEarnedHistory();

				pointHistory.setUser(orderRequestDto.getUser());
				pointHistory.setModule(module);
				pointHistory.setModuleSlug(moduleSlug);
				pointHistory.setCreatedDate(new Date());
				pointHistory.setEarnPoint(pt);
				PointsEarnedHistoryrepo.save(pointHistory);

				OrderPointCount orderPoints = orderPointRepo.getOverallDistance(orderRequestDto.getUser().getId());

				Optional<BadgeConfiguration> userBadge = badgeConfigRepo.getUserBadge(orderPoints.getOrderUserCount(),
						"shops");
				if (userBadge.isPresent()) {

					Optional<BadgeEarned> badgeEarned = badgeEarnedRepo.findRange(userBadge.get().getRangeFrom(),
							userBadge.get().getRangeTo(), orderRequestDto.getUser().getId(),
							userBadge.get().getBatchName(), module);
					if (badgeEarned.isPresent()) {

						badgeEarnedRepo.deleteById(badgeEarned.get().getId());


						earnBatch.setBadgeName(userBadge.get().getBatchName());
						earnBatch.setDescription(userBadge.get().getDescription());
						earnBatch.setModule(module);
						earnBatch.setImage(userBadge.get().getImage());
						earnBatch.setRangeFrom(userBadge.get().getRangeFrom());
						earnBatch.setRangeTo(userBadge.get().getRangeTo());
						earnBatch.setUser(orderRequestDto.getUser());
//				earnBatch.setEarnPoint(( activity.getAverageDistance());
						badgeEarnedRepository.save(earnBatch);
					} else {


						earnBatch.setBadgeName(userBadge.get().getBatchName());
						earnBatch.setDescription(userBadge.get().getDescription());
						earnBatch.setModule(module);
						earnBatch.setImage(userBadge.get().getImage());
						earnBatch.setRangeFrom(userBadge.get().getRangeFrom());
						earnBatch.setRangeTo(userBadge.get().getRangeTo());
						earnBatch.setUser(orderRequestDto.getUser());
//						earnBatch.setEarnPoint(( activity.getAverageDistance());
						badgeEarnedRepository.save(earnBatch);

					}
				}
				pointHistory.setEarnPoint(currentPoint);
				pointBadge.setPoint(pointHistory);
				resp.setExtras(pointBadge);
				pointBadge.setBadge(earnBatch);
				resp.setMsg(msg);
				resp.setResponseContent(order);
				resp.setErrorCode(RequestStatusUtil.CREATED_RESPONSE.getErrorCode());
				resp.setMsg(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription());
				return resp;
			}
		}

		resp.setMsg(msg);
		resp.setResponseContent(order);
		return resp;

	}

	@Override
	public BaseResponseDTO changeStatusById(Long id, Integer statusId) throws Exception {
		Optional<EcomOrderItems> ecomOrders = ecomOrderItem.findById(id);
		BaseResponseDTO responseDTO = new BaseResponseDTO();

		if (ecomOrders.isPresent()) {
			switch (statusId) {
			case 2:
				ecomOrders.get().setItemStatus(RecordStatusUtil.RECORD_DELETED);
				BaseResponseDTO resp = new BaseResponseDTO();
				resp.setMsg("Order is Processing");
				responseDTO = resp;
				break;
			case 0:
				ecomOrders.get().setItemStatus(RecordStatusUtil.RECORD_INACTIVE);
				BaseResponseDTO rp = new BaseResponseDTO();
				rp.setMsg("Order Confirmed");
				responseDTO = rp;
				break;
			case 1:
				ecomOrders.get().setItemStatus(RecordStatusUtil.RECORD_ACTIVE);
				BaseResponseDTO bresp = new BaseResponseDTO();
				bresp.setMsg("Order Placed");
				responseDTO = bresp;
				break;
			case 3:
				ecomOrders.get().setItemStatus(RecordStatusUtil.RETURN);
				BaseResponseDTO baseresp = new BaseResponseDTO();
				baseresp.setMsg("Order Shipped");
				responseDTO = baseresp;
				break;
			case 4:
				ecomOrders.get().setItemStatus(RecordStatusUtil.DELIVERED);
				BaseResponseDTO response = new BaseResponseDTO();
				response.setMsg("Order Delivered");
				responseDTO = response;
				break;
			default:
				ecomOrders.get().setItemStatus(RecordStatusUtil.RECORD_INACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.INACTIVE.getErrorDescription(),
						RequestStatusUtil.INACTIVE.getErrorCode());
				break;
			}
			ecomOrderItem.save(ecomOrders.get());
		} else {
			throw new ResourceNotFoundException("No order items found for given Id.");
		}
		return responseDTO;
	}
	
	@Override
	public BaseResponseDTO changeStatusByOrderId(Long id, Integer statusId) throws Exception {
		Optional<EcomOrders> ecomOrders = ecomOrdersRepository.findById(id);
		BaseResponseDTO responseDTO = new BaseResponseDTO();

		if (ecomOrders.isPresent()) {
			switch (statusId) {
			case 2:
				ecomOrders.get().setOrderStatus(RecordStatusUtil.RECORD_DELETED);
				BaseResponseDTO resp = new BaseResponseDTO();
				resp.setMsg("Order is Processing");
				responseDTO = resp;
				break;
			case 0:
				ecomOrders.get().setOrderStatus(RecordStatusUtil.RECORD_INACTIVE);
				BaseResponseDTO rp = new BaseResponseDTO();
				rp.setMsg("Order Confirmed");
				responseDTO = rp;
				break;
			case 1:
				ecomOrders.get().setOrderStatus(RecordStatusUtil.RECORD_ACTIVE);
				BaseResponseDTO bresp = new BaseResponseDTO();
				bresp.setMsg("Order Placed");
				responseDTO = bresp;
				break;
			case 3:
				ecomOrders.get().setOrderStatus(RecordStatusUtil.RETURN);
				BaseResponseDTO baseresp = new BaseResponseDTO();
				baseresp.setMsg("Order Shipped");
				responseDTO = baseresp;
				break;
			case 4:
				ecomOrders.get().setOrderStatus(RecordStatusUtil.DELIVERED);
				BaseResponseDTO response = new BaseResponseDTO();
				response.setMsg("Order Delivered");
				responseDTO = response;
				break;
			default:
				ecomOrders.get().setOrderStatus(RecordStatusUtil.RECORD_INACTIVE);
				responseDTO = new BaseResponseDTO(RequestStatusUtil.INACTIVE.getErrorDescription(),
						RequestStatusUtil.INACTIVE.getErrorCode());
				break;
			}
			ecomOrdersRepository.save(ecomOrders.get());
		} else {
			throw new ResourceNotFoundException("No order items found for given Id.");
		}
		return responseDTO;
	}
//
//	@Override
//	public BaseResponseDTO save(OrderRequestDto orderRequestDto) throws Exception {
//		ecomOrdersRepository.save(objectMapper.convertValue(orderRequestDto, EcomOrders.class));
//		return new BaseResponseDTO(RequestStatusUtil.CREATED_RESPONSE.getErrorDescription(),
//				RequestStatusUtil.CREATED_RESPONSE.getErrorCode());	}

	@Override
	public BaseResponseDTO getByUserId(Long userId) throws Exception {
		return GenericUtils.wrapOrEmptyList(ecomOrderItem.findByOrderItemsByUserId(userId));

	}

	@Override
	public BaseResponseDTO getByOrderItemId(Long orderItemId) throws Exception {
		return GenericUtils.wrapOrNotFound(ecomOrderItem.findById(orderItemId));

	}

	@Override
	public BaseResponseDTO getAllOrderItems() throws Exception {
		return GenericUtils.wrapOrEmptyList(ecomOrderItem.findByAllOrderItems());

	}

	@Override
	public BaseResponseDTO update(EcomOrderDto ecomOrderDto) throws Exception {
		BaseResponseDTO baseResponseDTO = null;
		Optional<EcomOrders> ecomOrders= ecomOrdersRepository.findById(ecomOrderDto.getId());
		if (ecomOrders.isPresent()) {
			EcomOrderMapper.toEcomOrder(ecomOrderDto, ecomOrders.get());
			ecomOrdersRepository.save(ecomOrders.get());
			baseResponseDTO = new BaseResponseDTO(RequestStatusUtil.UPDATED_RESPONSE.getErrorDescription(),
				RequestStatusUtil.UPDATED_RESPONSE.getErrorCode());
		} else {
			throw new ResourceNotFoundException("No order cancel for given id.");
		}
		return baseResponseDTO;
	}

}
