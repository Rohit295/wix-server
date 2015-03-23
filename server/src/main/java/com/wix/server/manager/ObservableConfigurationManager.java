package com.wix.server.manager;

import com.wix.common.model.ObservableDTO;
import com.wix.server.exception.DuplicateEntityException;
import com.wix.server.exception.UnknownEntityException;
import com.wix.server.exception.ValidationException;
import com.wix.server.persistence.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by racastur on 05-03-2015.
 */
@Component("observableConfigurationManager")
public class ObservableConfigurationManager {

    private static final Logger log = Logger.getLogger(ObservableConfigurationManager.class.getName());

    public ObservableDTO createUpdateObservable(ObservableDTO observableDTO) throws ValidationException, UnknownEntityException, DuplicateEntityException {

        if (observableDTO == null || !StringUtils.hasText(observableDTO.getName())) {
            // throw validation exception
            throw new ValidationException("invalid observable data");
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();

        try {

            // TODO validate routeId and routeStopId

            boolean newObservable = false;

            Observable observable;
            if (StringUtils.hasText(observableDTO.getId())) {

                observable = pm.getObjectById(Observable.class, observableDTO.getId());
                if (observable == null) {
                    throw new UnknownEntityException("Unknown observable");
                }

                Observable routeBySameName = getObservableByName(observableDTO.getName());
                if (routeBySameName != null && !routeBySameName.getId().equals(observable.getId())) {
                    throw new DuplicateEntityException("another observable with given name already exists");
                }

                observable.setName(observableDTO.getName());
                observable.setRouteId(observableDTO.getRouteId());
                observable.setRouteStopId(observableDTO.getRouteStopId());
                observable.setStopPurpose(observableDTO.getStopPurpose().name());

            } else {

                newObservable = true;

                observable = getObservableByName(observableDTO.getName());
                if (observable != null) {
                    throw new DuplicateEntityException("observable with given name already exists");
                }

                observable = new Observable(observableDTO);

            }

            if (newObservable) {
                pm.makePersistent(observable);
            }

            return observable.getDTO();

        } finally {
            try {
                pm.close();
            } catch (Exception e) {
                // ignore
            }
        }

    }

    public List<ObservableDTO> getAllObservables() {

        PersistenceManager pm = PMF.get().getPersistenceManager();

        Extent<Observable> observableExtent = pm.getExtent(Observable.class);
        if (observableExtent == null) {
            return new ArrayList<ObservableDTO>();
        }

        List<ObservableDTO> dtos = new ArrayList<>();
        for (Observable observable : observableExtent) {
            dtos.add(observable.getDTO());
        }

        return dtos;

    }

    public ObservableDTO getObservable(String observableId) {

        if (!StringUtils.hasText(observableId)) {
            return null;
        }

        PersistenceManager pm = PMF.get().getPersistenceManager();

        Observable observable = pm.getObjectById(Observable.class, observableId);
        if (observable == null) {
            return null;
        }

        return observable.getDTO();

    }

    public Observable getObservableByName(String name) {

        PersistenceManager pm = PMF.get().getPersistenceManager();

        Query q = pm.newQuery(Observable.class);
        q.setFilter("name == nameParam");
        q.declareParameters("String nameParam");

        try {

            List<Observable> results = (List<Observable>) q.execute(name);
            if (results == null || results.isEmpty() || results.size() > 1) {
                return null;
            }

            return results.get(0);

        } finally {
            q.closeAll();
        }

    }

}
