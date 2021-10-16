package com.safetynet.alerts.utils;

import org.springframework.stereotype.Component;

import com.safetynet.alerts.dto.PersonInfoDTO;
import com.safetynet.alerts.model.PersonInfo;

@Component
public class PersonInfoMapper {
	public PersonInfo toPersonInfo(PersonInfoDTO personInfoDTO) {

		PersonInfo personInfo = null;
		try {
			personInfo = new PersonInfo(personInfoDTO.getFirstName(), personInfoDTO.getLastName(),
					personInfoDTO.getAddress(), personInfoDTO.getPhone());
		} catch (NullPointerException e) {
			// logger.error("Error null pointer : " + e);
		} catch (Exception ex) {
			// logger.error("Error general purpose : ", ex);
		}

		return personInfo;

	}

	public PersonInfoDTO toPersonInfoDTO(PersonInfo personInfo) {
		PersonInfoDTO personInfoDTO = null;
		try {
			personInfoDTO = new PersonInfoDTO(personInfo.getFirstName(), personInfo.getLastName(),
					personInfo.getAddress(), personInfo.getPhone());
		} catch (NullPointerException e) {
			// logger.error("Error null pointer : ", e);
		} catch (Exception ex) {
			// logger.error("Error general purpose : ", ex);
		}

		return personInfoDTO;
	}
}
