package com.americadigital.libupapi.Controllers;

import com.americadigital.libupapi.Business.Interfaces.CategoryBusiness;
import com.americadigital.libupapi.Dao.Entity.PromoEntity;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Pojos.Category.CategoriesActives;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.Utils.StringFormatUtilities;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryBusiness categoryBusiness;

    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody final AddCategoryRequest request) {
        String msg;
        CategoryResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /category/add Request: " + request + " at " + new Date());
            return categoryBusiness.addCategory(request);
        } catch (DataIntegrityViolationException e) {
            msg = ConstantsTexts.CATEGORY_DUPLICATE;
            LOG.error(msg);
            response = new CategoryResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new CategoryResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "update",
            method = RequestMethod.PUT,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CategoryResponse> updateCategory(@Valid @RequestBody final UpdateCategoryRequest request) {
        String msg;
        CategoryResponse response;
        try {
            LOG.info("Endpoint entry PUT Method: /category/update Request: " + request + " at " + new Date());
            return categoryBusiness.updateCategory(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new CategoryResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (DataIntegrityViolationException e) {
            msg = ConstantsTexts.CATEGORY_DUPLICATE;
            LOG.error(msg);
            response = new CategoryResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new CategoryResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "status",
            method = RequestMethod.PUT,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> changeStatusCategory(@Valid @RequestBody final CategoryStatusRequest request) {
        String msg;
        HeaderResponse response;
        try {
            LOG.info("Endpoint entry PUT Method: /category/status Request: " + request + " at " + new Date());
            return categoryBusiness.changeActiveStatus(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "getById",
            method = RequestMethod.GET)
    public ResponseEntity<CategoryResponse> getCategoryById(@RequestParam(value = "idCategory") String idCategory) {
        String msg;
        CategoryResponse response;
        try {
            LOG.info("Endpoint entry GET Method: /category/getById Request: " + idCategory + " at " + new Date());
            return categoryBusiness.getCategoryById(idCategory);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new CategoryResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new CategoryResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "getAll",
            method = RequestMethod.GET)
    public ResponseEntity<GetAllCategoryResponse> getAllCategories() {
        String msg;
        GetAllCategoryResponse response;
        try {
            LOG.info("Endpoint entry GET Method: /category/getAll Request: " + " at " + new Date());
            return categoryBusiness.getAllCategories();
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new GetAllCategoryResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "findAll",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<GetAllCategoryActivesResponse> findAllCategoriesActives(@Valid @RequestBody CategoriesActivesRequest request) {
        String msg;
        GetAllCategoryActivesResponse response;
        try {
            LOG.info("Endpoint entry GET Method: /category/findAll Request: " + " at " + new Date());
            return categoryBusiness.getAllCategoryActives(request);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new GetAllCategoryActivesResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new CategoriesActives(new ArrayList<>()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "findAllActives",
            method = RequestMethod.GET)
    public ResponseEntity<GetAllCategoryActivesResponse> findAllCategoriesActivesPannel() {
        String msg;
        GetAllCategoryActivesResponse response;
        try {
            LOG.info("Endpoint entry GET Method: /category/findAll Request: " + " at " + new Date());
            return categoryBusiness.getAllCategoriesActivesPanel();
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new GetAllCategoryActivesResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new CategoriesActives(new ArrayList<>()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
