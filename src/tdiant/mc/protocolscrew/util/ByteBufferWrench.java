package tdiant.mc.protocolscrew.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ByteBufferWrench {
    /**
     * Reads an UTF8 string from a byte buffer.
     *
     * @param buf The byte buffer to read from
     * @return The read string
     * @throws IOException If the reading fails
     */
    public static String readUTF8(DataInput buf) throws IOException
    {
        // Read the string's length
        final int len = readVarInt(buf);
        final byte[] bytes = new byte[len];
        buf.readFully(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Writes an UTF8 string to a byte buffer.
     *
     * @param buf The byte buffer to write too
     * @param value The string to write
     * @throws IOException If the writing fails
     */
    public static void writeUTF8(DataOutput buf, String value) throws IOException {
        final byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        if (bytes.length >= Short.MAX_VALUE) {
            throw new IOException("Attempt to write a string with a length greater than Short.MAX_VALUE to ByteBuf!");
        }
        // Write the string's length
        writeVarInt(buf, bytes.length);
        buf.write(bytes);
    }

    /**
     * Reads an integer written into the byte buffer as one of various bit sizes.
     *
     * @param buf The byte buffer to read from
     * @return The read integer
     * @throws IOException If the reading fails
     */
    public static int readVarInt(DataInput buf) throws IOException {
        int out = 0;
        int bytes = 0;
        byte in;
        while (true) {
            in = buf.readByte();
            out |= (in & 0x7F) << (bytes * 7);
            if (bytes > 32) {
                throw new IOException("Attempt to read int bigger than allowed for a varint!");
            }
            if ((in & 0x80) != 0x80) {
                break;
            }
        }
        return out;
    }

    /**
     * Writes an integer into the byte buffer using the least possible amount of bits.
     *
     * @param buf The byte buffer to write too
     * @param value The integer value to write
     */
    public static void writeVarInt(DataOutput buf, int value) throws IOException
    {
        int part;
        while (true) {
            part = value & 0x7F;
            value >>>= 7;
            if (value != 0) {
                part |= 0x80;
            }
            buf.writeByte(part);
            if (value == 0) {
                break;
            }
        }
    }

    public static void writeVarLong(DataOutput buf, long value) throws IOException {
        do {
            byte temp = (byte)(value & 0b01111111);
            // Note: >>> means that the sign bit is shifted with the rest of the number rather than being left alone
            value >>>= 7;
            if (value != 0) {
                temp |= 0b10000000;
            }
            buf.write(temp);
        } while (value != 0);
    }

    public static long readVarLong(DataInput buf) throws IOException {
        int numRead = 0;
        long result = 0;
        byte read;
        do {
            read = buf.readByte();
            int value = (read & 0b01111111);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 10) {
                throw new RuntimeException("VarLong is too big");
            }
        } while ((read & 0b10000000) != 0);

        return result;
    }
}
