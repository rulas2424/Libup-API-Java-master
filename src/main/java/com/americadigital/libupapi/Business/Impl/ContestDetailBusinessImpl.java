package com.americadigital.libupapi.Business.Impl;

import com.americadigital.libupapi.Business.Interfaces.ContestDetailBusiness;
import com.americadigital.libupapi.Dao.Entity.ContestDetailEntity;
import com.americadigital.libupapi.Dao.Entity.TContestEntity;
import com.americadigital.libupapi.Dao.Interfaces.Contest.TContestDao;
import com.americadigital.libupapi.Dao.Interfaces.ContestDetail.ContestDetailDao;
import com.americadigital.libupapi.Dao.Interfaces.User.UserEntityDao;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.GenerateUuid;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.WsPojos.Request.ContestDetail.ContestDetailRequest;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
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
import java.util.Date;
import java.util.Set;

@Service("contestDetailBusinessImpl")
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT)
public class ContestDetailBusinessImpl implements ContestDetailBusiness {
    private static final Logger LOG = LoggerFactory.getLogger(ContestDetailBusinessImpl.class);

    @Autowired
    private UserEntityDao userEntityDao;

    @Autowired
    private TContestDao tContestDao;

    @Autowired
    private ContestDetailDao contestDetailDao;

    @Override
    public ResponseEntity<HeaderResponse> addContestDetail(ContestDetailRequest contestDetailRequest) {
        String msg = "";
        HeaderResponse response;
        TContestEntity tContestEntity = tContestDao.findByIdContest(contestDetailRequest.idContest).orElseThrow(() -> new ConflictException(ConstantsTexts.CONTEST_INVALID));
        if(tContestEntity.getStatusContest().equals(TContestEntity.StatusContest.Terminado)){
            msg = ConstantsTexts.CONTEST_END;
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg));
            LOG.info(msg);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        userEntityDao.findByIdUser(contestDetailRequest.idUser).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_BY_ID));
        ContestDetailEntity contestDetailEntity = new ContestDetailEntity();
        contestDetailEntity.idDetail = new GenerateUuid().generateUuid();
        contestDetailEntity.idUser = contestDetailRequest.idUser;
        contestDetailEntity.idContest = contestDetailRequest.idContest;
        contestDetailEntity.tickCount = contestDetailRequest.tickCount;
        contestDetailEntity.releaseDate = new Date();
        try {
            contestDetailDao.save(contestDetailEntity);
        } catch (TransactionSystemException e) {
            final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            final Validator validator = validatorFactory.getValidator();
            final Set<ConstraintViolation<ContestDetailEntity>> violations = validator.validate(contestDetailEntity);
            for (final ConstraintViolation<ContestDetailEntity> violation : violations) {
                msg += violation.getMessage() + " ";
            }
            LOG.error(msg);
            throw new ConflictException(msg);
        }
        msg = ConstantsTexts.DETAIL_ADD;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
