/*
 * Copyright (c) OSGi Alliance (2016). All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.osgi.service.zigbee.types;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.osgi.service.zigbee.ZigBeeDataInput;
import org.osgi.service.zigbee.ZigBeeDataOutput;
import org.osgi.service.zigbee.ZigBeeDataTypes;

/**
 * This class contains the common serialize/deserialize methods for all the
 * org.osgi.service.zigbee.types.* classes. The array, bag, set, structure data
 * types are not supported.
 * 
 * @author $Id$
 */
class ZigBeeZCLDefaultSerializer {
	static final long zigBeeTimeZero = 946684800000L;	// 1/1/2000

	/**
	 * Marshal the passed value into a {@code ZigBeeDataOutput} stream,
	 * according the {@code dataType} argument. An
	 * {@code IllegalArgumentException} is thrown when the the passed
	 * {@code value} does not belong to the class allowed for the
	 * {@code dataType}.
	 * 
	 * <p>
	 * Array, bag, set and structure data types are not supported.
	 * 
	 * <p>
	 * If the data type allows that, a null value is marshaled into ZCL Invalid
	 * Value for that data type.
	 *
	 * @param os a {@link ZigBeeDataOutput} stream where to stream the value.
	 *        This parameter cannot be null. If it is nul a
	 *        {@link NullPointerException} is thrown.
	 * 
	 * @param dataType The data type that have to be marshaled on the output
	 *        stream.
	 * 
	 * @param value The value that have to be serialized on the output stream.
	 *        If null is passed this method outputs on the stream the ZigBee
	 *        invalid value related to the specified data type. If the data type
	 *        do not allow any invalid value and the passed value is null an
	 *        {@link IllegalArgumentException} is thrown.
	 * 
	 * @throws IllegalArgumentException Thrown when the the passed {@code value}
	 *         does not belong to the allowed class for the {@code dataType} as
	 *         described in the specification or when the value exceed the range
	 *         allowed by that type (i.e. length for Octet String data types).
	 */
	static void serializeDataType(ZigBeeDataOutput os, short dataType, Object value) {

		if (os == null) {
			throw new NullPointerException();
		}

		if (value == null) {
			/*
			 * the passed value must be marshalled as ZCL Data Type Invalid
			 * Number
			 */

			if (dataType >= ZigBeeDataTypes.UNSIGNED_INTEGER_8 && dataType <= ZigBeeDataTypes.UNSIGNED_INTEGER_64) {
				// This is an Unsigned Integer Data Type

				if (value == null) {
					// serialize an Invalid Value for Unsigned Integers
					switch (dataType) {
						case ZigBeeDataTypes.UNSIGNED_INTEGER_8 :
						case ZigBeeDataTypes.UNSIGNED_INTEGER_16 :
						case ZigBeeDataTypes.UNSIGNED_INTEGER_24 :
						case ZigBeeDataTypes.UNSIGNED_INTEGER_32 :
							os.writeInt(0xffffffff, (dataType & 0x07) + 1);
							return;

						case ZigBeeDataTypes.UNSIGNED_INTEGER_40 :
						case ZigBeeDataTypes.UNSIGNED_INTEGER_48 :
						case ZigBeeDataTypes.UNSIGNED_INTEGER_56 :
						case ZigBeeDataTypes.UNSIGNED_INTEGER_64 :
							os.writeLong(0xffffffffffffffffL, (dataType & 0x07) + 1);
							return;

						default :
							break;

					}
				}
			} else {
				switch (dataType) {
					case ZigBeeDataTypes.BOOLEAN :
					case ZigBeeDataTypes.ENUMERATION_8 :
					case ZigBeeDataTypes.CHARACTER_STRING :
					case ZigBeeDataTypes.OCTET_STRING :
						os.writeByte((byte) 0xff);
						return;

					case ZigBeeDataTypes.ENUMERATION_16 :
					case ZigBeeDataTypes.LONG_CHARACTER_STRING :
					case ZigBeeDataTypes.LONG_OCTET_STRING :
					case ZigBeeDataTypes.CLUSTER_ID :
					case ZigBeeDataTypes.ATTRIBUTE_ID :
						os.writeInt(0xffff, 2);
						return;

					case ZigBeeDataTypes.FLOATING_SEMI :
						os.writeFloat(Float.NaN, 2);
						return;

					case ZigBeeDataTypes.FLOATING_SINGLE :
						os.writeFloat(Float.NaN, 4);
						return;

					case ZigBeeDataTypes.FLOATING_DOUBLE :
						os.writeDouble(Double.NaN);
						return;

					case ZigBeeDataTypes.ARRAY :
					case ZigBeeDataTypes.BAG :
					case ZigBeeDataTypes.SET :
					case ZigBeeDataTypes.STRUCTURE :
						throw new IllegalArgumentException("ZCL data types bag, structure, set, array can not be serialized with this generic method.");

					case ZigBeeDataTypes.TIME_OF_DAY :
					case ZigBeeDataTypes.DATE :
					case ZigBeeDataTypes.UTC_TIME :
					case ZigBeeDataTypes.BACNET_OID :
						os.writeInt(0xffffffff, 4);
						return;

					case ZigBeeDataTypes.IEEE_ADDRESS :
						os.writeLong(0xffffffffffffffffL, 8);
						return;

					case ZigBeeDataTypes.NO_DATA :
						// do nothing!!!
						return;

					default :
						break;
				}
			}
		} else {
			int size = (dataType & 0x07) + 1;
			// serialize the actual value
			switch (dataType) {
				case ZigBeeDataTypes.UNSIGNED_INTEGER_8 :
				case ZigBeeDataTypes.GENERAL_DATA_8 :
				case ZigBeeDataTypes.BITMAP_8 :
				case ZigBeeDataTypes.ENUMERATION_8 :
					if (value instanceof Short) {
						os.writeInt((((Number) value).shortValue()), size);
					} else {
						throw new IllegalArgumentException();
					}
					return;

				case ZigBeeDataTypes.UNSIGNED_INTEGER_16 :
				case ZigBeeDataTypes.UNSIGNED_INTEGER_24 :
				case ZigBeeDataTypes.GENERAL_DATA_16 :
				case ZigBeeDataTypes.GENERAL_DATA_24 :
				case ZigBeeDataTypes.BITMAP_16 :
				case ZigBeeDataTypes.BITMAP_24 :

				case ZigBeeDataTypes.ENUMERATION_16 :
					if (value instanceof Integer) {
						os.writeInt((((Number) value).intValue()), size);
					} else {
						throw new IllegalArgumentException();
					}
					return;

				case ZigBeeDataTypes.UNSIGNED_INTEGER_32 :
				case ZigBeeDataTypes.UNSIGNED_INTEGER_40 :
				case ZigBeeDataTypes.UNSIGNED_INTEGER_48 :
				case ZigBeeDataTypes.UNSIGNED_INTEGER_56 :
				case ZigBeeDataTypes.GENERAL_DATA_32 :
				case ZigBeeDataTypes.GENERAL_DATA_40 :
				case ZigBeeDataTypes.GENERAL_DATA_48 :
				case ZigBeeDataTypes.GENERAL_DATA_56 :
				case ZigBeeDataTypes.BITMAP_32 :
				case ZigBeeDataTypes.BITMAP_40 :
				case ZigBeeDataTypes.BITMAP_48 :
				case ZigBeeDataTypes.BITMAP_56 :
					if (value instanceof Long) {
						os.writeLong((((Number) value).longValue()), size);
					} else {
						throw new IllegalArgumentException();
					}
					return;

				case ZigBeeDataTypes.UNSIGNED_INTEGER_64 :
				case ZigBeeDataTypes.GENERAL_DATA_64 :
				case ZigBeeDataTypes.BITMAP_64 :
					if (value instanceof Long) {
						os.writeLong((((Number) value).longValue()), size);
					} else {
						throw new IllegalArgumentException();
					}
					return;

				case ZigBeeDataTypes.SIGNED_INTEGER_8 :
				case ZigBeeDataTypes.SIGNED_INTEGER_16 :
					if (value instanceof Short) {
						os.writeInt((((Short) value).shortValue()), size);
					} else {
						throw new IllegalArgumentException();
					}
					return;

				case ZigBeeDataTypes.SIGNED_INTEGER_24 :
				case ZigBeeDataTypes.SIGNED_INTEGER_32 :
					if (value instanceof Integer) {
						os.writeInt((((Integer) value).intValue()), size);
					} else {
						throw new IllegalArgumentException();
					}
					return;

				case ZigBeeDataTypes.SIGNED_INTEGER_40 :
				case ZigBeeDataTypes.SIGNED_INTEGER_48 :
				case ZigBeeDataTypes.SIGNED_INTEGER_56 :
					if (value instanceof Number) {
						os.writeLong((((Long) value).longValue()), size);
					} else {
						throw new IllegalArgumentException();
					}
					return;

				case ZigBeeDataTypes.SIGNED_INTEGER_64 :
					if (value instanceof Long) {
						os.writeLong((((Long) value).longValue()), size);
					} else {
						throw new IllegalArgumentException();
					}
					return;

				case ZigBeeDataTypes.BOOLEAN :
					boolean b = ((Boolean) value).booleanValue();
					os.writeByte((byte) (b ? 1 : 0));
					return;

				case ZigBeeDataTypes.CHARACTER_STRING : {
					String s = (String) value;
					if (s.length() > 0xfe) {
						throw new IllegalArgumentException();
					}
					os.writeByte((byte) s.length());
					os.writeBytes(s.getBytes(), s.length());
					return;
				}

				case ZigBeeDataTypes.OCTET_STRING : {
					byte[] array = (byte[]) value;
					if (array.length > 0xfe) {
						throw new IllegalArgumentException();
					}
					os.writeByte((byte) array.length);
					os.writeBytes(array, array.length);
					return;
				}

				case ZigBeeDataTypes.LONG_CHARACTER_STRING : {
					String s = (String) value;
					if (s.length() > 0xfffe) {
						throw new IllegalArgumentException();
					}
					os.writeByte((byte) s.length());
					os.writeBytes(s.getBytes(), s.length());
					return;
				}

				case ZigBeeDataTypes.LONG_OCTET_STRING : {
					byte[] array = (byte[]) value;
					if (array.length > 0xfffe) {
						throw new IllegalArgumentException();
					}
					os.writeByte((byte) array.length);
					os.writeBytes(array, array.length);
					return;
				}

				case ZigBeeDataTypes.CLUSTER_ID :
				case ZigBeeDataTypes.ATTRIBUTE_ID : {
					if (value instanceof Integer) {
						int i = ((Integer) value).intValue();
						os.writeInt(i, 2);
					} else {
						throw new IllegalArgumentException();
					}
					return;
				}

				case ZigBeeDataTypes.FLOATING_SEMI : {
					float f = ((Float) value).floatValue();
					os.writeFloat(f, 2);
					return;
				}

				case ZigBeeDataTypes.FLOATING_SINGLE : {
					float f = ((Float) value).floatValue();
					os.writeFloat(f, 4);
					return;
				}

				case ZigBeeDataTypes.FLOATING_DOUBLE : {
					double d = ((Double) value).doubleValue();
					os.writeDouble(d);
					return;
				}

				case ZigBeeDataTypes.ARRAY :
				case ZigBeeDataTypes.BAG :
				case ZigBeeDataTypes.SET :
				case ZigBeeDataTypes.STRUCTURE :
					throw new IllegalArgumentException("ZCL data types bag, structure, set, array can not be serialized with this generic method.");

				case ZigBeeDataTypes.TIME_OF_DAY : {
					Date d = (Date) value;
					Calendar c = GregorianCalendar.getInstance();
					c.setTime(d);
					os.writeByte((byte) c.get(Calendar.HOUR_OF_DAY));
					os.writeByte((byte) c.get(Calendar.MINUTE));
					os.writeByte((byte) c.get(Calendar.SECOND));
					os.writeByte((byte) c.get(Calendar.MILLISECOND / 10));
					return;
				}
				case ZigBeeDataTypes.DATE : {
					Date d = (Date) value;
					Calendar c = GregorianCalendar.getInstance();
					c.setTime(d);
					os.writeByte((byte) c.get(Calendar.YEAR));
					os.writeByte((byte) c.get(Calendar.MONTH));
					os.writeByte((byte) c.get(Calendar.DAY_OF_MONTH));
					os.writeByte((byte) c.get(Calendar.DAY_OF_WEEK));
					return;
				}

				case ZigBeeDataTypes.UTC_TIME : {
					Date d = (Date) value;
					long utc = d.getTime() - zigBeeTimeZero;
					os.writeInt((int) utc, 4);
					return;
				}

				case ZigBeeDataTypes.IEEE_ADDRESS :
					if (value instanceof Number) {
						os.writeLong((((Number) value).longValue()), 8);
					} else {
						throw new IllegalArgumentException();
					}
					return;

				case ZigBeeDataTypes.SECURITY_KEY_128 : {
					byte[] array = (byte[]) value;
					if (array.length != 8) {
						throw new IllegalArgumentException();
					}
					os.writeBytes(array, array.length);
					return;
				}
			}
		}

		throw new IllegalArgumentException();
	}

	/**
	 * Unarshal {@code ZigBeeDataInput} stream, according the {@code dataType}
	 * argument. An {@code IllegalArgumentException} is thrown when the the
	 * passed {@code value} does not belong to the class allowed for the
	 * {@code dataType}.
	 * 
	 * <p>
	 * Array, bag, set and structure data types are not supported.
	 * 
	 * @param is A valid {@link ZigBeeDataInput} stream instance. This parameter
	 *        cannot be null.
	 * 
	 * @param dataType The data type that have to be deserialized. This value
	 *        must be one of the valid data types constants defined at the
	 *        beginning of this interface, otherwise an IllegalArgumentException
	 *        exception is thrown.
	 * 
	 * @return The deserialized object. The returned value is null if the
	 *         deserialized value is equal to the <em>Invalid Value</em> for the
	 *         specified dataType.
	 * 
	 * @throws IOException in case of problems while deserializing the
	 *         {@code ZigBeeDataInput}. For instance this could happen if in the
	 *         stream the remaining bytes are not enough to be able to unmarshal
	 *         the requested data type.
	 * 
	 * @throws IllegalArgumentException if the passed {@code dataType} is not
	 *         supported.
	 * 
	 * @throws NullPointerException if the passed {@code ZigBeeDataInput} is
	 *         null
	 */
	static Object deserializeDataType(ZigBeeDataInput is, short dataType) throws IOException {
		if (is == null) {
			throw new NullPointerException();
		}
		switch (dataType) {
			case ZigBeeDataTypes.GENERAL_DATA_8 :
			case ZigBeeDataTypes.BITMAP_8 :
			case ZigBeeDataTypes.ENUMERATION_8 : {
				short s = (short) (is.readInt(1) & 0xff);
				return new Short(s);
			}

			case ZigBeeDataTypes.GENERAL_DATA_16 :
			case ZigBeeDataTypes.BITMAP_16 :
			case ZigBeeDataTypes.ENUMERATION_16 : {
				int i = is.readInt(2) & 0xffff;
				return new Integer(i);
			}

			case ZigBeeDataTypes.GENERAL_DATA_24 :
			case ZigBeeDataTypes.BITMAP_24 : {
				int i = is.readInt(3) & 0xffffff;
				return new Integer(i);
			}

			case ZigBeeDataTypes.GENERAL_DATA_32 :
			case ZigBeeDataTypes.BITMAP_32 : {
				long l = is.readLong(4) & 0xffffffff;
				return new Long(l);
			}

			case ZigBeeDataTypes.GENERAL_DATA_40 :
			case ZigBeeDataTypes.BITMAP_40 : {
				long l = is.readLong(5) & 0xffffffffffL;
				if (l == 0xffffffff) {
					return null;
				}
				return new Long(l);
			}

			case ZigBeeDataTypes.GENERAL_DATA_48 :
			case ZigBeeDataTypes.BITMAP_48 : {
				long l = is.readLong(6) & 0xffffffffffffL;
				return new Long(l);
			}

			case ZigBeeDataTypes.GENERAL_DATA_56 :
			case ZigBeeDataTypes.BITMAP_56 : {
				long l = is.readLong(7) & 0xffffffffffffffL;
				return new Long(l);
			}

			case ZigBeeDataTypes.GENERAL_DATA_64 :
			case ZigBeeDataTypes.BITMAP_64 : {
				long l = is.readLong(8) & 0xffffffffffffffffL;
				return BigInteger.valueOf(l);
			}

			case ZigBeeDataTypes.UNSIGNED_INTEGER_8 : {
				short s = (short) (is.readInt(1) & 0xff);
				if (s == 0xff) {
					return null;
				}
				return new Short(s);
			}

			case ZigBeeDataTypes.UNSIGNED_INTEGER_16 : {
				int i = is.readInt(2) & 0xffff;
				if (i == 0xffff) {
					return null;
				}
				return new Integer(i);
			}

			case ZigBeeDataTypes.UNSIGNED_INTEGER_24 : {
				int i = is.readInt(3) & 0xffffff;
				if (i == 0xffffff) {
					return null;
				}
				return new Integer(i);
			}

			case ZigBeeDataTypes.UNSIGNED_INTEGER_32 : {
				long l = is.readLong(4) & 0xffffffff;
				if (l == 0xffffffffL) {
					return null;
				}
				return new Long(l);
			}

			case ZigBeeDataTypes.UNSIGNED_INTEGER_40 : {
				long l = is.readLong(5) & 0xffffffffffL;
				if (l == 0xffffffffffL) {
					return null;
				}
				return new Long(l);
			}

			case ZigBeeDataTypes.UNSIGNED_INTEGER_48 : {
				long l = is.readLong(6) & 0xffffffffffffL;
				if (l == 0xffffffffffffL) {
					return null;
				}
				return new Long(l);
			}

			case ZigBeeDataTypes.UNSIGNED_INTEGER_56 : {
				long l = is.readLong(7) & 0xffffffffffffffL;
				if (l == 0xffffffffffffffL) {
					return null;
				}
				return new Long(l);
			}

			case ZigBeeDataTypes.UNSIGNED_INTEGER_64 : {
				long l = is.readLong(8) & 0xffffffffffffffffL;
				if (l == 0xffffffffffffffffL) {
					return null;
				}
				BigInteger bl = BigInteger.valueOf(l & 0xffffffffL);
				BigInteger bh = BigInteger.valueOf(l >>> 32).shiftLeft(32);
				BigInteger bi = bh.or(bl);

				return bi;
			}

			case ZigBeeDataTypes.SIGNED_INTEGER_8 : {
				short s = is.readByte();
				if (s == Short.MIN_VALUE) {
					return null;
				}
				return new Short(s);
			}

			case ZigBeeDataTypes.SIGNED_INTEGER_16 : {
				short s = (short) is.readInt(2);
				if (s == Short.MIN_VALUE) {
					return null;
				}
				return new Short(s);
			}

			case ZigBeeDataTypes.SIGNED_INTEGER_24 : {
				int i = is.readInt(3);
				if (i == Integer.MIN_VALUE) {
					return null;
				}
				return new Integer(i);
			}

			case ZigBeeDataTypes.SIGNED_INTEGER_32 : {
				long l = is.readLong(4);
				if (l == Long.MIN_VALUE) {
					return null;
				}
				return new Long(l);
			}

			case ZigBeeDataTypes.SIGNED_INTEGER_40 : {
				long l = is.readLong(5);
				if (l == Long.MIN_VALUE) {
					return null;
				}
				return new Long(l);
			}

			case ZigBeeDataTypes.SIGNED_INTEGER_48 : {
				long l = is.readLong(6);
				if (l == Long.MIN_VALUE) {
					return null;
				}
				return new Long(l);
			}

			case ZigBeeDataTypes.SIGNED_INTEGER_56 : {
				long l = is.readLong(7);
				if (l == Long.MIN_VALUE) {
					return null;
				}
				return new Long(l);
			}

			case ZigBeeDataTypes.SIGNED_INTEGER_64 : {
				long l = is.readLong(8);
				if (l == Long.MIN_VALUE) {
					return null;
				}
				return new Long(l);
			}

			case ZigBeeDataTypes.BOOLEAN : {
				byte b = is.readByte();
				if (b == -1) {
					return null;
				} else {
					return (b > 0) ? Boolean.TRUE : Boolean.FALSE;
				}
			}

			case ZigBeeDataTypes.CHARACTER_STRING : {
				byte len = is.readByte();
				if (len == -1) {
					return null;
				}
				byte[] array = is.readBytes(len & 0xff);
				return new String(array);
			}

			case ZigBeeDataTypes.OCTET_STRING : {
				byte len = is.readByte();
				if (len == -1) {
					return null;
				}
				byte[] array = is.readBytes(len & 0xff);
				return new String(array);
			}

			case ZigBeeDataTypes.LONG_CHARACTER_STRING : {
				short len = (short) is.readInt(2);
				if (len == -1) {
					return null;
				}
				byte[] array = is.readBytes(len & 0xffff);
				return new String(array);
			}

			case ZigBeeDataTypes.LONG_OCTET_STRING : {
				short len = (short) is.readInt(2);
				if (len == -1) {
					return null;
				}
				byte[] array = is.readBytes(len & 0xffff);
				return new String(array);
			}

			case ZigBeeDataTypes.CLUSTER_ID :
			case ZigBeeDataTypes.ATTRIBUTE_ID : {
				int value = is.readInt(2);
				if (value == 0xffff) {
					return null;
				}
				return new Integer(value);
			}

			case ZigBeeDataTypes.FLOATING_SEMI : {
				float f = is.readFloat(2);
				/*
				 * this is the right way to compare d with NaN.
				 */
				if (Float.compare(f, Float.NaN) == 0) {
					return null;
				}
				return new Float(f);
			}

			case ZigBeeDataTypes.FLOATING_SINGLE : {
				float f = is.readFloat(4);

				if (Float.compare(f, Float.NaN) == 0) {
					return null;
				}
				return new Float(f);
			}

			case ZigBeeDataTypes.FLOATING_DOUBLE : {
				double d = is.readDouble();
				/*
				 * this is the right way to compare d with NaN.
				 */
				if (Double.compare(d, Double.NaN) == 0) {
					return null;
				}
				return new Double(d);
			}

			case ZigBeeDataTypes.ARRAY :
			case ZigBeeDataTypes.BAG :
			case ZigBeeDataTypes.SET :
			case ZigBeeDataTypes.STRUCTURE :
				throw new IllegalArgumentException("ZCL data types bag, structure, set, array can not be deserialized with this generic method.");

			case ZigBeeDataTypes.TIME_OF_DAY : {
				byte[] value = is.readBytes(4);
				if (value[0] == 0xff && value[1] == 0xff && value[3] == 0xff && value[4] == 0xff) {
					// checks for invalid value
					return null;
				}
				swap(value);
				return value;
			}

			case ZigBeeDataTypes.DATE : {
				int value = is.readInt(4);
				if (value == -1) {
					return null;
				}
				Calendar c = GregorianCalendar.getInstance();

				// NOTE: the Year byte contains years since 1900!
				c.set(Calendar.YEAR, (is.readByte() & 0xff) + 1900);
				c.set(Calendar.MONTH, is.readByte() & 0xff);
				c.set(Calendar.DAY_OF_MONTH, is.readByte() & 0xff);
				c.set(Calendar.DAY_OF_WEEK, is.readByte() & 0xff);

				return c.getTime();
			}

			case ZigBeeDataTypes.UTC_TIME : {
				int value = is.readInt(4);
				if (value == -1) {
					return null;
				}
				return new Date((value & 0xffffffff) + zigBeeTimeZero);
			}

			case ZigBeeDataTypes.IEEE_ADDRESS : {
				byte[] array = is.readBytes(8);
				swap(array);
				BigInteger invalidValue = new BigInteger("-1");
				BigInteger value = new BigInteger(array);
				if (value.equals(invalidValue)) {
					return null;
				}
				return value;
			}

			case ZigBeeDataTypes.SECURITY_KEY_128 : {
				byte[] array = is.readBytes(8);
				swap(array);
				return array;
			}
		}

		throw new IllegalArgumentException();
	}

	private static byte[] swap(byte[] array) {
		int j = array.length - 1;
		for (int i = 0; i < array.length / 2; i++) {
			byte tmp = array[i];
			array[i] = array[j];
			array[j] = tmp;
			j--;
		}
		return array;
	}
}