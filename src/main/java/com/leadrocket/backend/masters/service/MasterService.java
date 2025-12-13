package com.leadrocket.backend.masters.service;

import com.leadrocket.backend.masters.model.MasterData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterService {

	public List<MasterData> getByType(String type) {
		return List.of(); // mongo query will be added later
	}
}
