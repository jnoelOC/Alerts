package com.safetynet.alerts.utils;

import org.springframework.stereotype.Component;

import com.safetynet.alerts.dto.ChildInfoDTO;
import com.safetynet.alerts.model.ChildInfo;

@Component
public class ChildInfoMapper {
	public ChildInfo toChildInfo(ChildInfoDTO childInfoDTO) {

		ChildInfo childInfo = null;

		childInfo = new ChildInfo(childInfoDTO.getChildFirstname(), childInfoDTO.getChildLastname(),
				childInfoDTO.getAgeOfChild(), childInfoDTO.getListOfAdultsAtHome());

		return childInfo;

	}

	public ChildInfoDTO toChildInfoDTO(ChildInfo childInfo) {
		ChildInfoDTO childInfoDTO = null;
//		try {
		childInfoDTO = new ChildInfoDTO(childInfo.getChildFirstname(), childInfo.getChildLastname(),
				childInfo.getAgeOfChild(), childInfo.getListOfAdultsAtHome());
//		} catch (NullPointerException e) {
//			// logger.error("Error null pointer : ", e);
//		} catch (Exception ex) {
//			// logger.error("Error general purpose : ", ex);
//		}

		return childInfoDTO;
	}
}
