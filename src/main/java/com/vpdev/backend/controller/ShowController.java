/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vpdev.backend.controller;

import com.vpdev.backend.dto.CommentDTO;
import com.vpdev.backend.dto.ShowDTO;
import com.vpdev.backend.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
/**
 *
 * @author marco
 */
@RestController
@RequestMapping("/api/shows")
public class ShowController {

    @Autowired
    private ShowService showService;

    // Punto A: GET /api/shows/search?search_query=query
    @GetMapping("/search")
    public ResponseEntity<List<ShowDTO>> search(@RequestParam("search_query") String query) {
        return ResponseEntity.ok(showService.searchShows(query));
    }

    // Punto B: GET /api/shows/{showId}
    @GetMapping("/{showId}")
    public ResponseEntity<ShowDTO> getById(@PathVariable Long showId) {
        return ResponseEntity.ok(showService.getShowById(showId));
    }

    // Punto C: POST /api/shows/{showId}/comments
    @PostMapping("/{showId}/comments")
    public ResponseEntity<Map<String, String>> addComment(
            @PathVariable Long showId,
            @RequestBody CommentDTO request) {

        if (request.getRating() == null || request.getRating() < 0 || request.getRating() > 5) {
            return ResponseEntity.badRequest().body(Map.of("status", "Rating debe estar entre 0 y 5"));
        }

        showService.saveComment(showId, request.getComment(), request.getRating());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("status", "SUCCESS"));
    }
}
