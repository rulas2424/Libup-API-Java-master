package com.americadigital.libupapi.Utils;

import java.text.SimpleDateFormat;

public class ConstantsTexts {


    //Shops
    public static final String SHOP_ADD = "Se registro exitosamente el comercio.";
    public static final String SHOP_GET = "Se obtuvo exitosamente el comercio.";
    public static final String SHOP_DELETE = "Se elimino exitosamente el comercio.";
    public static final String SHOP_UPDATE = "Se actualizo exitosamente el comercio.";
    public static final String SHOP_INVALID = "No existe el comercio.";
    public static final String SHOP_ACTIVE = "Se actualizo exitosamente el estatus.";
    public static final String SHOP_LIST = "Se obtuvo exitosamente la lista de comercios.";
    public static final String EMPTY_LIST = "No existen elementos dentro de la lista.";
    public static final String STATUS_CHANGE = "Agrega sucursales para poder cambiar el status.";
    public static final String SHOP_DUPLICATE = "El comercio ya se encuentra registrado.";

    //BRANCH
    public static final String BRANCH_ADD = "Se registro exitosamente la sucursal.";
    public static final String BRANCH_INVALID = "No existe la sucursal.";
    public static final String BRANCH_UPDATE = "Se actualizo exitosamente la sucursal.";
    public static final String BRANCH_ACTIVE = "Al menos, debe de estar activa una sucursal por comercio.";
    public static final String BRANCH_GET = "Se obtuvo exitosamente la sucursal.";
    public static final String BRANCH_LIST = "Se obtuvo exitosamente la lista de sucursales.";
    public static final String BRANCH_DELETE = "Se elimino exitosamente la sucursal.";
    public static final String COORDINATES = "Te encuentras dentro del rango de un comercio.";
    public static final String COORDINATES_OUT_RANGE = "No te encuentras cerca de ningún comercio.";
    public static final Integer RANGE_METERS = 50;

    //STATES
    public static final String STATE_INVALID = "No existe el id del estado.";
    public static final String STATE_LIST = "Se obtuvo exitosamente la lista de estados.";

    //BROADCASTER
    public static final String BROADCASTER_ADD = "Se registro exitosamente la radiodifusora.";
    public static final String BROADCASTER_UPDATE = "Se actualizo exitosamente la radiodifusora.";
    public static final String BROADCASTER_INVALID = "No existe la radiodifusora.";
    public static final String BROADCASTER_GET = "Se obtuvo exitosamente la radiodifusora.";
    public static final String USER_BROADCASTER = "No puedes iniciar sesión, la radiodifusora al que perteneces no se encuentra activo.";
    public static final String BROADCASTER_LIST = "Se obtuvo exitosamente la lista de radiodifusoras.";
    public static final String BROADCASTER_DUPLICATE = "La radiodifusora ya se encuentra registrada.";

    //BROADCASTER SHOP RELATION
    public static final String BROADCASTER_SHOP_ADD = "Se agrego exitosamente a tu lista de radiodifusoras.";
    public static final String BROADCASTER_SHOP_INVALID = "No existe el id de relación.";
    public static final String BROADCASTER_SHOP_DELETE = "Se elimino exitosamente de tu lista de radiodifusoras.";
    public static final String BROADCASTER_SHOP_LIST = "Se obtuvo exitosamente la lista de relaciones.";
    public static final String BROADCASTER_SHOP_IS_ADD = "El comercio ya tiene contrato con la radiodifusora: ";
    public static final String BROADCASTER_RELATION = "Se actualizo el estatus del contrato.";

    //CHANNELS
    public static final String CHANNELS_ADD = "Se registro exitosamente el canal.";
    public static final String CHANNELS_UPDATE = "Se actualizo exitosamente el canal.";
    public static final String CHANNEL_INVALID = "No existe el canal.";
    public static final String CHANNEL_GET = "Se obtuvo exitosamente el canal.";
    public static final String CHANNEL_LIST = "Se obtuvo exitosamente la lista de canales.";

    //SCHEDULE
    public static final String SCHEDULE_ADD = "Se agrego exitosamente el horario.";
    public static final String SCHEDULE_UPDATE = "Se actualizo exitosamente el horario.";
    public static final String SCHEDULE_LIST = "Se obtuvo exitosamente los horarios.";

    //users
    public static final String USER_INVALID = "Usuario o contraseña invalido.";
    public static final String PASS_INVALID = "No se pudo actualizar, la contraseña actual no es valida.";
    public static final String PASS_UPDATE = "Se actualizo exitosamente la contraseña.";
    public static final String FORMAT_BASE64 = "El string de la imagen no es un formato valido base64.";
    public static final String IMAGE_NOT_TRANSPARENT = "La marca de agua debe de ser una imagen sin fondo.";
    public static final String FILE_EXCEPTION = "El directorio no existe, no se pudo guardar la imagen.";
    public static final String USER_VALID = "Sesión iniciada.";
    public static final String USER_BYID = "No existe el usuario administrador.";
    public static final String USER_BY_ID = "No existe el usuario.";
    public static final String USER_COMMERCE = "No puedes iniciar sesión el comercio al que perteneces no se encuentra activo.";
    public static final String USER_ADD = "Se registro exitosamente el usuario.";
    public static final String USER_COORDINATES = "Se actualizarón exitosamente las coordenadas.";
    public static final String USER_UPDATE = "Se actualizo exitosamente el usuario.";
    public static final String USER_DUPLICATE = "El usuario ya se encuentra registrado.";
    public static final String USER_NOT_ACTIVE = "El usuario no se encuentra activo, contacta al administrador.";
    public static final String USER_LIST = "Se obtuvo exitosamente la lista de usuarios.";
    public static final String USER_GET = "Se obtuvo exitosamente el usuario.";
    public static final String USER_DELETE = "Se elimino exitosamente el usuario.";
    public static final String SHOP_PLAN = "Se obtuvo exitosamente el plan.";
    public static final String ACTIVE = "Se actualizo exitosamente el estatus.";
    public static final String DELETE = "Se elimino exitosamente el registro.";
    public static final String JWT_GET = "Se obtuvo exitosamente el jwt.";


    //acr cloud
    public static final String BUCKETS_GET = "Se obtuvierón exitosamente los buckets.";
    public static final String BUCKETS_DELETE = "Se elimino exitosamente el bucket: ";
    public static final String BUCKETS_ADD = "Se añadió exitosamente el bucket.";
    public static final String BUCKET_EQUAL = "El nombre ya ha sido tomado.";

    //AUDIOS Y CONTEST
    public static final String AUDIO_ERROR = "Ocurrio un error al subir el audio.";
    public static final String AUDIO_DELETE = "Ocurrio un error al eliminar el audio.";
    public static final String AUDIO_CONTEST_ADD = "Se añadió exitosamente el concurso.";
    public static final String AUDIO_CONTEST_UPDATE = "Se actualizo exitosamente el concurso.";
    public static final String CONTEST_INVALID = "No existe el concurso.";
    public static final String ACR_INVALID = "El id de AcrCloud no existe.";
    public static final String CONTEST_GET = "Se obtuvo exitosamente el concurso.";
    public static final String CONTEST_LIST = "Se obtuvo exitosamente la lista de concursos.";
    public static final String NOTIFICATE_ERROR = "Ocurrio un error al enviar las notificaciones. ";
    public static final String SEND_NOTIFY = "Se notificó exitosamente a los usuarios.";
    public static final String SEND_NOTIFY_WINNER = "Se notificó exitosamente al ganador.";
    public static final String TERMINATE_CONTEST = "El concurso se termino exitosamente y se notificó al ganador.";


    //CONTEST DETAIL
    public static final String DETAIL_ADD = "Se registro exitosamente tus ticketeos.";
    public static final String CONTEST_END = "El concurso ya fue terminado.";

    //WINNERS
    public static final String NOT_WINNER = "No hubo ganadores.";
    public static final String WINNER = "Ganaste un premio.";
    public static final String WINNER_INVALID = "No existe el ganador.";
    public static final String WINNER_LIST = "Se obtuvo exitosamente el historial de ganadores.";
    public static final String WINNER_STATUS = "Se cambio el estatus del premio a 'Reclamado'.";
    public static final String WINNER_USERS = "Se obtuvo exitosamente tu historial de premios.";
    public static final String WINNERS = "Se obtuvo exitosamente los registros.";
    public static final String NOT_AWARDS = "No existen premios.";


    //CATEGORY
    public static final String CATEGORY_DUPLICATE = "La categoría ya se encuentra registrada.";
    public static final String CATEGORY_ADD = "Se registro exitosamente la categoría.";
    public static final String CATEGORY_UPDATE = "Se actualizo exitosamente la categoría.";
    public static final String CATEGORY_INVALID = "No existe la categoría.";
    public static final String CATEGORY_ACTIVE = "Se actualizo exitosamente el estatus.";
    public static final String CATEGORY_GET = "Se obtuvo exitosamente la categoría.";
    public static final String CATEGORY_LIST = "Se obtuvo exitosamente la lista de categorías.";

    //PROMOCIONES
    public static final String PROMO_ADD = "Se creo exitosamente la promoción.";
    public static final String PROMO_UPDATE = "Se actualizo exitosamente la promoción.";
    public static final String PROMO_INVALID = "No existe la promoción.";
    public static final String PREMIO_INVALID = "No existe el premio.";
    public static final String PROMO_LIST = "Se obtuvo exitosamente las promociones.";
    public static final String PROMO_GET = "Se obtuvo exitosamente el registro.";
    public static final String PROMO_DELETE_CONTEST = "No se puede eliminar, pertenece a un concurso creado.";
    public static final String PROMO_DELETE = "No se puede eliminar, pertenece a un premio de consolación.";


    //RELATIONS CATEGORY SHOP
    public static final String RCATEGORY_UPDATE = "Datos actualizados exitosamente.";
    public static final String RCATEGORY_LIST = "Se obtuvo exitosamente las relaciones.";


    //SEVERITY TEXTS

    public static final String CONFLICT = "CONFLICT";
    public static final String CREATED = "CREATED";
    public static final String SUCCESS = "SUCCESS";
    public static final String BAD_REQUEST = "BAD REQUEST";
    public static final String UNAUTHORIZED = "UNAUTHORIZED";
    public static final String NOT_FOUND = "NOT FOUND";
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL SERVER ERROR";
    public static final String UNPROCESSABLE_ENTITY = "UNPROCESSABLE ENTITY";
    public static final String PRIMARY_KEY = "Error en la bd: Primary Key duplicada, intenta nuevamente.";
    public static final String ERROR_MESSAGE = "Ocurrio un error al enviar el mensaje.";

    //HOUR
    public static final String HOUR_GET = "Se obtuvo exitosamente la hora del sistema.";

    //fecha

    public static final SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
    public static final SimpleDateFormat fechaMsg = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static final SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss a");
    public static final SimpleDateFormat onlyDate = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat fechaPromo = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final String DATE_INVALID = "Formato de fecha invalido";

    //KEYS NOTIFIY
    public static final String FIREBASE_KEY = "AAAA6jzXzYw:APA91bFNbcpblwOJn9rw2SLhmgXAig65USzxuBEYsBikKDIuHBS0LJsQWC5dNzXONonets6L1eKzJLMtET8iOHR5upcFk-VBE3f4jZJTr3F0gxqVDZtnj6rdtlUoHaxoXB9rllzo2Gns";
    public static final String FIREBASE_URL = "https://fcm.googleapis.com/fcm/send";

    //MESSAGES
    public static final String MESSAGE_NOTIFY = "¡Ponte Oreja!, Nuevos premios";
    public static final String LOSER_MSG = "¡Perdiste!, Sigue participando";
    public static final String WINNER_MSG = "¡Felicidades!";
    public static final String DISCOUNTS_MSG = "Tenemos un descuento especial! ";
    public static final String PROMOS_MSG = "¡Tenemos una promoción especial! ";

    public static final String MSG_SEND = "Mensaje enviado con éxito! ";
    public static final String MSG_LIST = "Se obtuvo exitosamente la lista de mensajes.";

    //TYPE NOTIFICATIONS
    public static final String TICKTEAR_TYPE = "ticktear";
    public static final String TICKTEAR_NOT_AUDIO = "SinAudioTicktear";
    public static final String TICKTEAR_LOSERS = "Losers";
    public static final String WINNER_TYPE = "winnerTicktear";
    public static final String WINNER_DIRECT_TYPE = "directWinner";
    public static final String DISCOUNT_TYPE = "descuentos";
    public static final String PROMO_TYPE = "promociones";

    //URL DE IMAGENES Y AUDIO
    public static final String TOMCAT_HOME_PROPERTY = "catalina.home";
    public static final String TOMCAT_HOME_PATH = System.getProperty(TOMCAT_HOME_PROPERTY);

    public static final String NAME_FOLDER_AUDIO = "webapps/audios";
    public static final String NAME_FOLDER_PROMOS = "webapps/images/libup/promos";
    public static final String NAME_FOLDER_PROMOS_LOST = "webapps/images/libup/promos/lost";

    public static final String NAME_FOLDER_STORES = "webapps/images/libup/stores";
    public static final String NAME_FOLDER_WATER = "webapps/images/libup/stores/watermarks";

    public static final String NAME_FOLDER_PROFILE_ADMIN = "webapps/images/libup/profile_pictures/admin";
    public static final String NAME_FOLDER_PROFILE_USER = "webapps/images/libup/profile_pictures/user";

    public static final String NAME_FOLDER_RADIO = "webapps/images/libup/radio";
    public static final String NAME_FOLDER_ADJUNTOS = "webapps/images/libup/adjuntos";
    public static final String NAME_FOLDER_CHANNELS = "webapps/images/libup/canales";
}
