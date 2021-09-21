package com.safetynet.alerts.utils;

import org.springframework.stereotype.Component;

import com.safetynet.alerts.dto.FirestationDTO;
import com.safetynet.alerts.model.Firestation;

@Component
public class FirestationMapper {

	public Firestation toFirestation(FirestationDTO firestationDTO) {
		return new Firestation(firestationDTO.getAddress(), firestationDTO.getStation());
	}

	public FirestationDTO toFirestationDTO(Firestation firestation) {
		return new FirestationDTO(firestation.getAddress(), firestation.getStation());
	}
}
