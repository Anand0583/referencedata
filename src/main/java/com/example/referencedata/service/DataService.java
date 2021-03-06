package com.example.referencedata.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.example.referencedata.dao.DelivererClient;
import com.example.referencedata.dao.DeliveryChannelClient;
import com.example.referencedata.dao.DeliveryMomentClient;
import com.example.referencedata.dao.DeliveryStreamClient;
import com.example.referencedata.dao.LogisticChannelClient;
import com.example.referencedata.dao.entity.Deliverer;
import com.example.referencedata.dao.entity.DeliveryChannel;
import com.example.referencedata.dao.entity.DeliveryMoment;
import com.example.referencedata.dao.entity.DeliveryStream;
import com.example.referencedata.dao.entity.LogisticChannel;
import com.example.referencedata.mapper.ReferenceDataServiceMapper;

public class DataService {

	@Inject
	private ReferenceDataServiceMapper referenceDataServiceMapper;

	@Inject
	DelivererClient delivererClient;

	@Inject
	DeliveryChannelClient deliveryChannelClient;

	@Inject
	DeliveryStreamClient deliveryStreamClient;

	@Inject
	LogisticChannelClient logisticChannelClient;
	
	@Inject
	DeliveryMomentClient deliveryMomentClient;

	public List<com.example.referencedata.domain.Deliverer> findDelivererByValue(String findByValue) {
		List<Deliverer> delivererEntityList = delivererClient.findByFilterValue("delivererNumber", findByValue)
				.blockingGet();
		return referenceDataServiceMapper.mapperDelivererEntityToDomain(delivererEntityList);
	}

	public List<com.example.referencedata.domain.Deliverer> findAllDeliverer() {
		List<Deliverer> delivererEntityList = delivererClient.findAll().blockingGet();
		return referenceDataServiceMapper.mapperDelivererEntityToDomain(delivererEntityList);
	}

	public List<com.example.referencedata.domain.DeliveryChannel> findAllDeliveryChannel() {
		List<DeliveryChannel> deliveryChannelEntityList = deliveryChannelClient.findAll().blockingGet();
		return referenceDataServiceMapper.mapperDeliveryChannelEntityToDomain(deliveryChannelEntityList);
	}
	
	
	public List<com.example.referencedata.domain.DeliveryChannel> findDeliveryChannel(String filterValue) {
		List<DeliveryChannel> deliveryChannelEntityList = deliveryChannelClient.findByFilterValue("storeNumber", new Integer(filterValue)).blockingGet();
		return referenceDataServiceMapper.mapperDeliveryChannelEntityToDomain(deliveryChannelEntityList);
	}

	public List<com.example.referencedata.domain.LogisticChannel> findLogisticChannel() {
		List<LogisticChannel> logisticChannelEntityList = logisticChannelClient.findAll().blockingGet();
		return referenceDataServiceMapper.mapperLogisticChannelEntityToDomain(logisticChannelEntityList);
	}

	public List<com.example.referencedata.domain.DeliveryStream> findDeliveryStream() {
		List<DeliveryStream> deliveryStreamEntityList = deliveryStreamClient.findAll().blockingGet();
		return referenceDataServiceMapper.mapperDeliveryStreamEntityToDomain(deliveryStreamEntityList);
	}
	
	public List<com.example.referencedata.domain.DeliveryStream> findDeliveryStream(String streamNumber) {
		List<DeliveryStream> deliveryStreamEntityList = deliveryStreamClient.findByFilterValue("streamNumber", streamNumber).blockingGet();
		return referenceDataServiceMapper.mapperDeliveryStreamEntityToDomain(deliveryStreamEntityList);
	}

	
	public List<com.example.referencedata.domain.DeliveryMoment> findDeliveryMomment(int storeNumber,String streamNumber,String delivererNumber) {
		Map<String,Object> filterNameValueMap = new HashMap<String,Object>();
		filterNameValueMap.put("storenumber", new Integer(storeNumber));
		if(streamNumber != null)filterNameValueMap.put("streamnumber",new Integer(streamNumber));
		if(delivererNumber != null) filterNameValueMap.put("delivererNumber",new Integer(delivererNumber));
		List deliveryMomentListEntity = (List<DeliveryMoment>) deliveryMomentClient.filterNameValueMap(filterNameValueMap).blockingGet();
		return  referenceDataServiceMapper.mapperDeliveryMomentEntityToDomain(deliveryMomentListEntity);
	}
}
