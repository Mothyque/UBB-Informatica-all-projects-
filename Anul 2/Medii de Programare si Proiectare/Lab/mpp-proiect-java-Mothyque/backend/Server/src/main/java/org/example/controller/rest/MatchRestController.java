package org.example.controller.rest;

import org.example.domain.Match;
import org.example.service.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin
@RestController
@RequestMapping("/api/matches")

public class MatchRestController
{
    private final MatchService matchService;

    public MatchRestController(MatchService matchService)
    {
        this.matchService = matchService;
    }

    @GetMapping
    public List<Match> getMatches(@RequestParam(name = "matchType", required = false) String matchType)
    {
        Iterable<Match> getAllMatches = matchService.findAll();
        List<Match> matchList = StreamSupport.stream(getAllMatches.spliterator(), false).collect(Collectors.toList());

        if (matchType != null && !matchType.isEmpty())
        {
            return matchList.stream().filter(match -> match.getMatchType().equals(matchType)).collect(Collectors.toList());
        }
        return matchList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Match> getMatchById(@PathVariable(name = "id") Integer id) {
        Optional<Match> match = matchService.findOne(id);
        Match matchEntity = match.orElse(null);
        if (matchEntity != null)
        {
            return new ResponseEntity<>(matchEntity, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Integer> createMatch(@RequestBody Match match)
    {
        matchService.add(match);
        return new ResponseEntity<>(match.getId(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Match> updateMatch(@PathVariable(name = "id") Integer id, @RequestBody Match match)
    {
        match.setId(id);
        matchService.update(match);
        return new ResponseEntity<>(match, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable(name = "id") Integer id) {
        Optional<Match> match = matchService.findOne(id);
        Match matchEntity = match.orElse(null);
        if (matchEntity != null)
        {
            matchService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}