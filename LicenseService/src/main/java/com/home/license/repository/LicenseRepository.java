package com.home.license.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.home.license.model.License;

@Repository //optional when extend from a CrudRepository
public interface LicenseRepository extends CrudRepository<License, String> {

	public List<License> findByOrganizationId(String organizationId);
	public License findByOrganizationIdAndLicenseId(String organizationId, String licenseId);
}
