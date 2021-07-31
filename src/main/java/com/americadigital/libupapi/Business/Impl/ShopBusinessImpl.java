package com.americadigital.libupapi.Business.Impl;

import com.americadigital.libupapi.Business.Interfaces.ShopBusiness;
import com.americadigital.libupapi.Dao.Entity.BranchEntity;
import com.americadigital.libupapi.Dao.Entity.ShopEntity;
import com.americadigital.libupapi.Dao.Entity.UserAdminEntity;
import com.americadigital.libupapi.Dao.Interfaces.Admin.UserAdminDao;
import com.americadigital.libupapi.Dao.Interfaces.Branch.BranchDao;
import com.americadigital.libupapi.Dao.Interfaces.Shop.ShopDao;
import com.americadigital.libupapi.Dao.Interfaces.Shop.ShopPagDao;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Pojos.Shop.ActivePojo;
import com.americadigital.libupapi.Pojos.Shop.GetAllShops;
import com.americadigital.libupapi.Pojos.Shop.ShopGetAll;
import com.americadigital.libupapi.Utils.*;
import com.americadigital.libupapi.WsPojos.Request.Shop.*;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import com.americadigital.libupapi.WsPojos.Response.Shop.AllShopResponse;
import com.americadigital.libupapi.WsPojos.Response.Shop.CommerceResponse;
import com.americadigital.libupapi.WsPojos.Response.Shop.ShopGetAllResponse;
import com.americadigital.libupapi.WsPojos.Response.Shop.ShopResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service("shopBusinessImpl")
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT)
public class ShopBusinessImpl implements ShopBusiness {
    private static final Logger LOG = LoggerFactory.getLogger(ShopBusinessImpl.class);
    private static final String IMAGE_FOLDER = ConstantsTexts.TOMCAT_HOME_PATH + "/" + ConstantsTexts.NAME_FOLDER_STORES + "/";
    private static final String IMAGE_FOLDER_WATER = ConstantsTexts.TOMCAT_HOME_PATH + "/" + ConstantsTexts.NAME_FOLDER_WATER + "/";

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private ShopPagDao shopPagDao;

    @Autowired
    private UserAdminDao userAdminDao;

    @Autowired
    private BranchDao branchDao;

    @Autowired
    private JavaMailSender sender;

    @Override
    public ResponseEntity<CommerceResponse> addCommerce(AddShopRequest shopRequest) {
        CommerceResponse response;
        ShopEntity shopEntity;
        GenerateUuid uuid;
        String msg = "";
        String extension = "";
        String extensionWater = "";
        uuid = new GenerateUuid();
        shopEntity = new ShopEntity();
        shopEntity.idShop = uuid.generateUuid();
        shopEntity.name = shopRequest.getName();
        shopEntity.active = true;
        shopEntity.urlCommerce = shopRequest.urlCommerce;
        GenerateNameImg generateNameImg = new GenerateNameImg();
        String nameImage = generateNameImg.generateUuidImage();
        String nameImageWater = new GenerateNameImg().generateUuidImage();
        //logo de comercio
        if (shopRequest.getLogotype().length() > 0) {
            DecodeBase64 decodeBase64 = new DecodeBase64();
            BufferedImage bufferedImage = decodeBase64.decodeToImage(shopRequest.getLogotype());
            if (bufferedImage != null) {
                extension = GetExtensions.getExtensionImage(shopRequest.getLogotype());
                shopEntity.image = nameImage + extension;
            } else {
                msg = ConstantsTexts.FORMAT_BASE64;
                throw new ConflictException(msg);
            }
        }
        if (shopRequest.getWaterMark().length() > 0) {
            DecodeBase64 decodeBase64 = new DecodeBase64();
            BufferedImage bufferedImage = decodeBase64.decodeToImage(shopRequest.getWaterMark());
            boolean hasAlpha = bufferedImage.getColorModel().hasAlpha();//transparent
            if (!hasAlpha) {
                msg = ConstantsTexts.IMAGE_NOT_TRANSPARENT;
                throw new ConflictException(msg);
            }
            if (bufferedImage != null) {
                extensionWater = GetExtensions.getExtensionImage(shopRequest.getWaterMark());
                shopEntity.waterMark = nameImageWater + extensionWater;
            } else {
                msg = ConstantsTexts.FORMAT_BASE64;
                throw new ConflictException(msg);
            }
        }
        ShopEntity save;
        try {
            save = shopDao.save(shopEntity);
        } catch (TransactionSystemException e) {
            final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            final Validator validator = validatorFactory.getValidator();
            final Set<ConstraintViolation<ShopEntity>> violations = validator.validate(shopEntity);
            for (final ConstraintViolation<ShopEntity> violation : violations) {
                msg += violation.getMessage() + " ";
            }
            LOG.error(msg);
            response = new CommerceResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);

        }
        msg = ConstantsTexts.SHOP_ADD;
        response = new CommerceResponse(new HeaderGeneric(ConstantsTexts.CREATED, HttpStatus.CREATED.value(), msg), save);
        if (shopRequest.getLogotype().length() > 0) {
            try {
                new SaveImages().saveImages(shopRequest.getLogotype(), IMAGE_FOLDER, nameImage, extension);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (shopRequest.getWaterMark().length() > 0) {
            try {
                new SaveImages().saveImages(shopRequest.getWaterMark(), IMAGE_FOLDER_WATER, nameImageWater, extensionWater);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CommerceResponse> updateCommerce(UpdateShopRequest updateShopRequest) {
        CommerceResponse response;
        String msg = "";
        ShopEntity commerce;
        String extension = "";
        String extensionMark = "";
        String logo;
        String waterMark;
        commerce = shopDao.findByIdShop(updateShopRequest.getId_shop()).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
        logo = commerce.image;
        waterMark = commerce.waterMark;
        commerce.name = updateShopRequest.getName();
        commerce.urlCommerce = updateShopRequest.urlCommerce;
        GenerateNameImg generateNameImg = new GenerateNameImg();
        String nameImage = generateNameImg.generateUuidImage();
        String nameImageMark = new GenerateNameImg().generateUuidImage();
        //logo de comercio
        if (updateShopRequest.getLogotype().isPresent() && updateShopRequest.getLogotype().get().length() > 0) {
            DecodeBase64 decodeBase64 = new DecodeBase64();
            BufferedImage bufferedImage = decodeBase64.decodeToImage(updateShopRequest.getLogotype().get());
            if (bufferedImage != null) {
                extension = GetExtensions.getExtensionImage(updateShopRequest.getLogotype().get());
                commerce.image = nameImage + extension;
            } else {
                msg = ConstantsTexts.FORMAT_BASE64;
                throw new ConflictException(msg);
            }
        }

        //watermark
        if (updateShopRequest.getWaterMark().isPresent() && updateShopRequest.getWaterMark().get().length() > 0) {
            DecodeBase64 decodeBase64 = new DecodeBase64();
            BufferedImage bufferedImage = decodeBase64.decodeToImage(updateShopRequest.getWaterMark().get());
            boolean hasAlpha = bufferedImage.getColorModel().hasAlpha();//transparent
            if (!hasAlpha) {
                msg = ConstantsTexts.IMAGE_NOT_TRANSPARENT;
                throw new ConflictException(msg);
            }
            if (bufferedImage != null) {
                extensionMark = GetExtensions.getExtensionImage(updateShopRequest.getWaterMark().get());
                commerce.waterMark = nameImageMark + extensionMark;
            } else {
                msg = ConstantsTexts.FORMAT_BASE64;
                throw new ConflictException(msg);
            }
        }
        ShopEntity save;
        try {
            save = shopDao.save(commerce);
        } catch (TransactionSystemException e) {
            final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            final Validator validator = validatorFactory.getValidator();
            final Set<ConstraintViolation<ShopEntity>> violations = validator.validate(commerce);
            for (final ConstraintViolation<ShopEntity> violation : violations) {
                msg += violation.getMessage() + " ";
            }
            LOG.error(msg);
            response = new CommerceResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        msg = ConstantsTexts.SHOP_UPDATE;
        response = new CommerceResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), save);
        if (updateShopRequest.getLogotype().isPresent() && updateShopRequest.getLogotype().get().length() > 0) {
            SaveImages saveImages = new SaveImages();
            DeleteImages delImg = new DeleteImages();
            try {
                saveImages.saveImages(updateShopRequest.getLogotype().get(), IMAGE_FOLDER, nameImage, extension);
                String msgImage = delImg.deleteImage(IMAGE_FOLDER + logo);
                LOG.error("Error: " + msgImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (updateShopRequest.getWaterMark().isPresent() && updateShopRequest.getWaterMark().get().length() > 0) {
            SaveImages saveImages = new SaveImages();
            DeleteImages delImg = new DeleteImages();
            try {
                saveImages.saveImages(updateShopRequest.getWaterMark().get(), IMAGE_FOLDER_WATER, nameImageMark, extensionMark);
                String msgImage = delImg.deleteImage(IMAGE_FOLDER_WATER + waterMark);
                LOG.error("Error: " + msgImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CommerceResponse> changeActiveStatus(ActivePojo activePojo) {
        String msg;
        CommerceResponse response;
        ShopEntity commerce;
        commerce = shopDao.findByIdShop(activePojo.getId_shop()).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
        commerce.active = activePojo.getActive();
        ShopEntity save = shopDao.save(commerce);
        msg = ConstantsTexts.SHOP_ACTIVE;
        response = new CommerceResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), save);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ShopResponse> getCommerceById(String id_shop) {
        String msg;
        ShopResponse response;
        ShopEntity commerce;
        commerce = shopDao.findByIdShop(id_shop).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
        List<ShopGetAll> shops = new ArrayList<>();
        Optional<List<BranchEntity>> branchByIdShop = branchDao.findBranchByIdShop(commerce.idShop);
        shops.add(new ShopGetAll(commerce.idShop, commerce.name, commerce.active, commerce.image, commerce.waterMark, branchByIdShop.isPresent() ? branchByIdShop.get().size() : 0, commerce.urlCommerce));
        msg = ConstantsTexts.SHOP_GET;
        response = new ShopResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), shops);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<ShopGetAllResponse> getAllShopsPanel(AllShopRequest shopRequest) {
        String msg;
        ShopGetAllResponse response;
        Page<ShopEntity> allCommerces = shopPagDao.findAllCommercesForPanel(PageRequest.of(shopRequest.getPage(), shopRequest.getMaxResults()));
        if (allCommerces.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new ShopGetAllResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new GetAllShops(new ArrayList<>()), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        List<ShopGetAll> shops = new ArrayList<>();

        allCommerces.forEach(commerce -> {
            Optional<List<BranchEntity>> branchByIdShop = branchDao.findBranchByIdShop(commerce.idShop);
            shops.add(new ShopGetAll(commerce.idShop, commerce.name, commerce.active, commerce.image, commerce.waterMark, branchByIdShop.isPresent() ? branchByIdShop.get().size() : 0, commerce.urlCommerce));
        });
        msg = ConstantsTexts.SHOP_LIST;
        response = new ShopGetAllResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new GetAllShops(shops), allCommerces.getTotalPages(), allCommerces.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ShopGetAllResponse> searchCommercesPanel(SearchShopRequest searchShopRequest) {
        String msg;
        ShopGetAllResponse response;
        Page<ShopEntity> allCommerces = shopPagDao.searchCommerces(searchShopRequest.searchText, PageRequest.of(searchShopRequest.page, Integer.MAX_VALUE));
        if (allCommerces.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new ShopGetAllResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new GetAllShops(new ArrayList<>()), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        List<ShopGetAll> shops = new ArrayList<>();

        allCommerces.forEach(commerce -> {
            Optional<List<BranchEntity>> branchByIdShop = branchDao.findBranchByIdShop(commerce.idShop);
            shops.add(new ShopGetAll(commerce.idShop, commerce.name, commerce.active, commerce.image, commerce.waterMark, branchByIdShop.isPresent() ? branchByIdShop.get().size() : 0, commerce.urlCommerce));
        });
        msg = ConstantsTexts.SHOP_LIST;
        response = new ShopGetAllResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new GetAllShops(shops), allCommerces.getTotalPages(), allCommerces.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AllShopResponse> getAllShopsActives(Long idState) {
        String msg;
        AllShopResponse response;
        List<ShopEntity> allCommerces = shopPagDao.findAllComercesActives();
        if (allCommerces.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new AllShopResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        if (idState == null) {
            return getAllShopsActivesAll(allCommerces);
        } else {
            return getAllShopsActivesByIdState(idState, allCommerces);
        }
    }

    private ResponseEntity<AllShopResponse> getAllShopsActivesByIdState(Long idState, List<ShopEntity> allCommerces) {
        String msg;
        AllShopResponse response;
        List<ShopEntity> shopEntities = new ArrayList<>();
        allCommerces.forEach(shopEntity -> {
            if (branchDao.findBranchByIdShop(shopEntity.idShop).isPresent()) {
                branchDao.findBranchByIdShop(shopEntity.idShop).get().forEach(branchEntity -> {
                    boolean existence = shopEntities.stream().anyMatch(o -> o.idShop.equals(shopEntity.idShop));
                    if (!existence) {
                        //aqui checo que perteneza al estado
                        if (branchEntity.idState.equals(idState)) {
                            shopEntities.add(shopEntity);
                        }
                    }
                });
            }
        });
        msg = ConstantsTexts.SHOP_LIST;
        response = new AllShopResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), shopEntities);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private ResponseEntity<AllShopResponse> getAllShopsActivesAll(List<ShopEntity> allCommerces) {
        String msg;
        AllShopResponse response;
        msg = ConstantsTexts.SHOP_LIST;
        response = new AllShopResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), allCommerces);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AllShopResponse> getAllShops() {
        String msg;
        AllShopResponse response;
        List<ShopEntity> allCommerces = shopPagDao.findAllCommerces();
        if (allCommerces.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new AllShopResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        msg = ConstantsTexts.SHOP_LIST;
        response = new AllShopResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), allCommerces);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<HeaderResponse> deleteCommerce(String id_shop) {
        String msg;
        HeaderResponse response;
        ShopEntity commerce;
        commerce = shopDao.findByIdShop(id_shop).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
        commerce.isDeleted = true;
        shopDao.save(commerce);
        msg = ConstantsTexts.SHOP_DELETE;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<HeaderResponse> addCommercesFromPage(AddCommercesRequest request) {
        String msg;
        HeaderResponse response;
        String idShop = new GenerateUuid().generateUuid();
        UserAdminEntity userAdminEntity = new UserAdminEntity();
        userAdminEntity.typeAdmin = UserAdminEntity.TypeAdmin.Comercio;
        userAdminEntity.lastName = request.lastName;
        userAdminEntity.active = true;
        userAdminEntity.name = request.name;
        userAdminEntity.email = request.email;
        userAdminEntity.idAdmin = new GenerateUuid().generateUuid();
        userAdminEntity.password = request.password;
        userAdminEntity.phoneNumber = request.phoneNumber;
        userAdminEntity.isDeleted = false;
        userAdminEntity.profilePicture = null;
        userAdminEntity.idBroadcaster = null;
        userAdminEntity.idShop = idShop;

        ShopEntity shopEntity = new ShopEntity();
        shopEntity.urlCommerce = !request.urlCommerce.isPresent() ? null : request.urlCommerce.orElse(null);
        shopEntity.active = true;
        shopEntity.waterMark = null;
        shopEntity.image = null;
        shopEntity.name = request.commerce;
        shopEntity.isDeleted = false;
        shopEntity.idShop = idShop;
        shopEntity.userAdminEntity = userAdminEntity;

        shopDao.save(shopEntity);
        sendMail(request.email);
        msg = ConstantsTexts.SHOP_ADD;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void sendMail(String email) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(email);
            helper.setFrom("info@libup.mx", "Libup");
            helper.setSubject("Gracias por registrarte en Libup");
            String htmlMsg = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <title>Correo de registro</title>\n" +
                    "    <meta charset=\"UTF-8\"/>\n" +
                    "</head>\n" +
                    "<center>\n" +
                    "    <body style=\"font-family: sans-serif;\">\n" +
                    "    <div\n" +
                    "            style=\"\">\n" +
                    "        <div style=\"background-color: #fff; height: 100%; width: 500px; border: 5px solid #fdb09f; border-radius: 5px;\">\n" +
                    "            <div style=\"text-align: center; padding-top: 18px; background-color: #f3421d;\"><img src=\"https://libup.mx/img/logo_white.png\" width=\"200px\" height=\"auto\" >\n" +
                    "            </div>\n" +
                    "\n" +
                    "            <div style=\"font-size: 14px;\">\n" +
                    "                <p style=\"margin-left: 13px; font-size:20px; color: #ffb845\">Gracias por registrarte, para acceder a tu cuenta, ingresa con tu email y contraseña que generaste.</p>\n" +
                    "            </div>\n" +
                    "\n" +
                    "\n" +
                    "            <div\n" +
                    "                    style=\"text-align: center; padding-top: 15px; padding-bottom: 9px;\">\n" +
                    "                <a name=\"verify\" href=\"" + "https://libup.mx/admin/" + "\" target=\"_blank\"\n" +
                    "                   style=\"padding:10px;background: #f3421d;    FONT-WEIGHT: 900; outline: 0; border: 0; cursor: pointer; color: #fff; box-shadow: 0px 4px 5px 0px Gray !important; height: 45px; width: 199px; border-radius: 4px; font-size: 12px; text-decoration: none;\">\n" +
                    "                    Ingresar a mi cuenta</a>\n" +
                    "\n" +
                    "            </div>\n" +
                    "            <div style=\"padding-left: 13px; font-size: 14px;margin-top: 18px\">\n" +
                    "                <span>Si tiene algún problema para iniciar sesión, no dude en contactarnos gracias</span>\n" +
                    "            </div>\n" +
                    "            <div\n" +
                    "                    style=\"background-color: #c3c1c1; width: 500px; height: 50px; margin-top: 6px; text-align: -webkit-center; padding-top: 20px; font-size: 11px; color: #fff;\">\n" +
                    "                <span>Has recibido este correo porque te registraste en https://libup.mx/. No está seguro de por qué está recibiendo esto, póngase en contacto con nosotros.</span>\n" +
                    "\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "    </body>\n" +
                    "</center>\n" +
                    "</html>";
            message.setContent(htmlMsg, "text/html");
            message.setHeader("Content-Type", "text/plain; charset=UTF-8");
            message.setContent(htmlMsg, "text/html; charset=utf-8");
            LOG.info("ENVIADO");
        } catch (MessagingException e) {
            e.printStackTrace();
            String s = "Error al enviar correo .." + e;
            LOG.info(s);
            throw new ConflictException(s);

        } catch (UnsupportedEncodingException e) {
            String s = "Error al enviar correo .." + e;
            LOG.info(s);
            throw new ConflictException(s);
        }
        sender.send(message);
    }
}
