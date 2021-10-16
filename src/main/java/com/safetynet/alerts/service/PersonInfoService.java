package com.safetynet.alerts.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.model.PersonInfo;
import com.safetynet.alerts.utils.PersonInfoMapper;

@Service
public class PersonInfoService implements IPersonInfoService {
	public static final Logger logger = LogManager.getLogger(PersonInfoService.class);
	private PersonInfoMapper personInfoMapper = new PersonInfoMapper();

	List<PersonInfoDTO> listOfPersonInfoDTO = new ArrayList<>();
	Integer nbOfAdults;
	Integer nbOfChildren;

	public PersonInfoService() {
		// constructor
	}

	public PersonInfoService setInfoUrl1(List<PersonInfo> listOfPersonsCoveredByFirestation, Integer nbAdults,
			Integer nbChildren) {

		PersonInfoService pis = null;
		try {

			pis = new PersonInfoService();
			for (PersonInfo onePersonInfo : listOfPersonsCoveredByFirestation) {
				pis.listOfPersonInfoDTO.add(personInfoMapper.toPersonInfoDTO(onePersonInfo));
			}
			pis.nbOfAdults = nbAdults;
			pis.nbOfChildren = nbChildren;
		} catch (Exception ex) {

			logger.error(MessageFormat.format("Error in setInfo() : {0}.", ex.getMessage()));
		}
		return pis;
	}

	public List<PersonInfoDTO> getListOfPersonInfoDTO() {
		return listOfPersonInfoDTO;
	}

	public void setListOfPersonInfoDTO(List<PersonInfoDTO> listOfPersonInfoDTO) {
		this.listOfPersonInfoDTO = listOfPersonInfoDTO;
	}

	public Integer getNbOfAdults() {
		return nbOfAdults;
	}

	public void setNbOfAdults(Integer nbOfAdults) {
		this.nbOfAdults = nbOfAdults;
	}

	public Integer getNbOfChildren() {
		return nbOfChildren;
	}

	public void setNbOfChildren(Integer nbOfChildren) {
		this.nbOfChildren = nbOfChildren;
	}

}
