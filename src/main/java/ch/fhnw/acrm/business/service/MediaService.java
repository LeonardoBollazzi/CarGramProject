package ch.fhnw.acrm.business.service;

import ch.fhnw.acrm.data.domain.Agent;
import ch.fhnw.acrm.data.domain.Media;
import ch.fhnw.acrm.data.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@Service
@Validated
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    public Media saveMedia(@Valid Media media) throws Exception {
        return mediaRepository.save(media);
    }

    public List<Media> getAllMedia() {
        return mediaRepository.findAll();
    }

    public List<Media> getAgentMedia(Long agentID) {
        return mediaRepository.findByAgent_IdEquals(agentID);
    }

    public List<Media> getNameMedia(String agentName) {
        return mediaRepository.findByAgent_NameEquals(agentName);
    }

    public Media getMediaByID(Long ID) {
        return mediaRepository.findByIdEquals(ID);
    }

    public Media putMediaLike(Media media, Agent agent) {
        try {
            List<Agent> likesList = media.getLikes();
            likesList.add(agent);
            media.setLikes(likesList);
            mediaRepository.save(media);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        return media;
    }
}
