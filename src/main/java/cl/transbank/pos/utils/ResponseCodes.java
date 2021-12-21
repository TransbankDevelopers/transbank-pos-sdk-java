package cl.transbank.pos.utils;

import java.util.HashMap;
import java.util.Map;

public class ResponseCodes {
    public static final Map<Integer, String> map = new HashMap<Integer, String>()
    {
        {
            put(0, "Aprobado");
            put(1, "Rechazado");
            put(2, "Host no responde");
            put(3, "Conexión falló");
            put(4, "Transacción ya fue anulada");
            put(5, "No existe transacción para anular");
            put(6, "Tarjeta no soportada");
            put(7, "Transacción cancelada desde el POS");
            put(8, "No puede anular transacción débito");
            put(9, "Error lectura tarjeta");
            put(10, "Monto menor al mínimo permitido");
            put(11, "No existe venta");
            put(12, "Transacción no soportada");
            put(13, "Debe ejecutar cierre ");
            put(14, "No hay tono");
            put(15, "Archivo BITMAP.DAT no encontrado. favor cargue");
            put(16, "Error formato respuesta del host");
            put(17, "Error en los 4 últimos dígitos.");
            put(18, "Menú invalido");
            put(19, "ERROR_TARJ_DIST");
            put(20, "Tarjeta inválida");
            put(21, "Anulación no permitida");
            put(22, "TIMEOUT");
            put(24, "Impresora sin papel");
            put(25, "Fecha inválida");
            put(26, "Debe cargar llaves");
            put(27, "Debe actualizar");
            put(54, "Rechazado");
            put(60, "Error en número de cuotas");
            put(61, "Error en armado de solicitud");
            put(62, "Problema con el pinpad interno");
            put(65, "Error al procesar la respuesta del host");
            put(67, "Superó número máximo de ventas, debe ejecutar cierre");
            put(68, "Error genérico, falla al ingresar montos");
            put(70, "Error de formato campo de boleta MAX 6");
            put(71, "Error de largo campo de impresión");
            put(72, "Error de monto venta, debe ser mayor que 0");
            put(73, "Terminal ID no configurado");
            put(74, "Debe ejecutar cierre");
            put(75, "Comercio no tiene tarjetas configuradas");
            put(76, "Superó número máximo de ventas, debe ejecutar cierre");
            put(77, "Debe ejecutar cierre");
            put(78, "Esperando leer tarjeta");
            put(79, "Solicitando confirmar monto");
            put(80, "Esperando selección de cuotas");
            put(81, "Solicitando ingreso de clave");
            put(82, "Enviando transacción al host");
            put(83, "Selección menú crédito/redcompra");
            put(84, "Opere tarjeta");
            put(85, "Selección de cuotas");
            put(86, "Ingreso de cuotas");
            put(87, "Confirmación de cuotas");
            put(88, "Error cantidad cuotas");
            put(89, "Opción mes de gracia");
            put(90, "Inicialización Exitosa");
            put(91, "Inicialización Fallida");
            put(92, "Lector no Conectado");
            put(93, "Declinada");
            put(94, "Error al procesar respuesta");
            put(95, "Error al imprimir TASA");
        }
    };
}
