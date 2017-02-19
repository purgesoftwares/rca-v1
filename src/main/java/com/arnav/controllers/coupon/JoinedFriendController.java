package com.arnav.controllers.coupon;

import com.arnav.model.coupon.JoinedFriend;
import com.arnav.repository.coupon.JoinedFriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Shankar on 2/19/2017.
 */
@Path("/secured/joined-friend")
public class JoinedFriendController {

    @Autowired
    private JoinedFriendRepository joinedFriendRepository;

    @POST
    @Produces("application/json")
    public JoinedFriend create(JoinedFriend joinedFriend){
        return joinedFriendRepository.save(joinedFriend);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JoinedFriend findOne(@PathParam(value="id")String id){
        return joinedFriendRepository.findOne(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Page<JoinedFriend> findAll(Pageable pageble){

        return new PageImpl<JoinedFriend>(joinedFriendRepository.findAll(pageble)
                .getContent(), pageble, 20);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JoinedFriend update(@PathParam(value="id")String id, @Valid JoinedFriend joinedFriend){
        JoinedFriend preJoinedFriend = joinedFriendRepository.findOne(id);
        joinedFriend.setId(id);

        if(joinedFriend.getCouponNumber() == null){
            joinedFriend.setCouponNumber(preJoinedFriend.getCouponNumber());
        }
        return joinedFriendRepository.save(joinedFriend);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public JoinedFriend delete(@PathParam(value="id")String id){
        JoinedFriend joinedFriend = joinedFriendRepository.findOne(id);
        joinedFriendRepository.delete(joinedFriend);
        return joinedFriend;
    }

}