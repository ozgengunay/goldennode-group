package com.goldennode.server.controllers.rest.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.goldennode.commons.entity.Thing;
import com.goldennode.commons.entity.ThingContext;
import com.goldennode.commons.entity.ThingOwnership;
import com.goldennode.commons.entity.ThingPoint;
import com.goldennode.commons.entity.Users;
import com.goldennode.commons.entity.BaseEntity.Status;
import com.goldennode.commons.entity.ThingContext.Type;
import com.goldennode.commons.entity.ThingPoint.Permission;
import com.goldennode.commons.repository.ThingContextRepository;
import com.goldennode.commons.repository.ThingOwnershipRepository;
import com.goldennode.commons.repository.ThingRepository;
import com.goldennode.commons.util.UUID;
import com.goldennode.server.controllers.rest.ErrorCode;
import com.goldennode.server.controllers.rest.GoldenNodeRestException;
import com.goldennode.server.security.GoldenNodeUserDetails;

@RestController
@RequestMapping(value = { "/rest/things" })
@CrossOrigin(origins = "*")
public class MapController implements Map<Object, Object> {

    @Autowired
    private ThingRepository thingRepository;
    @Autowired
    private ThingOwnershipRepository thingOwnershipRepository;
    @Autowired
    private ThingContextRepository thingContextRepository;

    
    @RequestMapping(value = { "/{id}" }, method = { RequestMethod.GET })
    public Thing get(@PathVariable("id") String id, @RequestParam("idType") String idType)
            throws GoldenNodeRestException {
        GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        Thing entity = null;
        if (idType.equals("thingId")) {
            entity = thingRepository.findByIdAndStatus(id, Status.ENABLED);

        }
        if (idType.equals("publicKey")) {
            entity = thingRepository.findByPublickeyAndStatus(id, Status.ENABLED);
        }
        if (idType.equals("thingContextId")) {
            return findByThingContextId(userDetails, id);
        }

        if (entity == null) {
            throw new GoldenNodeRestException(ErrorCode.THING_NOT_FOUND);
        }
        // get owner
        ThingOwnership ownership = thingOwnershipRepository.findByThingIdAndUserIdAndStatus(id, userDetails.getId(),
                Status.ENABLED);
        if (ownership == null) {
            throw new GoldenNodeRestException(ErrorCode.THING_NOT_OWNED);
        }

        return entity;
    }

    private Thing findByThingContextId(GoldenNodeUserDetails userDetails, String id) throws GoldenNodeRestException {
        List<ThingOwnership> ownerships = thingOwnershipRepository.findByUserIdAndStatus(userDetails.getId(),
                Status.ENABLED);
        for (ThingOwnership ownership : ownerships) {
            Thing thing = thingRepository.findByIdAndStatus(ownership.getThingId(), Status.ENABLED);
            if (thing.getThingContextId().equals(id)) {
                return thing;
            }
        }
        throw new GoldenNodeRestException(ErrorCode.THING_NOT_FOUND);

    }

    
    @RequestMapping(method = { RequestMethod.GET })
    public List<Thing> get(Principal principal) throws GoldenNodeRestException {
        GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        // get owner
        List<ThingOwnership> ownerships = thingOwnershipRepository.findByUserIdAndStatus(userDetails.getId(),
                Status.ENABLED);
        List<Thing> list = new ArrayList<Thing>();
        for (ThingOwnership ownership : ownerships) {
            list.add(thingRepository.findByIdAndStatus(ownership.getThingId(), Status.ENABLED));
        }

        return list;
    }

    @RequestMapping(method = { RequestMethod.POST })
    public Thing register(HttpServletRequest request, HttpServletResponse response, @RequestBody Thing data)
            throws GoldenNodeRestException {

        GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        ThingContext context = thingContextRepository.findByIdAndStatus(data.getThingContextId(), Status.ENABLED);
        if (context == null) {
            throw new GoldenNodeRestException(ErrorCode.THING_CONTEXT_NOT_FOUND);
        }

        if (context.getType() != Type.PHYSICAL && context.getType() != Type.VIRTUAL) {
            throw new GoldenNodeRestException(ErrorCode.CAN_REGISTER_ONLY_PHYSICAL_OR_VIRTUAL_THINGS);
        }

        if (context.getType() == Type.VIRTUAL) {

            List<ThingOwnership> ownerships = thingOwnershipRepository.findByUserIdAndStatus(userDetails.getId(),
                    Status.ENABLED);
            for (ThingOwnership ownership : ownerships) {
                Thing thing = thingRepository.findByIdAndStatus(ownership.getThingId(), Status.ENABLED);
                if (thing.getThingContextId().equals(data.getThingContextId())) {
                    throw new GoldenNodeRestException(ErrorCode.SAME_THING_ALREADY_REGISTERED);
                }
            }
        }

        if (context.getType()==Type.PHYSICAL && !userDetails.getRoles().contains(new SimpleGrantedAuthority(Users.Role.ROLE_DEVELOPER.toString())))
            throw new GoldenNodeRestException(ErrorCode.REGISTERATIONNOTALLOWEDFORNONDEVELOPERS);
        
        Thing newEntity = Thing.newEntity();
        newEntity.setPublickey(UUID.getUUID());
        newEntity.setSecretkey(UUID.getUUID());
        newEntity.setThingContextId(data.getThingContextId());
        newEntity.setLatitude(data.getLatitude());
        newEntity.setLongitude(data.getLongitude());
        
        List<ThingPoint> points = data.getThingPoints();
        for (ThingPoint point : points) {
            ThingPoint newSubentity = ThingPoint.newEntity();
            newSubentity.setInternalId(context.getType() == Type.VIRTUAL ? point.getInternalId() : null);
            newSubentity.setName(point.getName());
            newSubentity.setType(point.getType());
            newSubentity.setUnit(point.getUnit());
            newSubentity.setPermission(Permission.FRIENDS);// TODO set to
                                                            // private by
                                                            // default
            newEntity.addThingPoint(newSubentity);
        }
        newEntity = thingRepository.save(newEntity);

        if (context.getType() == Type.VIRTUAL) {
            try {
                own(newEntity);
            } catch (GoldenNodeRestException e) {
                // will not throw
            }
        }
        response.setHeader("Location", request.getRequestURI() + newEntity.getId());
        return newEntity;

    }
    
    
    @RequestMapping(value = { "/{id}" }, method = { RequestMethod.DELETE })
    public void delete(HttpServletResponse response, @PathVariable("id") String id,
            @RequestParam("idType") String idType, @RequestParam("operation") String operation)
                    throws GoldenNodeRestException {

        if (operation.equals("deregister")) {
            deregister(response, id, idType, operation);
        }
        if (operation.equals("disown")) {
            disown(response, id, idType, operation);
        }
    }

    private void deregister(HttpServletResponse response, String id, String idType, String operation)
            throws GoldenNodeRestException {

        GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        Thing entity = null;
        if (idType.equals("thingId")) {
            entity = thingRepository.findByIdAndStatus(id, Status.ENABLED);

        }
        if (idType.equals("publicKey")) {
            entity = thingRepository.findByPublickeyAndStatus(id, Status.ENABLED);
        }

        if (entity == null) {
            throw new GoldenNodeRestException(ErrorCode.THING_NOT_FOUND);
        }

        ThingContext context = thingContextRepository.findByIdAndStatus(entity.getThingContextId(), Status.ENABLED);
        if (context == null) {
            throw new GoldenNodeRestException(ErrorCode.THING_CONTEXT_NOT_FOUND);
        }

        if (context.getType() == Type.PHYSICAL) {
            throw new GoldenNodeRestException(ErrorCode.CANT_DEREGISTER_PHYSICAL_THINGS);
        }

        ThingOwnership ownership = thingOwnershipRepository.findByThingIdAndUserIdAndStatus(id, userDetails.getId(),
                Status.ENABLED);
        if (ownership == null) {
            throw new GoldenNodeRestException(ErrorCode.THING_NOT_OWNED);
        }
        ownership.disable();
        thingOwnershipRepository.save(ownership);

        entity.disable();
        List<ThingPoint> points = entity.getThingPoints();
        for (ThingPoint point : points) {
            point.disable();
        }
        thingRepository.save(entity);

    }

    
    
    @RequestMapping(value = { "/{id}" }, method = { RequestMethod.PUT })
    public void update(@PathVariable("id") String id, @RequestParam("operation") String operation,
            @RequestParam("idType") String idType, @RequestBody Thing data) throws GoldenNodeRestException {

        Thing entity = null;
        if (idType.equals("thingId")) {
            entity = thingRepository.findByIdAndStatus(id, Status.ENABLED);

        }
        if (idType.equals("publicKey")) {
            entity = thingRepository.findByPublickeyAndStatus(id, Status.ENABLED);
        }

        if (entity == null) {
            throw new GoldenNodeRestException(ErrorCode.THING_NOT_FOUND);
        }

        if (operation.equals("own")) {
            own(entity);
        }
    }

    private void own(Thing entity) throws GoldenNodeRestException {
        GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        // get owner
        ThingOwnership ownership_ = thingOwnershipRepository.findByThingIdAndStatus(entity.getId(), Status.ENABLED);
        if (ownership_ != null) {
            throw new GoldenNodeRestException(ErrorCode.THING_ALREADY_OWNED);
        }
        ThingOwnership ownership = ThingOwnership.newEntity();
        ownership.setThingId(entity.getId());
        ownership.setUserId(userDetails.getId());
        thingOwnershipRepository.save(ownership);

    }
    
    private void disown(HttpServletResponse response, String id, String idType, String operation)
            throws GoldenNodeRestException {

        Thing entity = null;
        if (idType.equals("thingId")) {
            entity = thingRepository.findByIdAndStatus(id, Status.ENABLED);

        }
        if (idType.equals("publicKey")) {
            entity = thingRepository.findByPublickeyAndStatus(id, Status.ENABLED);
        }
        if (entity == null) {
            throw new GoldenNodeRestException(ErrorCode.THING_NOT_FOUND);
        }

        GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        ThingContext context = thingContextRepository.findByIdAndStatus(entity.getThingContextId(), Status.ENABLED);
        if (context == null) {
            throw new GoldenNodeRestException(ErrorCode.THING_CONTEXT_NOT_FOUND);
        }
        if (context.getType() == Type.VIRTUAL) {
            throw new GoldenNodeRestException(ErrorCode.CAN_NOT_DISOWN_VIRTUAL_THINGS);
        }
        ThingOwnership ownership = thingOwnershipRepository.findByThingIdAndUserIdAndStatus(
                entity.getId(), userDetails.getId(),  Status.ENABLED);
        if (ownership==null) {
            throw new GoldenNodeRestException(ErrorCode.THING_NOT_OWNED);
        }
        
        ownership.disable();
        thingOwnershipRepository.save(ownership);
        
        
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Object get(Object key) {
        // TODO Auto-generated method stub
        return null;
    }

    //@RequestMapping(value = { "/{id}" }, method = { RequestMethod.GET })
    //public Thing get(@PathVariable("id") String id, @RequestParam("idType") String idType)
    
    
    
    @RequestMapping(value = { "/{id}" }, method = { RequestMethod.POST })
    
    public Object put(HttpServletRequest request, HttpServletResponse response, @RequestBody MapEntry data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object remove(Object key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void putAll(Map m) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Set keySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection values() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set entrySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object put(Object key, Object value) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * @RequestMapping(value = { "/share" }, method = { RequestMethod.PUT })
     * public void share( @PathVariable("thingId") String thingId,
     * 
     * @PathVariable("thingPointId") String thingPointId, @RequestBody
     * ThingPermission data) throws ThingException {
     * 
     * GoldenNodeUserDetails userDetails = (GoldenNodeUserDetails)
     * SecurityContextHolder.getContext() .getAuthentication().getPrincipal();
     * 
     * Thing entity = repository.findByIdAndStatus(thingId, Status.ENABLED); if
     * (entity == null) { throw new ThingNotFoundException(); }
     * List<ThingOwnership> ownerships = thingOwnershipRepository
     * .findByThingIdAndThingPointIdAndUserIdAndOwnershipAndStatus(thingId,
     * thingPointId, userDetails.getId(), Ownership.OWNER, Status.ENABLED); if
     * (ownerships.size() == 0) { throw new ThingNotOwnedException(); }
     * 
     * ThingPermission thingPermission =
     * thingPermissionRepository.findByThingIdAndThingPointIdAndStatus(thingId,
     * thingPointId, Status.ENABLED);
     * 
     * if (thingPermission == null) { throw new NoThingPermissionException(); }
     * 
     * thingPermission.setPermission(data.getPermission());
     * thingPermission.setThingId(data.getThingId());
     * thingPermission.setThingPointId(data.getThingPointId());
     * thingPermission.setUserIdSharedWith(data.getUserIdSharedWith());
     * thingPermissionRepository.save(thingPermission);
     * 
     * }
     */

}
