package com.americadigital.libupapi.Business.Impl;

import com.americadigital.libupapi.Business.Interfaces.RScheduleBusiness;
import com.americadigital.libupapi.Dao.Entity.RSheduleEntity;
import com.americadigital.libupapi.Dao.Interfaces.Branch.BranchDao;
import com.americadigital.libupapi.Dao.Interfaces.RShedule.RSheduleDao;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Pojos.RShedule.ScheduleAdd;
import com.americadigital.libupapi.Pojos.RShedule.ScheduleGet;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.GenerateUuid;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.WsPojos.Request.RShedule.AddScheduleRequest;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import com.americadigital.libupapi.WsPojos.Response.RShedule.GetSchedulesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service("rSheduleBusiness")
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT)
public class RSchedulesBusinessImpl implements RScheduleBusiness {

    private static final Logger LOG = LoggerFactory.getLogger(RSchedulesBusinessImpl.class);


    @Autowired
    private RSheduleDao rSheduleDao;

    @Autowired
    private BranchDao branchDao;

    @Override
    public ResponseEntity<HeaderResponse> addSchedule(AddScheduleRequest scheduleRequest) {
        String msg;
        HeaderResponse response;
        branchDao.findByIdBranch(scheduleRequest.idBranch).orElseThrow(() -> new ConflictException(ConstantsTexts.BRANCH_INVALID));
        List<RSheduleEntity> scheduleAddList = new ArrayList<>();
        GenerateUuid uuid;
        for (ScheduleAdd entity : scheduleRequest.schedules) {
            uuid = new GenerateUuid();
            String hourOpen = entity.hourOpen.isPresent() && entity.hourOpen.get().length() > 0 ? entity.hourOpen.get() : null;
            String hourClose = entity.hourClose.isPresent() && entity.hourClose.get().length() > 0 ? entity.hourClose.get() : null;
            scheduleAddList.add(new RSheduleEntity(uuid.generateUuid(), entity.weekDay, hourOpen, hourClose, entity.isClosed, scheduleRequest.idBranch));
        }

        try {
            rSheduleDao.saveAll(scheduleAddList);
        } catch (TransactionSystemException e) {
            scheduleAddList.forEach(s -> {
                String messages = "";
                final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
                final Validator validator = validatorFactory.getValidator();
                final Set<ConstraintViolation<RSheduleEntity>> violations = validator.validate(s);
                for (final ConstraintViolation<RSheduleEntity> violation : violations) {
                    messages += violation.getMessage() + " ";
                }
                LOG.error(messages);
                throw new ConflictException(messages);

            });
        }
        msg = ConstantsTexts.SCHEDULE_ADD;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HeaderResponse> updateSchedule(AddScheduleRequest scheduleRequest) {
        String msg;
        HeaderResponse response;
        branchDao.findByIdBranch(scheduleRequest.idBranch).orElseThrow(() -> new ConflictException(ConstantsTexts.BRANCH_INVALID));
        List<RSheduleEntity> scheduleAddList = new ArrayList<>();
        GenerateUuid uuid;
        List<RSheduleEntity> entityList = rSheduleDao.findListSheduleByIdBranch(scheduleRequest.idBranch);

        for (ScheduleAdd entity : scheduleRequest.schedules) {
            uuid = new GenerateUuid();
            String hourOpen = entity.hourOpen.isPresent() && entity.hourOpen.get().length() > 0 ? entity.hourOpen.get() : null;
            String hourClose = entity.hourClose.isPresent() && entity.hourClose.get().length() > 0 ? entity.hourClose.get() : null;
            scheduleAddList.add(new RSheduleEntity(uuid.generateUuid(), entity.weekDay, hourOpen, hourClose, entity.isClosed, scheduleRequest.idBranch));
        }

        try {
            rSheduleDao.saveAll(scheduleAddList);
            rSheduleDao.deleteAll(entityList);
        } catch (TransactionSystemException e) {
            scheduleAddList.forEach(s -> {
                String messages = "";
                final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
                final Validator validator = validatorFactory.getValidator();
                final Set<ConstraintViolation<RSheduleEntity>> violations = validator.validate(s);
                for (final ConstraintViolation<RSheduleEntity> violation : violations) {
                    messages += violation.getMessage() + " ";
                }
                LOG.error(messages);
                throw new ConflictException(messages);

            });
        }
        msg = ConstantsTexts.SCHEDULE_UPDATE;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetSchedulesResponse> getAllSchedulesByIdBranch(String idBranch) {
        String msg;
        GetSchedulesResponse response;
        List<RSheduleEntity> listScheduleByIdBranch = rSheduleDao.findListSheduleByIdBranch(idBranch);
        if (listScheduleByIdBranch.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new GetSchedulesResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        msg = ConstantsTexts.SCHEDULE_LIST;
        List<ScheduleGet> scheduleGetList = new ArrayList<>();
        listScheduleByIdBranch.forEach(schedule -> scheduleGetList.add(new ScheduleGet(schedule.id_schedule, schedule.day, schedule.hourOpen, schedule.hourClose, schedule.isClosed, schedule.idBranch)));
        response = new GetSchedulesResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), scheduleGetList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
