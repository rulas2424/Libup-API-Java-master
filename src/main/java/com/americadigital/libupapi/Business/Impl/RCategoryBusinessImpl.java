package com.americadigital.libupapi.Business.Impl;

import com.americadigital.libupapi.Business.Interfaces.RCategoryBusiness;
import com.americadigital.libupapi.Dao.Entity.RCategoryShopEntity;
import com.americadigital.libupapi.Dao.Interfaces.Category.CategoryDao;
import com.americadigital.libupapi.Dao.Interfaces.RCategory.RCategoryDao;
import com.americadigital.libupapi.Dao.Interfaces.Shop.ShopDao;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Pojos.RCategory.RCategoryAdd;
import com.americadigital.libupapi.Pojos.RCategory.RCategoryGet;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.GenerateUuid;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.WsPojos.Request.RCategory.RCategoryRequest;
import com.americadigital.libupapi.WsPojos.Response.RCategory.AddRCategoryResponse;
import com.americadigital.libupapi.WsPojos.Response.RCategory.RCategoryGetResponse;
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

@Service("rCategoryBusinessImpl")
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT)
public class RCategoryBusinessImpl implements RCategoryBusiness {
    private static final Logger LOG = LoggerFactory.getLogger(RCategoryBusinessImpl.class);

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private RCategoryDao rCategoryDao;

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public ResponseEntity<AddRCategoryResponse> addOrUpdateRelation(RCategoryRequest categoryRequest) {
        String msg;
        AddRCategoryResponse response;
        GenerateUuid uuid;
        shopDao.findByIdShop(categoryRequest.idShop).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
        List<RCategoryShopEntity> relationsAddList = new ArrayList<>();
        List<RCategoryShopEntity> listRelations = rCategoryDao.findListRelations(categoryRequest.idShop);
        for (RCategoryAdd rCategoryAddShop : categoryRequest.relations) {
            uuid = new GenerateUuid();
            relationsAddList.add(new RCategoryShopEntity(uuid.generateUuid(), categoryRequest.idShop, rCategoryAddShop.idCategory));
        }
        try {
            rCategoryDao.saveAll(relationsAddList);
            rCategoryDao.deleteAll(listRelations);
        } catch (TransactionSystemException e) {
            relationsAddList.forEach(s -> {
                String messages = "";
                final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
                final Validator validator = validatorFactory.getValidator();
                final Set<ConstraintViolation<RCategoryShopEntity>> violations = validator.validate(s);
                for (final ConstraintViolation<RCategoryShopEntity> violation : violations) {
                    messages += violation.getMessage() + " ";
                }
                LOG.error(messages);
                throw new ConflictException(messages);

            });
        }
        msg = ConstantsTexts.RCATEGORY_UPDATE;
        response = new AddRCategoryResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RCategoryGetResponse> getListRelationsCategories(String idShop) {
        String msg;
        RCategoryGetResponse response;
        List<RCategoryShopEntity> listRelations = rCategoryDao.findByIdShop(idShop);
        if (listRelations.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new RCategoryGetResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        List<RCategoryGet> rCategoryGetList = new ArrayList<>();
        msg = ConstantsTexts.RCATEGORY_LIST;
        listRelations.forEach(r -> {
            if (r.category.active) {
                rCategoryGetList.add(new RCategoryGet(r.idCategory, r.category.name));
            }
        });
        response = new RCategoryGetResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), rCategoryGetList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
