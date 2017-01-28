package com.arnav.controllers.opening;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.arnav.exceptions.AllPropertyRequiredException;
import com.arnav.exceptions.RequestedIdIsNotExists;
import com.arnav.model.openings.ExcludedTime;
import com.arnav.model.openings.OpeningDays;
import com.arnav.model.openings.OpeningExcludedRequest;
import com.arnav.model.openings.OpeningTime;
import com.arnav.model.provider.Provider;
import com.arnav.repository.opening.ExcludedTimeRepository;
import com.arnav.repository.opening.OpeningDaysRepository;
import com.arnav.repository.provider.ProviderRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Path("/secured/opening-day")
public class OpeningDaysController {

    @Autowired
    private OpeningDaysRepository openingDaysRepository;

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ExcludedTimeRepository excludedTimeRepository;

    @POST
    @Produces("application/json")
    public OpeningDays create(OpeningDays days) {

        Provider provider = providerRepository.findOne(days.getProviderId());
        if (provider == null) {
            throw new RequestedIdIsNotExists("This Provider is not exits,please enter differne one.");
        }
        List<OpeningDays> openingsDays = openingDaysRepository.findByProviderId(provider.getId());

        List<OpeningTime> existingOpeningTime = new ArrayList<OpeningTime>();

        for (OpeningDays day : openingsDays) {

            if (!days.getDays().contains(day.getDay())) {
                day.setStatus(0);
                openingDaysRepository.save(day);
            }

            existingOpeningTime.add(
                    day.getDay());
        }

        for (OpeningTime day : days.getDays()) {

            if (!existingOpeningTime.contains(day)) {
                OpeningDays openingDays = new OpeningDays();
                openingDays.setDay(day);
                openingDays.setStatus(1);
                openingDays.setOpeningTime("10:00 AM");
                openingDays.setEndingTime("06:00 PM");
                openingDays.setProviderId(days.getProviderId());
                openingDaysRepository.save(openingDays);
            } else {
                OpeningDays openingsDay = openingDaysRepository.findByProviderIdAndDay(provider.getId(), day);
                openingsDay.setStatus(1);
                openingDaysRepository.save(openingsDay);
            }
        }

        return days;
    }

    @PUT
    @Produces("application/json")
    public OpeningDays update(OpeningDays days) {

        Provider provider = providerRepository.findOne(days.getProviderId());
        if (provider == null) {
            throw new RequestedIdIsNotExists("This Provider is not exits, please use existing provider.");
        }

        List<OpeningDays> openingsDays = openingDaysRepository.findByProviderId(provider.getId());

        List<OpeningTime> existingOpeningTime = new ArrayList<OpeningTime>();

        for (OpeningDays day : openingsDays) {

            if (!Arrays.asList(days.getDays()).contains(day.getDay())) {
                day.setStatus(0);
                openingDaysRepository.save(day);
            }

            existingOpeningTime.add(
                    day.getDay());
        }

        for (OpeningTime day : days.getDays()) {

            if (!Arrays.asList(existingOpeningTime).contains(day)) {
                OpeningDays openingDays = new OpeningDays();
                openingDays.setDay(day);
                openingDays.setStatus(1);
                openingDays.setOpeningTime("10:00 AM");
                openingDays.setEndingTime("06:00 PM");
                openingDays.setProviderId(days.getProviderId());
                openingDaysRepository.save(openingDays);
            } else {
                OpeningDays openingsDay = openingDaysRepository.findByProviderIdAndDay(provider.getId(), day);
                openingsDay.setStatus(1);
                openingDaysRepository.save(openingsDay);
            }
        }

        return days;
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public OpeningDays findOne(@PathParam(value = "id") String id) {
        return openingDaysRepository.findOne(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Page<OpeningDays> findAll(Pageable pageable) {
        return openingDaysRepository.findAll(pageable);
    }

    @GET
    @Path("/get-by-provider/{providerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<OpeningDays> findByProviderId(@PathParam(value = "providerId") String providerId) {
        return openingDaysRepository.findByProviderId(providerId);
    }

    @PUT
    @Path("/{providerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public OpeningDays update(@PathParam(value = "providerId") String providerId,
                              OpeningExcludedRequest openingExcludedRequest) {

        if (openingExcludedRequest.getOpeningTime() == null && openingExcludedRequest.getEndingTime() == null
                && openingExcludedRequest.getDay() == null) {
            throw new AllPropertyRequiredException("Day, startingTime and Ending time is required, fill it properly.");
        }


        OpeningDays preOpenDays = openingDaysRepository.findByProviderIdAndDay(providerId, openingExcludedRequest.getDay());
        preOpenDays.setProviderId(providerId);
        preOpenDays.setOpeningTime(openingExcludedRequest.getOpeningTime());
        preOpenDays.setEndingTime(openingExcludedRequest.getEndingTime());
        preOpenDays = openingDaysRepository.save(preOpenDays);

        Iterable<ExcludedTime> existingExcludedTimes = excludedTimeRepository.findByOpeningId(preOpenDays.getId());
        excludedTimeRepository.delete(existingExcludedTimes);
        for (ExcludedTime excludedTime : openingExcludedRequest.getExcludedTimeList()) {
            excludedTime.setOpeningId(preOpenDays.getId());
            excludedTimeRepository.save(excludedTime);
        }

        return preOpenDays;
    }


    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public OpeningDays delete(@PathParam(value = "id") String id) {
        OpeningDays days = openingDaysRepository.findOne(id);
        openingDaysRepository.delete(days);
        return days;
    }

}
