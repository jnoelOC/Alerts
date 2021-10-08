package com.safetynet.alerts.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.MedicalRecord;

@Repository
public class MedicalRecordRepository implements IMedicalRecordRepository {

	public static final Logger logger = LogManager.getLogger(MedicalRecordRepository.class);

	private List<MedicalRecord> medicalrecords = new ArrayList<>();

	public MedicalRecord save(MedicalRecord medicalrecord) {

		try {
			boolean mrFound = false;
			if (medicalrecord == null) {
				mrFound = true;
			} else {

				for (MedicalRecord oneMedicalRecord : medicalrecords) {

					if (oneMedicalRecord.getFirstName().equalsIgnoreCase(medicalrecord.getFirstName())
							&& oneMedicalRecord.getLastName().equalsIgnoreCase(medicalrecord.getLastName())) {

						mrFound = true;
						break;
					}
				}
			}
			if (!mrFound) {
				medicalrecords.add(medicalrecord);
				return medicalrecord;
			} else { // ALREADY CREATED
				return null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public List<MedicalRecord> findAllMedicalRecords() {
		return medicalrecords;
	}

	public MedicalRecord readAMedicalRecord(String firstname, String lastname) {

		try {
			for (MedicalRecord oneMedicalRecord : medicalrecords) {

				if (oneMedicalRecord.getFirstName().equalsIgnoreCase(firstname)
						&& oneMedicalRecord.getLastName().equalsIgnoreCase(lastname)) {

					return oneMedicalRecord;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public MedicalRecord updateAMedicalRecord(MedicalRecord medicalrecord) {

		try {
			for (MedicalRecord oneMedicalRecord : medicalrecords) {

				if (oneMedicalRecord.getFirstName().equalsIgnoreCase(medicalrecord.getFirstName())
						&& oneMedicalRecord.getLastName().equalsIgnoreCase(medicalrecord.getLastName())) {

					oneMedicalRecord.setBirthDate(medicalrecord.getBirthDate());
					oneMedicalRecord.setMedications(medicalrecord.getMedications());
					oneMedicalRecord.setAllergies(medicalrecord.getAllergies());

					return oneMedicalRecord;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteAMedicalRecord(MedicalRecord medicalrecord) {

		boolean isRemoved = false;
		try {
			for (MedicalRecord oneMedicalRecord : medicalrecords) {

				if (oneMedicalRecord.getFirstName().equalsIgnoreCase(medicalrecord.getFirstName())
						&& oneMedicalRecord.getLastName().equalsIgnoreCase(medicalrecord.getLastName())) {

					isRemoved = medicalrecords.remove(oneMedicalRecord);
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return isRemoved;
	}

}
