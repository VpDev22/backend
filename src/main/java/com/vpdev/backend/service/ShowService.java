/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vpdev.backend.service;
import com.vpdev.backend.dto.CommentDTO;
import com.vpdev.backend.dto.ShowDTO;
import com.vpdev.backend.model.CommentDocument;
import com.vpdev.backend.model.ShowCacheDocument;
import com.vpdev.backend.repository.CommentRepository;
import com.vpdev.backend.repository.ShowCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author marco
 */
@Service
public class ShowService {

    @Autowired
    private ShowCacheRepository showCacheRepository;

    @Autowired
    private CommentRepository commentRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    // Punto A: Buscar shows y adjuntar comentarios guardados
    public List<ShowDTO> searchShows(String query) {
        String url = "http://api.tvmaze.com/search/shows?q=" + query;
        Map[] response = restTemplate.getForObject(url, Map[].class);
        List<ShowDTO> list = new ArrayList<>();

        if (response != null) {
            for (Map item : response) {
                Map showMap = (Map) item.get("show");
                ShowDTO dto = mapMapToDTO(showMap);
                dto.setComments(getCommentsForShow(dto.getId()));
                list.add(dto);
            }
        }
        return list;
    }

    // Punto B: Consultar caché en Mongo. Si no existe, consumir API externa y guardar
    public ShowDTO getShowById(Long showId) {
        ShowDTO dto;
        Optional<ShowCacheDocument> cached = showCacheRepository.findById(showId);

        if (cached.isPresent()) {
            ShowCacheDocument doc = cached.get();
            dto = new ShowDTO();
            dto.setId(doc.getId());
            dto.setName(doc.getName());
            dto.setChannel(doc.getChannel());
            dto.setSummary(doc.getSummary());
            dto.setGenres(doc.getGenres());
        } else {
            String url = "https://api.tvmaze.com/shows/" + showId;
            Map showMap = restTemplate.getForObject(url, Map.class);
            dto = mapMapToDTO(showMap);

            // Guardar en la caché de Mongo
            ShowCacheDocument doc = new ShowCacheDocument();
            doc.setId(dto.getId());
            doc.setName(dto.getName());
            doc.setChannel(dto.getChannel());
            doc.setSummary(dto.getSummary());
            doc.setGenres(dto.getGenres());
            showCacheRepository.save(doc);
        }

        dto.setComments(getCommentsForShow(showId));
        return dto;
    }

    // Punto C: Guardar comentario y calificación
    public void saveComment(Long showId, String comment, Integer rating) {
        CommentDocument doc = new CommentDocument();
        doc.setShowId(showId);
        doc.setComment(comment);
        doc.setRating(rating);
        commentRepository.save(doc);
    }

    private List<CommentDTO> getCommentsForShow(Long showId) {
        return commentRepository.findByShowId(showId).stream()
                .map(c -> new CommentDTO(c.getComment(), c.getRating()))
                .collect(Collectors.toList());
    }

    private ShowDTO mapMapToDTO(Map showMap) {
        ShowDTO dto = new ShowDTO();
        dto.setId(((Number) showMap.get("id")).longValue());
        dto.setName((String) showMap.get("name"));
        dto.setSummary((String) showMap.get("summary"));
        dto.setGenres((List<String>) showMap.get("genres"));

        // Obtener network_name o webchannel_name
        if (showMap.get("network") != null) {
            Map network = (Map) showMap.get("network");
            dto.setChannel((String) network.get("name"));
        } else if (showMap.get("webChannel") != null) {
            Map webChannel = (Map) showMap.get("webChannel");
            dto.setChannel((String) webChannel.get("name"));
        }
        return dto;
    }
}
