/*
 * Copyright (c) 2015. Instituto de Informática (UFG)
 */

package br.inf.ufg.fabrica.mr;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Valida decisões de tipos em Java empregados pela
 * implementação do Modelo de Referência. Os tipos
 * estão definidos em Support Information Model.
 *
 * <p>Os tipos {@code char}, {@code boolean}, {@code int},
 * {@code float} e {@code double}, são respectivamente as
 * implementações de Character, Boolean, Integer, Real e
 * Double.
 * </p>
 *
 * <p>Ao contrário dos tipos primeitivos acima, o tipo Octet
 * exige atenção especial. Isto porque em Java o tipo byte é
 * com sinal. Embora contenha exatamente 8 bits, armazenar um
 * valor de 0 a 255, inclusive, em um byte, exige operação
 * específica para recuperação do valor. Ou seja, o armazenamento
 * pode ser um byte, mas a recuperação exige o método
 * {@code #obtenhaByte}, documentado abaixo.</p>
 */
public class ModeloDeReferenciaSupportTest {

    @Test
    public void armazeneByte() {
        byte b = 0;
        for (int i = 0; i < 256; i++) {
            b = (byte)i;
            assertEquals(i, obtenhaByte(b));
        }
    }

    /**
     * Obtém valor inteiro correspondente a um
     * valor de 0 a 255, armazenado em um byte.
     *
     * @param entrada Valor do byte (com sinal em Java).
     * @return Valor inteiro, de 0 a 255, inclusive,
     * armazenado em um único byte com sinal.
     */
    public static int obtenhaByte(byte entrada) {
        return entrada & 0xFF;
    }

}