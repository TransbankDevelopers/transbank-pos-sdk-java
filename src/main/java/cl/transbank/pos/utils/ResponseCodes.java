package cl.transbank.pos.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResponseCodes {
    public static final Map<Integer, String> responseMessage;

    static {
        Map<Integer, String> baseMap = new HashMap<>();
        baseMap.put(0, "Aprobado");
        baseMap.put(1, "Rechazado");
        baseMap.put(2, "Host no responde");
        baseMap.put(3, "Conexión falló");
        baseMap.put(4, "Transacción ya fue anulada");
        baseMap.put(5, "No existe transacción para anular");
        baseMap.put(6, "Tarjeta no soportada");
        baseMap.put(7, "Transacción cancelada desde el POS");
        baseMap.put(8, "No puede anular transacción débito");
        baseMap.put(9, "Error lectura tarjeta");
        baseMap.put(10, "Monto menor al mínimo permitido");
        baseMap.put(11, "No existe venta");
        baseMap.put(12, "Transacción no soportada");
        baseMap.put(13, "Debe ejecutar cierre ");
        baseMap.put(14, "No hay tono");
        baseMap.put(15, "Archivo BITMAP.DAT no encontrado. favor cargue");
        baseMap.put(16, "Error formato respuesta del host");
        baseMap.put(17, "Error en los 4 últimos dígitos.");
        baseMap.put(18, "Menú invalido");
        baseMap.put(19, "ERROR_TARJ_DIST");
        baseMap.put(20, "Tarjeta inválida");
        baseMap.put(21, "Anulación no permitida");
        baseMap.put(22, "TIMEOUT");
        baseMap.put(24, "Impresora sin papel");
        baseMap.put(25, "Fecha inválida");
        baseMap.put(26, "Debe cargar llaves");
        baseMap.put(27, "Debe actualizar");
        baseMap.put(54, "Rechazado");
        baseMap.put(60, "Error en número de cuotas");
        baseMap.put(61, "Error en armado de solicitud");
        baseMap.put(62, "Problema con el pinpad interno");
        baseMap.put(65, "Error al procesar la respuesta del host");
        baseMap.put(67, "Superó número máximo de ventas, debe ejecutar cierre");
        baseMap.put(68, "Error genérico, falla al ingresar montos");
        baseMap.put(70, "Error de formato campo de boleta MAX 6");
        baseMap.put(71, "Error de largo campo de impresión");
        baseMap.put(72, "Error de monto venta, debe ser mayor que 0");
        baseMap.put(73, "Terminal ID no configurado");
        baseMap.put(74, "Debe ejecutar cierre");
        baseMap.put(75, "Comercio no tiene tarjetas configuradas");
        baseMap.put(76, "Superó número máximo de ventas, debe ejecutar cierre");
        baseMap.put(77, "Debe ejecutar cierre");
        baseMap.put(78, "Esperando leer tarjeta");
        baseMap.put(79, "Solicitando confirmar monto");
        baseMap.put(80, "Esperando selección de cuotas");
        baseMap.put(81, "Solicitando ingreso de clave");
        baseMap.put(82, "Enviando transacción al host");
        baseMap.put(83, "Selección menú crédito/redcompra");
        baseMap.put(84, "Opere tarjeta");
        baseMap.put(85, "Selección de cuotas");
        baseMap.put(86, "Ingreso de cuotas");
        baseMap.put(87, "Confirmación de cuotas");
        baseMap.put(88, "Error cantidad cuotas");
        baseMap.put(89, "Opción mes de gracia");
        baseMap.put(90, "Inicialización Exitosa");
        baseMap.put(91, "Inicialización Fallida");
        baseMap.put(92, "Lector no Conectado");
        baseMap.put(93, "Declinada");
        baseMap.put(94, "Error al procesar respuesta");
        baseMap.put(95, "Error al imprimir TASA");
        responseMessage = Collections.unmodifiableMap(baseMap);
    }

    private ResponseCodes() {}

    public static String getResponseMessage(int responseCode) {
        return responseMessage.getOrDefault(responseCode, "Mensaje no encontrado");
    }
}
