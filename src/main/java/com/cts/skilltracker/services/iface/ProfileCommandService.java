package com.cts.skilltracker.services.iface;

import com.cts.skilltracker.models.ProfileCreateDTO;
import com.cts.skilltracker.models.ProfileRsp;
import com.cts.skilltracker.models.ProfileUpdateDTO;

public interface ProfileCommandService {
	
	public ProfileRsp createUserProfile(ProfileCreateDTO profileCreateDTO);
	
	public ProfileRsp updateUserProfile(String userId, ProfileUpdateDTO profileUpdateDTO);

}
