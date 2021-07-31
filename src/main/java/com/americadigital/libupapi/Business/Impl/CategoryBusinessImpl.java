package com.americadigital.libupapi.Business.Impl;

import com.americadigital.libupapi.Business.Interfaces.CategoryBusiness;
import com.americadigital.libupapi.Dao.Entity.*;
import com.americadigital.libupapi.Dao.Interfaces.Branch.BranchDao;
import com.americadigital.libupapi.Dao.Interfaces.Category.CategoryDao;
import com.americadigital.libupapi.Dao.Interfaces.Promo.PromoDao;
import com.americadigital.libupapi.Dao.Interfaces.RCategory.RCategoryDao;
import com.americadigital.libupapi.Dao.Interfaces.RCategoryPromo.RCategoryPromoDao;
import com.americadigital.libupapi.Dao.Interfaces.Shop.ShopDao;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Pojos.Category.CategoriesActives;
import com.americadigital.libupapi.Pojos.Category.CategoryGet;
import com.americadigital.libupapi.Pojos.Category.ListCategorys;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.GenerateUuid;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.WsPojos.Request.Category.AddCategoryRequest;
import com.americadigital.libupapi.WsPojos.Request.Category.CategoriesActivesRequest;
import com.americadigital.libupapi.WsPojos.Request.Category.CategoryStatusRequest;
import com.americadigital.libupapi.WsPojos.Request.Category.UpdateCategoryRequest;
import com.americadigital.libupapi.WsPojos.Response.Category.CategoryResponse;
import com.americadigital.libupapi.WsPojos.Response.Category.GetAllCategoryActivesResponse;
import com.americadigital.libupapi.WsPojos.Response.Category.GetAllCategoryResponse;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
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

@Service("categoryBusinessImpl")
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT)
public class CategoryBusinessImpl implements CategoryBusiness {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryBusinessImpl.class);

    @Autowired
    private RCategoryPromoDao rCategoryPromoDao;

    @Autowired
    private RCategoryDao rCategoryDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private PromoDao promoDao;

    @Autowired
    private BranchDao branchDao;

    @Autowired
    private ShopDao shopDao;

    @Override
    public ResponseEntity<CategoryResponse> addCategory(AddCategoryRequest request) {
        CategoryResponse response;
        GenerateUuid uuid;
        String msg = "";
        CategoryEntity categoryEntity;
        uuid = new GenerateUuid();
        categoryEntity = new CategoryEntity();
        categoryEntity.idCategory = uuid.generateUuid();
        categoryEntity.name = request.nameCategory;
        CategoryEntity save;
        categoryEntity.active = true;
        try {
            save = categoryDao.save(categoryEntity);
        } catch (TransactionSystemException e) {
            final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            final Validator validator = validatorFactory.getValidator();
            final Set<ConstraintViolation<CategoryEntity>> violations = validator.validate(categoryEntity);
            for (final ConstraintViolation<CategoryEntity> violation : violations) {
                msg += violation.getMessage() + " ";
            }
            LOG.error(msg);
            response = new CategoryResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);

        }
        msg = ConstantsTexts.CATEGORY_ADD;
        response = new CategoryResponse(new HeaderGeneric(ConstantsTexts.CREATED, HttpStatus.CREATED.value(), msg), save);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CategoryResponse> updateCategory(UpdateCategoryRequest request) {
        String msg = "";
        CategoryResponse response;
        CategoryEntity categoryEntity;
        categoryEntity = categoryDao.findByIdCategory(request.idCategory).orElseThrow(() -> new ConflictException(ConstantsTexts.CATEGORY_INVALID));
        categoryEntity.name = request.nameCategory;
        CategoryEntity save;
        try {
            save = categoryDao.save(categoryEntity);

        } catch (TransactionSystemException e) {
            final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            final Validator validator = validatorFactory.getValidator();
            final Set<ConstraintViolation<CategoryEntity>> violations = validator.validate(categoryEntity);

            for (final ConstraintViolation<CategoryEntity> violation : violations) {
                msg += violation.getMessage() + " ";
            }
            LOG.error(msg);
            response = new CategoryResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        msg = ConstantsTexts.CATEGORY_UPDATE;
        response = new CategoryResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), save);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HeaderResponse> changeActiveStatus(CategoryStatusRequest request) {
        String msg;
        HeaderResponse response;
        CategoryEntity categoryEntity;
        categoryEntity = categoryDao.findByIdCategory(request.idCategory).orElseThrow(() -> new ConflictException(ConstantsTexts.CATEGORY_INVALID));
        categoryEntity.active = request.active;
        categoryDao.save(categoryEntity);
        msg = ConstantsTexts.CATEGORY_ACTIVE;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoryResponse> getCategoryById(String idCategory) {
        String msg;
        CategoryResponse response;
        CategoryEntity categoryEntity;
        categoryEntity = categoryDao.findByIdCategory(idCategory).orElseThrow(() -> new ConflictException(ConstantsTexts.CATEGORY_INVALID));
        msg = ConstantsTexts.CATEGORY_GET;
        response = new CategoryResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), categoryEntity);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetAllCategoryResponse> getAllCategories() {
        String msg;
        GetAllCategoryResponse response;
        List<CategoryEntity> categories;
        categories = categoryDao.findAllCategories();
        if (categories.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new GetAllCategoryResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ListCategorys(new ArrayList<>()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        msg = ConstantsTexts.CATEGORY_LIST;
        response = new GetAllCategoryResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ListCategorys(categories));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetAllCategoryActivesResponse> getAllCategoriesActivesPanel() {
        String msg;
        GetAllCategoryActivesResponse response;
        List<CategoryGet> categoryList = new ArrayList<>();
        List<CategoryEntity> allCategoriesActives = categoryDao.findAllCategoriesActives();
        if (allCategoriesActives.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new GetAllCategoryActivesResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new CategoriesActives(new ArrayList<>()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        allCategoriesActives.forEach(categoryEntity -> categoryList.add(new CategoryGet(categoryEntity.idCategory, categoryEntity.name, categoryEntity.active)));
        msg = ConstantsTexts.CATEGORY_LIST;
        response = new GetAllCategoryActivesResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new CategoriesActives(categoryList));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Async
    @Override
    public ResponseEntity<GetAllCategoryActivesResponse> getAllCategoryActives(CategoriesActivesRequest request) {
        if (request.activeLocation) {
            return getCategoriesByDistance(request);
        } else {
            return getCategoriesActives(request);
        }
    }

    @Async
    private ResponseEntity<GetAllCategoryActivesResponse> getCategoriesActives(CategoriesActivesRequest request) {
        String msg;
        GetAllCategoryActivesResponse response;
        List<CategoryEntity> categories;
        List<String> idPromos;
        List<String> promos;
        List<CategoryGet> categoryList = new ArrayList<>();
        categories = categoryDao.findAllCategoriesActives();
        if (categories.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new GetAllCategoryActivesResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new CategoriesActives(new ArrayList<>()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        for (CategoryEntity categoryEntity : categories) {
            promos = new ArrayList<>();
            List<RCategoryPromoEntity> listPromosByCategory = rCategoryPromoDao.findListPromosByCategory(categoryEntity.idCategory);
            //con este foreach checo que pertenezca al estado
            for (RCategoryPromoEntity rCategoryPromoEntity: listPromosByCategory) {
                for (RPromoBranchEntity rPromoBranchEntity: rCategoryPromoEntity.getPromoEntity().rPromoBranchEntity) {
                    if(rPromoBranchEntity.branchEntity.idState.equals(request.idState)){
                        promos.add(rPromoBranchEntity.idPromo);
                    }
                }
            }
            //aqui obtengo las categorías que tengan promociones
            for (String idPromo : promos) {
                idPromos = new ArrayList<>();
                idPromos.add(idPromo);
                List<PromoEntity> listPromosByIdAndType = promoDao.findListPromosByIdAndType(idPromos, request.promoType.toString());
                if (!listPromosByIdAndType.isEmpty()) {
                    boolean existence = categoryList.stream().anyMatch(o -> o.idCategory.equals(categoryEntity.idCategory));
                    if (!existence) {
                        categoryList.add(new CategoryGet(categoryEntity.idCategory, categoryEntity.name, categoryEntity.active));
                    }
                }
            }
        }
        msg = ConstantsTexts.CATEGORY_LIST;
        response = new GetAllCategoryActivesResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new CategoriesActives(categoryList));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Async
    private ResponseEntity<GetAllCategoryActivesResponse> getCategoriesByDistance(CategoriesActivesRequest request) {
        String msg;
        GetAllCategoryActivesResponse response;
        List<CategoryGet> categoryList = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        List<String> shopsList = new ArrayList<>();

        List<String> idPromos;
        List<BranchEntity> branchesNearest = branchDao.findBranchNearestByCoordinates(request.latitude, request.longitude, request.range);
        for (BranchEntity branchEntity : branchesNearest) {
            boolean existence = shopsList.stream().anyMatch(o -> o.equals(branchEntity.idShop));
            if (!existence) {
                //aqui checo que pertenezca al estado
                if (branchEntity.idState.equals(request.idState)) {
                    shopsList.add(branchEntity.idShop);
                }
            }
        }
        for (String idShop : shopsList) {
            List<RCategoryShopEntity> listRelations = rCategoryDao.findListRelations(idShop);
            for (RCategoryShopEntity shopEntity : listRelations) {
                boolean existence = categories.stream().anyMatch(o -> o.equals(shopEntity.idCategory));
                if (!existence) {
                    categories.add(shopEntity.idCategory);
                }
            }
        }
        for (String idCategories : categories) {
            List<RCategoryPromoEntity> listPromosByCategory = rCategoryPromoDao.findListPromosByCategory(idCategories);
            for (RCategoryPromoEntity rCategoryPromoEntity : listPromosByCategory) {
                idPromos = new ArrayList<>();
                idPromos.add(rCategoryPromoEntity.idPromo);
                List<PromoEntity> listPromosByIdAndType = promoDao.findListPromosByIdAndType(idPromos, request.promoType.toString());
                if (!listPromosByIdAndType.isEmpty()) {
                    boolean existence = categoryList.stream().anyMatch(o -> o.idCategory.equals(idCategories));
                    if (!existence) {
                        categoryList.add(new CategoryGet(idCategories, rCategoryPromoEntity.categoryEntity.name, rCategoryPromoEntity.categoryEntity.active));
                    }
                }
            }
        }
        msg = ConstantsTexts.CATEGORY_LIST;
        response = new GetAllCategoryActivesResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new CategoriesActives(categoryList));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
