package org.restapplication.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.restapplication.messenger.database.DatabaseClass;
import org.restapplication.messenger.model.Message;
import org.restapplication.messenger.model.Profile;


public class ProfileService {
	private Map<String,Profile> profiles= DatabaseClass.getProfiles();
	
	public ProfileService(){
		profiles.put("Abhinav",new Profile(1L,"SuperMan","Abhinav","Pandey"));
	}
	
	
	public List<Profile> getAllProfiles(){
		return new ArrayList<Profile>(profiles.values());	
		}
	
	
	public Profile addProfile(Profile profile){
		profile.setId(profiles.size()+1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile updateProfile(Profile profile){
		if(profile.getId()<=0){
			return null;
		}
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	public Profile removeProfile(String profileName){
		return profiles.remove(profileName);
	}
	
	public Profile getProfile(String profileName){
		return profiles	.get(profileName);
	}
	

}
