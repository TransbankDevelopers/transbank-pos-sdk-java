/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 4.0.1
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package cl.transbank.pos.utils;

public enum HexCodes {
  ACK(0x06),
  NAK(0x15),
  STX(0x02),
  ETX(0x03),
  PIPE(0x7C);

  public final int swigValue() {
    return swigValue;
  }

  public static HexCodes swigToEnum(int swigValue) {
    HexCodes[] swigValues = HexCodes.class.getEnumConstants();
    if (swigValue < swigValues.length && swigValue >= 0 && swigValues[swigValue].swigValue == swigValue)
      return swigValues[swigValue];
    for (HexCodes swigEnum : swigValues)
      if (swigEnum.swigValue == swigValue)
        return swigEnum;
    throw new IllegalArgumentException("No enum " + HexCodes.class + " with value " + swigValue);
  }

  @SuppressWarnings("unused")
  private HexCodes() {
    this.swigValue = SwigNext.next++;
  }

  @SuppressWarnings("unused")
  private HexCodes(int swigValue) {
    this.swigValue = swigValue;
    SwigNext.next = swigValue+1;
  }

  @SuppressWarnings("unused")
  private HexCodes(HexCodes swigEnum) {
    this.swigValue = swigEnum.swigValue;
    SwigNext.next = this.swigValue+1;
  }

  private final int swigValue;

  private static class SwigNext {
    private static int next = 0;
  }
}

