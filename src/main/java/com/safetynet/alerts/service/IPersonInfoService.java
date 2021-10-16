package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.model.PersonInfo;

public interface IPersonInfoService {

	PersonInfoService setInfoUrl1(List<PersonInfo> listOfPersonsCoveredByFirestationDTO, Integer nbAdults,
			Integer nbChildren);
}
