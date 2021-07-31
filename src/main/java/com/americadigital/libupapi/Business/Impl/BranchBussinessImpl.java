package com.americadigital.libupapi.Business.Impl;

import com.americadigital.libupapi.Business.Interfaces.BranchBusiness;
import com.americadigital.libupapi.Dao.Entity.BranchEntity;
import com.americadigital.libupapi.Dao.Entity.RSheduleEntity;
import com.americadigital.libupapi.Dao.Entity.UserAdminEntity;
import com.americadigital.libupapi.Dao.Interfaces.Admin.UserAdminDao;
import com.americadigital.libupapi.Dao.Interfaces.Branch.BranchDao;
import com.americadigital.libupapi.Dao.Interfaces.RShedule.RSheduleDao;
import com.americadigital.libupapi.Dao.Interfaces.Shop.ShopDao;
import com.americadigital.libupapi.Dao.Interfaces.States.StatesDao;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Pojos.Branch.BranchList;
import com.americadigital.libupapi.Pojos.Branch.BranchPojo;
import com.americadigital.libupapi.Pojos.Branch.Branches;
import com.americadigital.libupapi.Pojos.UserAdmin.UserAdminRepo;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.GenerateUuid;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.WsPojos.Request.Branch.AddBranchRequest;
import com.americadigital.libupapi.WsPojos.Request.Branch.CoordsRequest;
import com.americadigital.libupapi.WsPojos.Request.Branch.StatusBranchRequest;
import com.americadigital.libupapi.WsPojos.Request.Branch.UpdateBranchRequest;
import com.americadigital.libupapi.WsPojos.Response.Branch.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service("branchBussinessImpl")
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT)
public class BranchBussinessImpl implements BranchBusiness {
    private static final Logger LOG = LoggerFactory.getLogger(BranchBussinessImpl.class);

    @Autowired
    private BranchDao branchDao;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private RSheduleDao rSheduleDao;

    @Autowired
    private UserAdminDao userAdminDao;

    @Autowired
    private StatesDao statesDao;

    @Override
    public ResponseEntity<BranchResponse> addBranch(AddBranchRequest branchRequest) {
        String msg = "";
        BranchResponse response;
        BranchEntity save;
        BranchEntity branchEntity = new BranchEntity();
        GenerateUuid uuid = new GenerateUuid();
        shopDao.findByIdShop(branchRequest.idShop).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
        statesDao.findById(branchRequest.idState).orElseThrow(() -> new ConflictException(ConstantsTexts.STATE_INVALID));
        branchEntity.idBranch = uuid.generateUuid();
        branchEntity.idShop = branchRequest.idShop;
        branchEntity.address = branchRequest.address;
        if (branchRequest.phoneNumber.isPresent() && branchRequest.phoneNumber.get().length() > 0) {
            branchEntity.phoneNumber = branchRequest.phoneNumber.get();
        }
        branchEntity.type = branchRequest.branchType;
        branchEntity.latitud = branchRequest.latitude;
        branchEntity.longitud = branchRequest.longitude;
        branchEntity.idState = branchRequest.idState;
        branchEntity.active = true;
        try {
            save = branchDao.save(branchEntity);
        } catch (TransactionSystemException e) {
            final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            final Validator validator = validatorFactory.getValidator();
            final Set<ConstraintViolation<BranchEntity>> violations = validator.validate(branchEntity);
            for (final ConstraintViolation<BranchEntity> violation : violations) {
                msg += violation.getMessage() + " ";
            }
            LOG.error(msg);
            throw new ConflictException(msg);
        }
        response = new BranchResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), ConstantsTexts.BRANCH_ADD), new BranchPojo(save.idBranch));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BranchResponse> updateBranch(UpdateBranchRequest branchRequest) {
        String msg = "";
        BranchResponse response;
        BranchEntity save;
        BranchEntity branchEntity = branchDao.findByIdBranch(branchRequest.idBranch).orElseThrow(() -> new ConflictException(ConstantsTexts.BRANCH_INVALID));
        statesDao.findById(branchRequest.idState).orElseThrow(() -> new ConflictException(ConstantsTexts.STATE_INVALID));
        branchEntity.idShop = branchRequest.idShop;
        branchEntity.address = branchRequest.address;
        if (branchRequest.phoneNumber.isPresent() && branchRequest.phoneNumber.get().length() > 0) {
            branchEntity.phoneNumber = branchRequest.phoneNumber.get();
        }
        branchEntity.type = branchRequest.branchType;
        branchEntity.latitud = branchRequest.latitude;
        branchEntity.longitud = branchRequest.longitude;
        branchEntity.idState = branchRequest.idState;
        try {
            save = branchDao.save(branchEntity);
        } catch (TransactionSystemException e) {
            final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            final Validator validator = validatorFactory.getValidator();
            final Set<ConstraintViolation<BranchEntity>> violations = validator.validate(branchEntity);
            for (final ConstraintViolation<BranchEntity> violation : violations) {
                msg += violation.getMessage() + " ";
            }
            LOG.error(msg);
            throw new ConflictException(msg);
        }
        response = new BranchResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), ConstantsTexts.BRANCH_UPDATE), new BranchPojo(save.idBranch));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BranchResponse> changeStatus(StatusBranchRequest branchRequest) {
        String msg;
        BranchResponse response;

        shopDao.findByIdShop(branchRequest.idShop).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
        List<BranchEntity> branchEntities = branchDao.findActivesBranchesByIdShop(branchRequest.idShop);
        if (branchRequest.active.equals(false)) {
            if (branchEntities.size() == 1) {
                msg = ConstantsTexts.BRANCH_ACTIVE;
                LOG.error(msg);
                throw new ConflictException(msg);
            }
        }
        BranchEntity branchEntity = branchDao.findByIdBranch(branchRequest.idBranch).orElseThrow(() -> new ConflictException(ConstantsTexts.BRANCH_INVALID));
        branchEntity.active = branchRequest.active;
        BranchEntity save = branchDao.save(branchEntity);
        msg = ConstantsTexts.ACTIVE;
        response = new BranchResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new BranchPojo(save.idBranch));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BranchGetResponse> getBranchById(String idBranch) {
        String msg;
        BranchGetResponse response;
        BranchEntity branchEntity = branchDao.findByIdBranch(idBranch).orElseThrow(() -> new ConflictException(ConstantsTexts.BRANCH_INVALID));
        msg = ConstantsTexts.BRANCH_GET;
        List<RSheduleEntity> listSheduleByIdBranch = rSheduleDao.findListSheduleByIdBranch(idBranch);
        response = new BranchGetResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new BranchList(new Branches(branchEntity.idBranch, branchEntity.address, branchEntity.phoneNumber, branchEntity.active, branchEntity.type, branchEntity.idShop, branchEntity.isDeleted, branchEntity.latitud, branchEntity.longitud, listSheduleByIdBranch, branchEntity.idState)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BranchListResponse> getAllBranchesForAdmin(String idShop) {
        String msg;
        BranchListResponse response;
        List<UserAdminRepo> userAdminRepos = new ArrayList<>();
        List<BranchEntity> branchEntities = branchDao.findBranchAll(idShop);
        if (branchEntities.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new BranchListResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>(), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        msg = ConstantsTexts.BRANCH_LIST;
        List<BranchList> branchLists = new ArrayList<>();
        branchEntities.forEach(b -> {
            List<RSheduleEntity> listSheduleByIdBranch = rSheduleDao.findListSheduleByIdBranch(b.idBranch);
            branchLists.add(new BranchList(new Branches(b.idBranch, b.address, b.phoneNumber, b.active, b.type, b.idShop, b.isDeleted, b.latitud, b.longitud, listSheduleByIdBranch, b.idState)));
        });
        List<UserAdminEntity> adminDaoByIdShop = userAdminDao.findByIdShop(idShop);
        adminDaoByIdShop.forEach(userAdminEntity -> {
            userAdminRepos.add(new UserAdminRepo(userAdminEntity.idShop, userAdminEntity.name, userAdminEntity.lastName, userAdminEntity.email, userAdminEntity.phoneNumber));
        });
        response = new BranchListResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), branchLists, userAdminRepos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BranchActivesListResponse> getAllBranches(String idShop) {
        String msg;
        BranchActivesListResponse response;
        List<BranchEntity> branchEntities = branchDao.findBranchAllActives(idShop);
        if (branchEntities.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new BranchActivesListResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        msg = ConstantsTexts.BRANCH_LIST;
        List<Branches> branchLists = new ArrayList<>();
        branchEntities.forEach(b -> {
            List<RSheduleEntity> listSheduleByIdBranch = rSheduleDao.findListSheduleByIdBranch(b.idBranch);
            branchLists.add(new Branches(b.idBranch, b.address, b.phoneNumber, b.active, b.type, b.idShop, b.isDeleted, b.latitud, b.longitud, listSheduleByIdBranch, b.idState));
        });
        response = new BranchActivesListResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), branchLists);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HeaderResponse> deleteBranch(String idBranch) {
        String msg;
        HeaderResponse response;
        BranchEntity branchEntity = branchDao.findByIdBranch(idBranch).orElseThrow(() -> new ConflictException(ConstantsTexts.BRANCH_INVALID));
        branchEntity.isDeleted = true;
        branchDao.save(branchEntity);
        msg = ConstantsTexts.BRANCH_DELETE;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CoordenadasResponse> getNearestStore(CoordsRequest coordsRequest) {
        String msj;
        CoordenadasResponse response;
        List<BranchEntity> nearestByCoordinates = branchDao.findNearestByCoordenates(coordsRequest.latitud, coordsRequest.longitud, ConstantsTexts.RANGE_METERS);
        if (nearestByCoordinates.isEmpty()) {
            msj = ConstantsTexts.COORDINATES_OUT_RANGE;
            LOG.info(msj);
            response = new CoordenadasResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msj), null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        BranchEntity branchEntity = nearestByCoordinates.get(0);
        msj = ConstantsTexts.COORDINATES;
        List<RSheduleEntity> listSheduleByIdBranch = rSheduleDao.findListSheduleByIdBranch(branchEntity.idBranch);
        response = new CoordenadasResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msj), new BranchNearestResponse(branchEntity.idBranch,
                branchEntity.address, branchEntity.phoneNumber, branchEntity.active, branchEntity.type, branchEntity.shopEntity, branchEntity.isDeleted, branchEntity.latitud, branchEntity.longitud, listSheduleByIdBranch, branchEntity.idState));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
