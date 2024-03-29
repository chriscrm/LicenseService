package com.home.license.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.home.license.model.License;
import com.home.license.service.LicenseService;

@RestController
@RequestMapping(value = "v1/organization/{organizationId}/license")
public class LicenseController {
	
	@Autowired
	private LicenseService licenseService;
	
	@GetMapping(value = "/{licenseId}")
	public ResponseEntity<License> getLicense(
			@PathVariable("organizationId") String organizationId,
			@PathVariable("licenseId") String licenseId){
		
		License license = licenseService.getLicense(licenseId, organizationId);
		
		//HATEOAS LINK
		license.add(linkTo(methodOn(LicenseController.class)
				.getLicense(organizationId, license.getLicenseId()))
				.withSelfRel(),
				linkTo(methodOn(LicenseController.class)
						.createLicense(license, null))
					.withRel("createLicense"),
				linkTo(methodOn(LicenseController.class)
						.updateLicense(license))
						.withRel("updateLicense"),
				linkTo(methodOn(LicenseController.class)
						.deleteLicense(license.getLicenseId()))
				.withRel("deleteLicense"));
		
		return ResponseEntity.ok(license);
	}
	
	@PutMapping
	public ResponseEntity<License> updateLicense(
			@RequestBody License request){
		return ResponseEntity.ok(licenseService.updateLicense(request));
	}
	
	@PostMapping
	public ResponseEntity<License> createLicense(
			@RequestBody License request,
			@RequestHeader(value = "Accept-Language", required = false) Locale locale){
		return ResponseEntity.ok(licenseService.createLicense(request));
	}
	
	@DeleteMapping(value = "/{licenseId}")
	public ResponseEntity<String> deleteLicense(
			@PathVariable("licenseId") String licenseId){
		return ResponseEntity.ok(licenseService.deleteLicense(licenseId));
	}

}

