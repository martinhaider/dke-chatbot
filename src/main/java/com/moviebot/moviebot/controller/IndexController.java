package com.moviebot.moviebot.controller;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class IndexController {

    public String SERVICE_URI = "http://localhost:3030/movie/query";

    @RequestMapping("/director")
    public @ResponseBody List<String> getMovieByDirector(@RequestParam String director){
        List<String> movieList = new ArrayList<String>();
        QueryExecution q = QueryExecutionFactory.sparqlService(SERVICE_URI, "PREFIX ab: <http://learningsparql.com/ns/adressbook#> SELECT ?movie WHERE { ?director ab:director '" + director + "' . ?director ab:movie ?movie . }");

        ResultSet results = q.execSelect();

        while(results.hasNext()){
            QuerySolution soln = results.nextSolution();
            RDFNode movie = soln.get("movie");
            movieList.add(movie.toString());
        }

        q.close();

        return movieList;
    }

    @RequestMapping("/movie")
    public @ResponseBody List<String> getDirectorByMovie(@RequestParam String movie){
        List<String> directorList = new ArrayList<String>();
        QueryExecution q = QueryExecutionFactory.sparqlService(SERVICE_URI, "PREFIX ab: <http://learningsparql.com/ns/adressbook#> SELECT ?director WHERE { ?movie ab:movie '" + movie + "' . ?movie ab:director ?director . }");

        ResultSet results = q.execSelect();

        while(results.hasNext()){
            QuerySolution soln = results.nextSolution();
            RDFNode director = soln.get("director");
            directorList.add(director.toString());
        }

        q.close();

        return directorList;
    }
}