package com.americadigital.libupapi.Business.Impl;

import com.americadigital.libupapi.Business.Interfaces.StatesBusiness;
import com.americadigital.libupapi.Dao.Entity.StatesEntity;
import com.americadigital.libupapi.Dao.Interfaces.States.StatesDao;
import com.americadigital.libupapi.Pojos.States;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.WsPojos.Response.States.StatesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("statesBusinessImpl")
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT)
public class StatesBusinessImpl implements StatesBusiness {

    @Autowired
    private StatesDao statesDao;

    @Override
    public ResponseEntity<StatesResponse> getAllStates() {
        String msg;
        StatesResponse response;
        List<States> states = new ArrayList<>();
        Iterable<StatesEntity> allStates = statesDao.findAll();
        allStates.forEach(statesEntity -> {
            states.add(new States(statesEntity.idState, statesEntity.state));
        });
        msg = ConstantsTexts.STATE_LIST;
        response = new StatesResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), states);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
