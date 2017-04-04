package gd.web.domain;

import org.springframework.transaction.annotation.Transactional;

import gd.web.entity.OutStreamEntity;

public interface OutStreamDAO {
	@Transactional
	void addOutStream(OutStreamEntity outStreamEntity);

	@Transactional
	void inactivate(int staId);
	
	@Transactional
	void updateOutStream (OutStreamEntity outStreamEntity);

	@Transactional
	OutStreamEntity getActiveRecord(int staId);
}
