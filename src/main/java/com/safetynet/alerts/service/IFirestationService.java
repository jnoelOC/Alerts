package com.safetynet.alerts.service;

import java.util.List;

import com.safetynet.alerts.dto.FirestationDTO;

public interface IFirestationService {

	public List<FirestationDTO> getAllFirestations();

	public FirestationDTO getOneFirestation(String station, String address);

	public List<FirestationDTO> getSeveralFirestations(String station);

	public FirestationDTO updateOneFirestation(FirestationDTO firestationDTO);

	public boolean deleteOneFirestation(FirestationDTO firestationDTO);

	public FirestationDTO addFirestation(FirestationDTO firestationDTO);
}
