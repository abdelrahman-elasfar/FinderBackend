package com.finder.prod.Controllers;

import com.finder.prod.Services.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class QueryController {

    private QueryService queryService;

    @Autowired
    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{query}")
    public ResponseEntity<?> getAllWebsites(@PathVariable("query") String query, @RequestParam("pageSize") int pageSize, @RequestParam("pageNum") int pageNum) {
        System.out.println("PAGE NUMBER : "+pageNum);
        System.out.println("PAGE SIZE : "+pageSize);
        query = query.toLowerCase();
        
        return queryService.getAllWebsitesByQuery(query, pageSize, pageNum);       
    }


    @RequestMapping(method = RequestMethod.GET, value = "/suggestions/{query}")
    public ResponseEntity<?> getSuggestions(@PathVariable("query") String query) {
        query = query.toLowerCase();
        return queryService.getSuggestions(query);       

    }

}

