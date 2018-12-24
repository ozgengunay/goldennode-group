package com.goldennode.server.controllers;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goldennode.server.common.ErrorCode;
import com.goldennode.server.common.GoldenNodeRestException;
import com.goldennode.server.common.ResponseEntity;
import com.goldennode.server.common.StatusCode;
import com.goldennode.server.security.GoldenNodeUser;
import com.goldennode.server.services.MapService;

@RestController
@RequestMapping(value = { "/goldennode/map/id/{mapId}" })
@CrossOrigin(origins = "*")
public class SetController {
    @Autowired
    private MapService mapService;

    public int size(String userId, String objectId) {
        return init(userId, objectId).size();
    }

    public boolean isEmpty(String userId, String objectId) {
        return init(userId, objectId).isEmpty();
    }

    public boolean contains(String userId, String objectId, Object o) {
        return init(userId, objectId).contains(o);
    }

    public Iterator iterator(String userId, String objectId) {
        return init(userId, objectId).iterator();
    }

    public Object[] toArray(String userId, String objectId) {
        return init(userId, objectId).toArray();
    }

    public Object[] toArray(String userId, String objectId, Object[] a) {
        return init(userId, objectId).toArray();
    }

    public boolean remove(String userId, String objectId, Object o) {
        return init(userId, objectId).remove(o);
    }

    public boolean containsAll(String userId, String objectId, Collection c) {
        return init(userId, objectId).containsAll(c);
    }

    public boolean addAll(String userId, String objectId, Collection c) {
        return init(userId, objectId).addAll(c);
    }

    public boolean removeAll(String userId, String objectId, Collection c) {
        return init(userId, objectId).removeAll(c);
    }

    public boolean retainAll(String userId, String objectId, Collection c) {
        return init(userId, objectId).retainAll(c);
    }

    public void clear(String userId, String objectId) {
        init(userId, objectId).clear();
    }

    public boolean add(String userId, String objectId, Object e) {
        return init(userId, objectId).add(e);
    }
}
