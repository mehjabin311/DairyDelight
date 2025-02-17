package com.dairyproject.services;

import java.util.List;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dairyproject.entities.FeedBackDetails;
import com.dairyproject.repositories.FeedBackRepository;

@Service
@Transactional
public class FeedBackServiceImpl implements FeedBackService {

    @Autowired
    private FeedBackRepository feedRepo;

    @Override
    public List<FeedBackDetails> getAllFeedBackDetails() {
        return feedRepo.findAllFeedBackDetails();
    }

    @Override
    public List<FeedBackDetails> getFeedBackDetailsByName(String name) {
        return feedRepo.findFeedBackDetailsByName(name);
    }

    @Override
    public List<FeedBackDetails> getFeedBackDetailsBySubject(String subject) {
        return feedRepo.findFeedBackDetailsBySubject(subject);
    }

    @Override
    public FeedBackDetails getFeedBackDetailsById(Integer fId) {
        return feedRepo.findFeedBackDetailsById(fId);
    }

    @Override
    public String insertNewFeedBackDetails(FeedBackDetails feedBackDetails) {
        if (feedRepo.save(feedBackDetails) != null) {
            return "Thank You for your valuable feedback !";
        }
        return "Failed to submit your feedback ! Please try again later.";
    }
}
