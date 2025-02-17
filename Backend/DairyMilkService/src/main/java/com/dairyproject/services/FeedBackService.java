package com.dairyproject.services;

import java.util.List;
import com.dairyproject.entities.FeedBackDetails;

public interface FeedBackService {
	List<FeedBackDetails> getAllFeedBackDetails();

	List<FeedBackDetails> getFeedBackDetailsByName(String name);

	List<FeedBackDetails> getFeedBackDetailsBySubject(String subject);

	FeedBackDetails getFeedBackDetailsById(Integer fId);

	String insertNewFeedBackDetails(FeedBackDetails feedBackDetails);
}
