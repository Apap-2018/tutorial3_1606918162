package com.apap.tutorial3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial3.model.PilotModel;
import com.apap.tutorial3.service.PilotService;

@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping("/pilot/add")
	public String add(@RequestParam(value = "id", required = true) String id,
					@RequestParam(value = "licenseNumber", required = true) String licenseNumber,
					@RequestParam(value = "name", required = true) String name,
					@RequestParam(value = "flyHour", required = true) int flyHour) {
		PilotModel pilot = new PilotModel(id, licenseNumber, name, flyHour);
		pilotService.addPilot(pilot);
		return "add";
	}
	
	@RequestMapping("/pilot/view")
	public String view(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		
		model.addAttribute("pilot", archive);
		return "view-pilot";
	}
	
	@RequestMapping("/pilot/viewall")
	public String viewall(Model model) {
		List<PilotModel> archive = pilotService.getPilotList();
		
		model.addAttribute("listPilot", archive);
		return "viewall-pilot";
	}
	
	@RequestMapping("/pilot/view/license-number/{licenseNumber}")
	public String viewPath(@PathVariable String licenseNumber, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		if (archive == null) {
			model.addAttribute("error", "Nomor lisensi "+licenseNumber+" tidak ditemukan" );
			return "error-msg";
		}
		else {
			model.addAttribute("pilot", archive);
			return "view-pilot";
		}
	}
	
	@RequestMapping("/pilot/update/license-number/{licenseNumber}/fly-hour/{flyHour}")
	public String updateFlyHour(
			@PathVariable("licenseNumber") String licenseNumber,
			@PathVariable("flyHour") String flyHour,
			Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		if (archive == null) {
			model.addAttribute("error", "Nomor lisensi "+licenseNumber+" tidak ditemukan.");
			return "error-msg";
		}
		else {
			pilotService.removePilot(archive.getLicenseNumber());
			pilotService.addPilot(new PilotModel(archive.getId(), archive.getLicenseNumber(), archive.getName(), Integer.parseInt(flyHour)));
			model.addAttribute("change", archive.getName() + " berhasil diubah");
			model.addAttribute("pilot", archive);
			return "change-msg";
		}
	}
	
	@RequestMapping("/pilot/delete/licenseNumber/{licenseNumber}")
	public String delete(
			@PathVariable String licenseNumber,
			Model model
			) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		PilotModel pilotRemove = pilotService.removePilot(archive.getLicenseNumber());
		if (pilotRemove == null) {
			model.addAttribute("error", "Nomor lisensi "+licenseNumber+" tidak ditemukan");
			return "error-msg";
		}
		else {
			model.addAttribute("change", pilotRemove.getName()+ " berhasil dihapus");
			model.addAttribute("pilot", pilotRemove);
			return "change-msg";
		}
	}

}
