package com.safetynet.alerts.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.model.MedicalRecord;

@Repository
public class MedicalRecordRepository implements IMedicalRecordRepository {

	public static final Logger logger = LogManager.getLogger(MedicalRecordRepository.class);

	private List<MedicalRecord> medicalrecords = new ArrayList<>();

	@Override
	public MedicalRecord save(MedicalRecord medicalrecord) {
		medicalrecords.add(medicalrecord);
		return medicalrecord;
	}

	@Override
	public List<MedicalRecord> findAllMedicalRecords() {
		return medicalrecords;
	}

	@Override
	public MedicalRecord readAMedicalRecord(String firstname, String lastname) {

		Iterator<MedicalRecord> iteratorMedicalRecords = medicalrecords.iterator();

		try {
			Integer numMedicalRecord = 0;
			while (iteratorMedicalRecords.hasNext()) {

				if (medicalrecords.get(numMedicalRecord).getFirstName().equalsIgnoreCase(firstname)
						&& medicalrecords.get(numMedicalRecord).getLastName().equalsIgnoreCase(lastname)) {

					return medicalrecords.get(numMedicalRecord);
				}

				numMedicalRecord++;
				iteratorMedicalRecords.next();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public MedicalRecord updateAMedicalRecord(MedicalRecord medicalrecord) {
		Iterator<MedicalRecord> iteratorMedicalRecords = medicalrecords.iterator();

		try {
			Integer numMedicalRecord = 0;
			while (iteratorMedicalRecords.hasNext()) {

				if (medicalrecords.get(numMedicalRecord).getFirstName().equalsIgnoreCase(medicalrecord.getFirstName())
						&& medicalrecords.get(numMedicalRecord).getLastName()
								.equalsIgnoreCase(medicalrecord.getLastName())) {

					medicalrecords.get(numMedicalRecord).setBirthDate(medicalrecord.getBirthDate());
					medicalrecords.get(numMedicalRecord).setMedications(medicalrecord.getMedications());
					medicalrecords.get(numMedicalRecord).setAllergies(medicalrecord.getAllergies());

					return medicalrecords.get(numMedicalRecord);
				}

				numMedicalRecord++;
				iteratorMedicalRecords.next();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteAMedicalRecord(MedicalRecord medicalrecord) {
		Iterator<MedicalRecord> iteratorMedicalRecords = medicalrecords.iterator();
		boolean isRemoved = false;
		try {
			Integer numMedicalRecord = 0;
			while (iteratorMedicalRecords.hasNext()) {

				if (medicalrecords.get(numMedicalRecord).getFirstName().equalsIgnoreCase(medicalrecord.getFirstName())
						&& medicalrecords.get(numMedicalRecord).getLastName()
								.equalsIgnoreCase(medicalrecord.getLastName())) {

					medicalrecords.remove(medicalrecords.get(numMedicalRecord));
					isRemoved = true;
					break;

				} else {
					numMedicalRecord++;
					iteratorMedicalRecords.next();
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return isRemoved;
	}

}
