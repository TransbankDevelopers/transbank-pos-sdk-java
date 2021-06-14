package cl.transbank.pos.responses;

import cl.transbank.pos.exceptions.NotInstantiableException;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResponseCodes {

    static {
        //las llaves deben ser INTs. Si no, se caera en el inicializador estatico.
        // _NO_ usar un 0 antes del numero porque en java eso significa un numero octal
        Object[][] codeMessage = {
                {0, "Aprobado"},
                {1, "Rechazado"},
                {2, "Host no responde"},
                {3, "Conexión falló. Verifique conexión a internet."},
                {4, "Transacción ya fue anulada"},
                {5, "No existe transacción para anular"},
                {6, "Tarjeta no soportada"},
                {7, "Transacción cancelada desde el POS"},
                {8, "No puede anular transacción Débito"},
                {9, "Error lectura tarjeta"},
                {10, "Monto menor al mínimo permitido"},
                {11, "No existe venta"},
                {12, "Transacción no soportada"},
                {13, "Debe ejecutar cierre "},
                {14, "No hay Tono"},
                {15, "Archivo BITMAP.DAT no encontrado. Favor cargue"},
                {16, "Error formato respuesta del HOST"},
                {17, "Error en los 4 últimos dígitos."},
                {18, "Menú inválido"},
                {19, "ERROR_TARJ_DIST"},
                {20, "Tarjeta inválida"},
                {21, "Anulación no permitida"},
                {22, "Tiempo de espera agotado. Reintente transacción."},
                {24, "Impresora sin papel"},
                {25, "Fecha inválida"},
                {26, "Debe cargar llaves"},
                {27, "Debe Actualizar"},
                {60, "Error en Número de Cuotas"},
                {61, "Error en Armado de Solicitud"},
                {62, "Problema con el Pinpad interno"},
                {65, "Error al Procesar la Respuesta del Host"},
                {67, "Superó Número Máximo de Ventas, Debe Ejecutar Cierre"},
                {68, "Error Genérico, Falla al Ingresar Montos"},
                {70, "Error de formato Campo de Boleta MAX 6"},
                {71, "Error de Largo Campo de Impresión"},
                {72, "Error de Monto Venta, Debe ser Mayor que 0"},
                {73, "Terminal ID no configurado"},
                {74, "Debe Ejecutar Cierre"},
                {75, "Comercio no tiene Tarjetas Configuradas"},
                {76, "Supero Número Máximo de Ventas, Debe Ejecutar Cierre"},
                {77, "Debe Ejecutar Cierre"},
                {78, "Esperando Leer Tarjeta"},
                {79, "Solicitando Confirmar Monto"},
                {81, "Solicitando Ingreso de Clave"},
                {82, "Enviando transacción al Host"},
                {88, "Error Cantidad Cuotas"},
                {93, "Declinada"},
                {94, "Error al Procesar Respuesta"},
                {95, "Error al Imprimir TASA"}
        };
        Map<Integer, String> values = Stream.of(codeMessage).collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));
        map = Collections.unmodifiableMap(values);
    }

    public static final Map<Integer, String> map; //public so programmers can call up a list of possible messages

    public static String getMessage(int code) {
        if (map.containsKey(code)) {
            return map.get(code);
        }
        return "Unexpected code [" + code + "]";
    }

    private ResponseCodes() {
        throw new NotInstantiableException("Do not instantiate this!");
    }
}
