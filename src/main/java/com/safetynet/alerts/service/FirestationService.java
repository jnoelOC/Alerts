package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.FirestationDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.repository.IFirestationRepository;
import com.safetynet.alerts.utils.FirestationMapper;

@Service
public class FirestationService implements IFirestationService {

	public static final Logger logger = LogManager.getLogger(FirestationService.class);

	@Autowired
	UrlService us;
	@Autowired
	IFirestationRepository firestationRepository;

	private FirestationMapper firestationMapper = new FirestationMapper();

	public List<FirestationDTO> getAllFirestations() {
		List<Firestation> listNotDto = firestationRepository.findAllFirestations();
		List<FirestationDTO> listFirestationDto = new ArrayList<>();

		for (Firestation firestation : listNotDto) {
			FirestationDTO firestationDTO = firestationMapper.toFirestationDTO(firestation);
			listFirestationDto.add(firestationDTO);
		}
		return listFirestationDto;
	}

	public FirestationDTO addFirestation(FirestationDTO firestationDTO) {
		FirestationDTO fDTO;
		Firestation firestation = firestationMapper.toFirestation(firestationDTO);
		Firestation f = this.firestationRepository.save(firestation);

		if (f == null) {
			fDTO = null;
		} else {
			fDTO = firestationMapper.toFirestationDTO(f);
		}
		return fDTO;
	}

	public FirestationDTO getOneFirestation(String station, String address) {

		Firestation firestation = this.firestationRepository.readAFirestation(station, address);
		return firestationMapper.toFirestationDTO(firestation);
	}

	public List<FirestationDTO> getSeveralFirestations(String station) {
		List<Firestation> listNotDto = this.firestationRepository.readSeveralFirestations(station);
		List<FirestationDTO> listFirestationDto = new ArrayList<>();

		for (Firestation firestation : listNotDto) {
			FirestationDTO firestationDTO = firestationMapper.toFirestationDTO(firestation);
			listFirestationDto.add(firestationDTO);
		}
		return listFirestationDto;
	}

	public FirestationDTO updateOneFirestation(FirestationDTO firestationDTO) {
		Firestation firestation = firestationMapper.toFirestation(firestationDTO);
		Firestation f = firestationRepository.updateAFirestation(firestation);
		return firestationMapper.toFirestationDTO(f);
	}

	public boolean deleteOneFirestation(FirestationDTO firestationDTO) {
		Firestation firestation = firestationMapper.toFirestation(firestationDTO);
		return firestationRepository.deleteAFirestation(firestation);
	}

}
