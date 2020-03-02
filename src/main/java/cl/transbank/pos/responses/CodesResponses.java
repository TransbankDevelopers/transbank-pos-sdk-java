package cl.transbank.pos.responses;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CodesResponses {

    static {
        //las llaves deben ser INTs. Si no, se caera en el inicializador estatico.
        // _NO_ usar un 0 antes del numero porque en java eso significa un numero octal
        Map<Integer, String> values = Stream.of(new Object[][]{
                {0, "Aprobado"},
                {1, "Rechazado"},
                {2, "Host no Responde"},
                {3, "Conexión Fallo"},
                {4, "Transacción ya Fue Anulada"},
                {5, "No existe Transacción para Anular"},
                {6, "Tarjeta no Soportada"},
                {7, "Transacción Cancelada desde el POS"},
                {8, "No puede Anular Transacción Debito"},
                {9, "Error Lectura Tarjeta"},
                {10, "Monto menor al mínimo permitido"},
                {11, "No existe venta"},
                {12, "Transacción No Soportada"},
                {13, "Debe ejecutar cierre "},
                {14, "No hay Tono"},
                {15, "Archivo BITMAP.DAT no encontrado. Favor cargue"},
                {16, "Error Formato Respuesta del HOST"},
                {17, "Error en los 4 últimos dígitos."},
                {18, "Menú invalido"},
                {19, "ERROR_TARJ_DIST"},
                {20, "Tarjeta Invalida"},
                {21, "Anulación. No Permitida"},
                {22, "TIMEOUT"},
                {24, "Impresora Sin Papel"},
                {25, "Fecha Invalida"},
                {26, "Debe Cargar Llaves"},
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
                {74, "Debe Ejecutar CIERRE"},
                {75, "Comercio no tiene Tarjetas Configuradas"},
                {76, "Supero Número Máximo de Ventas, Debe Ejecutar CIERRE"},
                {77, "Debe Ejecutar Cierre"},
                {78, "Esperando Leer Tarjeta"},
                {79, "Solicitando Confirmar Monto"},
                {81, "Solicitando Ingreso de Clave"},
                {82, "Enviando transacción al Host"},
                {88, "Error Cantidad Cuotas"},
                {93, "Declinada"},
                {94, "Error al Procesar Respuesta"},
                {95, "Error al Imprimir TASA"}
        }).collect(Collectors.toMap(data -> (Integer) data[0], data -> (String) data[1]));
        map = Collections.unmodifiableMap(values);
    }

    public static Map<Integer, String> map;

}
