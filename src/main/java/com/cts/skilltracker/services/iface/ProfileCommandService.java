package com.cts.skilltracker.services.iface;

import java.util.concurrent.CompletableFuture;

import com.cts.skilltracker.models.ProfileCreateDTO;
import com.cts.skilltracker.models.ProfileRsp;

public interface ProfileCommandService {
	
	public CompletableFuture<ProfileRsp> createUserProfile(ProfileCreateDTO profileCreateDTO);

}
