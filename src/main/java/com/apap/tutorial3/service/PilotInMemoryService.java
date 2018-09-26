package com.apap.tutorial3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.apap.tutorial3.model.PilotModel;

@Service
public class PilotInMemoryService implements PilotService {
	private List<PilotModel> archivePilot;
	
	public PilotInMemoryService() {
		archivePilot = new ArrayList<>();
	}
	
	@Override
	public void addPilot(PilotModel pilot) {
		archivePilot.add(pilot);
	}
	
	@Override
	public List<PilotModel> getPilotList(){
		return archivePilot;
	}

	@Override
	public PilotModel getPilotDetailByLicenseNumber(String licenseNumber) {
		for (PilotModel p : archivePilot) {
			if (p.getLicenseNumber().equals(licenseNumber)) {
				return p;
			}
		}
		return null;
	}
	
	@Override
	public PilotModel removePilot(String licenseNumber) {
		PilotModel pilotDelete = null;
		pilotDelete = getPilotDetailByLicenseNumber(licenseNumber);
		if (pilotDelete != null) {
			archivePilot.remove(pilotDelete);
		}
		return pilotDelete;
	}

}
