package com.himamis.retex.renderer.desktop.graphics

import java.util.Arrays

/**
 * A very fast and memory efficient class to encode and decode to and from
 * BASE64 in full accordance with RFC 2045.<br></br>
 * <br></br>
 * On Windows XP sp1 with 1.4.2_04 and later ;), this encoder and decoder is
 * about 10 times faster on small arrays (10 - 1000 bytes) and 2-3 times as fast
 * on larger arrays (10000 - 1000000 bytes) compared to
 * `sun.misc.Encoder()/Decoder()`.<br></br>
 * <br></br>
 *
 * On byte arrays the encoder is about 20% faster than Jakarta Commons Base64
 * Codec for encode and about 50% faster for decoding large arrays. This
 * implementation is about twice as fast on very small arrays (&lt; 30 bytes).
 * If source/destination is a `String` this version is about three
 * times as fast due to the fact that the Commons Codec result has to be recoded
 * to a `String` from `byte[]`, which is very expensive.
 * <br></br>
 * <br></br>
 *
 * This encode/decode algorithm doesn't create any temporary arrays as many
 * other codecs do, it only allocates the resulting array. This produces less
 * garbage and it is possible to handle arrays twice as large as algorithms that
 * create a temporary array. (E.g. Jakarta Commons Codec). It is unknown whether
 * Sun's `sun.misc.Encoder()/Decoder()` produce temporary arrays but
 * since performance is quite low it probably does.<br></br>
 * <br></br>
 *
 * The encoder produces the same output as the Sun one except that the Sun's
 * encoder appends a trailing line separator if the last character isn't a pad.
 * Unclear why but it only adds to the length and is probably a side effect.
 * Both are in conformance with RFC 2045 though.<br></br>
 * Commons codec seem to always att a trailing line separator.<br></br>
 * <br></br>
 *
 * **Note!** The encode/decode method pairs (types) come in three versions
 * with the **exact** same algorithm and thus a lot of code redundancy. This
 * is to not create any temporary arrays for transcoding to/from different
 * format types. The methods not used can simply be commented out.<br></br>
 * <br></br>
 *
 * There is also a "fast" version of all decode methods that works the same way
 * as the normal ones, but har a few demands on the decoded input. Normally
 * though, these fast verions should be used if the source if the input is known
 * and it hasn't bee tampered with.<br></br>
 * <br></br>
 *
 * If you find the code useful or you find a bug, please send me a note at
 * base64 @ miginfocom . com.
 *
 * Licence (BSD): ==============
 *
 * Copyright (c) 2004, Mikael Grev, MiG InfoCom AB. (base64 @ miginfocom . com)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. Neither the name of the MiG InfoCom AB nor the names
 * of its contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * @version 2.2
 * @author Mikael Grev Date: 2004-aug-02 Time: 11:31:11
 */
object Base64 {
    private val CA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
        .toCharArray()
    private val IA = IntArray(256)

    init {
        Arrays.fill(IA, -1)
        var i = 0
        val iS = CA.size
        while (i < iS) {
            IA[CA[i].code] = i
            i++
        }
        IA['='.code] = 0
    }
    // ****************************************************************************************
    // * char[] version
    // ****************************************************************************************
    /**
     * Encodes a raw byte array into a BASE64 `char[]` representation
     * i accordance with RFC 2045.
     *
     * @param sArr
     * The bytes to convert. If `null` or length 0 an
     * empty array will be returned.
     * @param lineSep
     * Optional "\r\n" after 76 characters, unless end of file.<br></br>
     * No line separator will be in breach of RFC 2045 which
     * specifies max 76 per line but will be a little faster.
     * @return A BASE64 encoded array. Never `null`.
     */
    fun encodeToChar(sArr: ByteArray?, lineSep: Boolean): CharArray {
        // Check special case
        val sLen = sArr?.size ?: 0
        if (sLen == 0) {
            return CharArray(0)
        }
        val eLen = sLen / 3 * 3 // Length of even 24-bits.
        val cCnt = (sLen - 1) / 3 + 1 shl 2 // Returned character count
        val dLen = cCnt + if (lineSep) (cCnt - 1) / 76 shl 1 else 0 // Length of
        // returned
        // array
        val dArr = CharArray(dLen)

        // Encode even 24-bits
        var s = 0
        var d = 0
        var cc = 0
        while (s < eLen) {
            // Copy next three bytes into lower 24 bits of int, paying attension
            // to sign.
            val i = sArr!![s++].toInt() and 0xff shl 16 or (
                    sArr[s++].toInt() and 0xff shl 8
                    ) or (sArr[s++].toInt() and 0xff)

            // Encode the int into four chars
            dArr[d++] = CA[i ushr 18 and 0x3f]
            dArr[d++] = CA[i ushr 12 and 0x3f]
            dArr[d++] = CA[i ushr 6 and 0x3f]
            dArr[d++] = CA[i and 0x3f]

            // Add optional line separator
            if (lineSep && ++cc == 19 && d < dLen - 2) {
                dArr[d++] = '\r'
                dArr[d++] = '\n'
                cc = 0
            }
        }

        // Pad and encode last bits if source isn't even 24 bits.
        val left = sLen - eLen // 0 - 2.
        if (left > 0) {
            // Prepare the int
            val i = (
                    sArr!![eLen].toInt() and 0xff shl 10
                            or if (left == 2) sArr[sLen - 1].toInt() and 0xff shl 2 else 0
                    )

            // Set last four chars
            dArr[dLen - 4] = CA[i shr 12]
            dArr[dLen - 3] = CA[i ushr 6 and 0x3f]
            dArr[dLen - 2] = if (left == 2) CA[i and 0x3f] else '='
            dArr[dLen - 1] = '='
        }
        return dArr
    }

    /**
     * Decodes a BASE64 encoded char array. All illegal characters will be
     * ignored and can handle both arrays with and without line separators.
     *
     * @param sArr
     * The source array. `null` or length 0 will return an
     * empty array.
     * @return The decoded array of bytes. May be of length 0. Will be
     * `null` if the legal characters (including '=') isn't
     * divideable by 4. (I.e. definitely corrupted).
     */
    fun decode(sArr: CharArray?): ByteArray? {
        // Check special case
        val sLen = sArr?.size ?: 0
        if (sLen == 0) {
            return ByteArray(0)
        }

        // Count illegal characters (including '\r', '\n') to know what size the
        // returned array will be,
        // so we don't have to reallocate & copy it later.
        var sepCnt = 0 // Number of separator characters. (Actually illegal
        // characters, but that's a bonus...)
        for (i in 0 until sLen) {
            // separators or illegal chars) base64
            // this loop can be commented out.
            if (IA[sArr!![i].code] < 0) {
                sepCnt++
            }
        }

        // Check so that legal chars (including '=') are evenly divideable by 4
        // as specified in RFC 2045.
        if ((sLen - sepCnt) % 4 != 0) {
            return null
        }
        var pad = 0
        var i = sLen
        while (i > 1 && IA[sArr!![--i].code] <= 0) {
            if (sArr[i] == '=') {
                pad++
            }
        }
        val len = ((sLen - sepCnt) * 6 shr 3) - pad
        val dArr = ByteArray(len) // Preallocate byte[] of exact length
        var s = 0
        var d = 0
        while (d < len) {
            // Assemble three bytes into an int from four "valid" characters.
            var i = 0
            var j = 0
            while (j < 4) {
                // j only increased if a valid char
                // was found.
                val c = IA[sArr!![s++].code]
                if (c >= 0) {
                    i = i or (c shl 18 - j * 6)
                } else {
                    j--
                }
                j++
            }
            // Add the bytes
            dArr[d++] = (i shr 16).toByte()
            if (d < len) {
                dArr[d++] = (i shr 8).toByte()
                if (d < len) {
                    dArr[d++] = i.toByte()
                }
            }
        }
        return dArr
    }

    /**
     * Decodes a BASE64 encoded char array that is known to be resonably well
     * formatted. The method is about twice as fast as [.decode].
     * The preconditions are:<br></br>
     * + The array must have a line length of 76 chars OR no line separators at
     * all (one line).<br></br>
     * + Line separator must be "\r\n", as specified in RFC 2045 + The array
     * must not contain illegal characters within the encoded string<br></br>
     * + The array CAN have illegal characters at the beginning and end, those
     * will be dealt with appropriately.<br></br>
     *
     * @param sArr
     * The source array. Length 0 will return an empty array.
     * `null` will throw an exception.
     * @return The decoded array of bytes. May be of length 0.
     */
    fun decodeFast(sArr: CharArray): ByteArray {
        // Check special case
        val sLen = sArr.size
        if (sLen == 0) {
            return ByteArray(0)
        }
        var sIx = 0
        var eIx = sLen - 1 // Start and end index after trimming.

        // Trim illegal chars from start
        while (sIx < eIx && IA[sArr[sIx].code] < 0) {
            sIx++
        }

        // Trim illegal chars from end
        while (eIx > 0 && IA[sArr[eIx].code] < 0) {
            eIx--
        }

        // get the padding count (=) (0, 1 or 2)
        val pad = if (sArr[eIx] == '=') (if (sArr[eIx - 1] == '=') 2 else 1) else 0 // Count
        // '='
        // at
        // end.
        val cCnt = eIx - sIx + 1 // Content count including possible separators
        val sepCnt = if (sLen > 76) (if (sArr[76] == '\r') cCnt / 78 else 0) shl 1 else 0
        val len = ((cCnt - sepCnt) * 6 shr 3) - pad // The number of decoded
        // bytes
        val dArr = ByteArray(len) // Preallocate byte[] of exact length

        // Decode all but the last 0 - 2 bytes.
        var d = 0
        var cc = 0
        val eLen = len / 3 * 3
        while (d < eLen) {
            // Assemble three bytes into an int from four "valid" characters.
            val i = IA[sArr[sIx++].code] shl 18 or (
                    IA[sArr[sIx++].code] shl 12
                    ) or (IA[sArr[sIx++].code] shl 6) or IA[sArr[sIx++].code]

            // Add the bytes
            dArr[d++] = (i shr 16).toByte()
            dArr[d++] = (i shr 8).toByte()
            dArr[d++] = i.toByte()

            // If line separator, jump over it.
            if (sepCnt > 0 && ++cc == 19) {
                sIx += 2
                cc = 0
            }
        }
        if (d < len) {
            // Decode last 1-3 bytes (incl '=') into 1-3 bytes
            var i = 0
            var j = 0
            while (sIx <= eIx - pad) {
                i = i or (IA[sArr[sIx++].code] shl 18 - j * 6)
                j++
            }
            var r = 16
            while (d < len) {
                dArr[d++] = (i shr r).toByte()
                r -= 8
            }
        }
        return dArr
    }
    // ****************************************************************************************
    // * byte[] version
    // ****************************************************************************************
    /**
     * Encodes a raw byte array into a BASE64 `byte[]` representation
     * i accordance with RFC 2045.
     *
     * @param sArr
     * The bytes to convert. If `null` or length 0 an
     * empty array will be returned.
     * @param lineSep
     * Optional "\r\n" after 76 characters, unless end of file.<br></br>
     * No line separator will be in breach of RFC 2045 which
     * specifies max 76 per line but will be a little faster.
     * @return A BASE64 encoded array. Never `null`.
     */
    fun encodeToByte(sArr: ByteArray?, lineSep: Boolean): ByteArray {
        // Check special case
        val sLen = sArr?.size ?: 0
        if (sLen == 0) {
            return ByteArray(0)
        }
        val eLen = sLen / 3 * 3 // Length of even 24-bits.
        val cCnt = (sLen - 1) / 3 + 1 shl 2 // Returned character count
        val dLen = cCnt + if (lineSep) (cCnt - 1) / 76 shl 1 else 0 // Length of
        // returned
        // array
        val dArr = ByteArray(dLen)

        // Encode even 24-bits
        var s = 0
        var d = 0
        var cc = 0
        while (s < eLen) {
            // Copy next three bytes into lower 24 bits of int, paying attension
            // to sign.
            val i = sArr!![s++].toInt() and 0xff shl 16 or (
                    sArr[s++].toInt() and 0xff shl 8
                    ) or (sArr[s++].toInt() and 0xff)

            // Encode the int into four chars
            dArr[d++] = CA[i ushr 18 and 0x3f].code.toByte()
            dArr[d++] = CA[i ushr 12 and 0x3f].code.toByte()
            dArr[d++] = CA[i ushr 6 and 0x3f].code.toByte()
            dArr[d++] = CA[i and 0x3f].code.toByte()

            // Add optional line separator
            if (lineSep && ++cc == 19 && d < dLen - 2) {
                dArr[d++] = '\r'.code.toByte()
                dArr[d++] = '\n'.code.toByte()
                cc = 0
            }
        }

        // Pad and encode last bits if source isn't an even 24 bits.
        val left = sLen - eLen // 0 - 2.
        if (left > 0) {
            // Prepare the int
            val i = (
                    sArr!![eLen].toInt() and 0xff shl 10
                            or if (left == 2) sArr[sLen - 1].toInt() and 0xff shl 2 else 0
                    )

            // Set last four chars
            dArr[dLen - 4] = CA[i shr 12].code.toByte()
            dArr[dLen - 3] = CA[i ushr 6 and 0x3f].code.toByte()
            dArr[dLen - 2] = if (left == 2) CA[i and 0x3f].code.toByte() else '='.code.toByte()
            dArr[dLen - 1] = '='.code.toByte()
        }
        return dArr
    }

    /**
     * Decodes a BASE64 encoded byte array. All illegal characters will be
     * ignored and can handle both arrays with and without line separators.
     *
     * @param sArr
     * The source array. Length 0 will return an empty array.
     * `null` will throw an exception.
     * @return The decoded array of bytes. May be of length 0. Will be
     * `null` if the legal characters (including '=') isn't
     * divideable by 4. (I.e. definitely corrupted).
     */
    fun decode(sArr: ByteArray): ByteArray? {
        // Check special case
        val sLen = sArr.size

        // Count illegal characters (including '\r', '\n') to know what size the
        // returned array will be,
        // so we don't have to reallocate & copy it later.
        var sepCnt = 0 // Number of separator characters. (Actually illegal
        // characters, but that's a bonus...)
        for (i in 0 until sLen) {
            // separators or illegal chars) base64
            // this loop can be commented out.
            if (IA[sArr[i].toInt() and 0xff] < 0) {
                sepCnt++
            }
        }

        // Check so that legal chars (including '=') are evenly divideable by 4
        // as specified in RFC 2045.
        if ((sLen - sepCnt) % 4 != 0) {
            return null
        }
        var pad = 0
        var i = sLen
        while (i > 1 && IA[sArr[--i].toInt() and 0xff] <= 0) {
            if (sArr[i] == '='.code.toByte()) {
                pad++
            }
        }
        val len = ((sLen - sepCnt) * 6 shr 3) - pad
        val dArr = ByteArray(len) // Preallocate byte[] of exact length
        var s = 0
        var d = 0
        while (d < len) {
            // Assemble three bytes into an int from four "valid" characters.
            var i = 0
            var j = 0
            while (j < 4) {
                // j only increased if a valid char
                // was found.
                val c = IA[sArr[s++].toInt() and 0xff]
                if (c >= 0) {
                    i = i or (c shl 18 - j * 6)
                } else {
                    j--
                }
                j++
            }

            // Add the bytes
            dArr[d++] = (i shr 16).toByte()
            if (d < len) {
                dArr[d++] = (i shr 8).toByte()
                if (d < len) {
                    dArr[d++] = i.toByte()
                }
            }
        }
        return dArr
    }

    /**
     * Decodes a BASE64 encoded byte array that is known to be resonably well
     * formatted. The method is about twice as fast as [.decode].
     * The preconditions are:<br></br>
     * + The array must have a line length of 76 chars OR no line separators at
     * all (one line).<br></br>
     * + Line separator must be "\r\n", as specified in RFC 2045 + The array
     * must not contain illegal characters within the encoded string<br></br>
     * + The array CAN have illegal characters at the beginning and end, those
     * will be dealt with appropriately.<br></br>
     *
     * @param sArr
     * The source array. Length 0 will return an empty array.
     * `null` will throw an exception.
     * @return The decoded array of bytes. May be of length 0.
     */
    fun decodeFast(sArr: ByteArray): ByteArray {
        // Check special case
        val sLen = sArr.size
        if (sLen == 0) {
            return ByteArray(0)
        }
        var sIx = 0
        var eIx = sLen - 1 // Start and end index after trimming.

        // Trim illegal chars from start
        while (sIx < eIx && IA[sArr[sIx].toInt() and 0xff] < 0) {
            sIx++
        }

        // Trim illegal chars from end
        while (eIx > 0 && IA[sArr[eIx].toInt() and 0xff] < 0) {
            eIx--
        }

        // get the padding count (=) (0, 1 or 2)
        val pad =
            if (sArr[eIx] == '='.code.toByte()) (if (sArr[eIx - 1] == '='.code.toByte()) 2 else 1) else 0 // Count
        // '='
        // at
        // end.
        val cCnt = eIx - sIx + 1 // Content count including possible separators
        val sepCnt =
            if (sLen > 76) (if (sArr[76] == '\r'.code.toByte()) cCnt / 78 else 0) shl 1 else 0
        val len = ((cCnt - sepCnt) * 6 shr 3) - pad // The number of decoded
        // bytes
        val dArr = ByteArray(len) // Preallocate byte[] of exact length

        // Decode all but the last 0 - 2 bytes.
        var d = 0
        var cc = 0
        val eLen = len / 3 * 3
        while (d < eLen) {
            // Assemble three bytes into an int from four "valid" characters.
            val i = IA[sArr[sIx++].toInt()] shl 18 or (
                    IA[sArr[sIx++].toInt()] shl 12
                    ) or (IA[sArr[sIx++].toInt()] shl 6) or IA[sArr[sIx++].toInt()]

            // Add the bytes
            dArr[d++] = (i shr 16).toByte()
            dArr[d++] = (i shr 8).toByte()
            dArr[d++] = i.toByte()

            // If line separator, jump over it.
            if (sepCnt > 0 && ++cc == 19) {
                sIx += 2
                cc = 0
            }
        }
        if (d < len) {
            // Decode last 1-3 bytes (incl '=') into 1-3 bytes
            var i = 0
            var j = 0
            while (sIx <= eIx - pad) {
                i = i or (IA[sArr[sIx++].toInt()] shl 18 - j * 6)
                j++
            }
            var r = 16
            while (d < len) {
                dArr[d++] = (i shr r).toByte()
                r -= 8
            }
        }
        return dArr
    }
    // ****************************************************************************************
    // * String version
    // ****************************************************************************************
    /**
     * Encodes a raw byte array into a BASE64 `String` representation
     * i accordance with RFC 2045.
     *
     * @param sArr
     * The bytes to convert. If `null` or length 0 an
     * empty array will be returned.
     * @param lineSep
     * Optional "\r\n" after 76 characters, unless end of file.<br></br>
     * No line separator will be in breach of RFC 2045 which
     * specifies max 76 per line but will be a little faster.
     * @return A BASE64 encoded array. Never `null`.
     */
    fun encodeToString(sArr: ByteArray?, lineSep: Boolean): String {
        // Reuse char[] since we can't create a String incrementally anyway and
        // StringBuffer/Builder would be slower.
        return String(encodeToChar(sArr, lineSep))
    }

    /**
     * Decodes a BASE64 encoded `String`. All illegal characters will
     * be ignored and can handle both strings with and without line separators.
     * <br></br>
     * **Note!** It can be up to about 2x the speed to call
     * `decode(str.toCharArray())` instead. That will create a
     * temporary array though. This version will use `str.charAt(i)`
     * to iterate the string.
     *
     * @param str
     * The source string. `null` or length 0 will return
     * an empty array.
     * @return The decoded array of bytes. May be of length 0. Will be
     * `null` if the legal characters (including '=') isn't
     * divideable by 4. (I.e. definitely corrupted).
     */
    fun decode(str: String?): ByteArray? {
        // Check special case
        val sLen = str?.length ?: 0
        if (sLen == 0) {
            return ByteArray(0)
        }

        // Count illegal characters (including '\r', '\n') to know what size the
        // returned array will be,
        // so we don't have to reallocate & copy it later.
        var sepCnt = 0 // Number of separator characters. (Actually illegal
        // characters, but that's a bonus...)
        for (i in 0 until sLen) {
            // separators or illegal chars) base64
            // this loop can be commented out.
            if (IA[str!![i].code] < 0) {
                sepCnt++
            }
        }

        // Check so that legal chars (including '=') are evenly divideable by 4
        // as specified in RFC 2045.
        if ((sLen - sepCnt) % 4 != 0) {
            return null
        }

        // Count '=' at end
        var pad = 0
        var i = sLen
        while (i > 1 && IA[str!![--i].code] <= 0) {
            if (str[i] == '=') {
                pad++
            }
        }
        val len = ((sLen - sepCnt) * 6 shr 3) - pad
        val dArr = ByteArray(len) // Preallocate byte[] of exact length
        var s = 0
        var d = 0
        while (d < len) {
            // Assemble three bytes into an int from four "valid" characters.
            var i = 0
            var j = 0
            while (j < 4) {
                // j only increased if a valid char
                // was found.
                val c = IA[str!![s++].code]
                if (c >= 0) {
                    i = i or (c shl 18 - j * 6)
                } else {
                    j--
                }
                j++
            }
            // Add the bytes
            dArr[d++] = (i shr 16).toByte()
            if (d < len) {
                dArr[d++] = (i shr 8).toByte()
                if (d < len) {
                    dArr[d++] = i.toByte()
                }
            }
        }
        return dArr
    }

    /**
     * Decodes a BASE64 encoded string that is known to be resonably well
     * formatted. The method is about twice as fast as [.decode].
     * The preconditions are:<br></br>
     * + The array must have a line length of 76 chars OR no line separators at
     * all (one line).<br></br>
     * + Line separator must be "\r\n", as specified in RFC 2045 + The array
     * must not contain illegal characters within the encoded string<br></br>
     * + The array CAN have illegal characters at the beginning and end, those
     * will be dealt with appropriately.<br></br>
     *
     * @param s
     * The source string. Length 0 will return an empty array.
     * `null` will throw an exception.
     * @return The decoded array of bytes. May be of length 0.
     */
    fun decodeFast(s: String): ByteArray {
        // Check special case
        val sLen = s.length
        if (sLen == 0) {
            return ByteArray(0)
        }
        var sIx = 0
        var eIx = sLen - 1 // Start and end index after trimming.

        // Trim illegal chars from start
        while (sIx < eIx && IA[s[sIx].code and 0xff] < 0) {
            sIx++
        }

        // Trim illegal chars from end
        while (eIx > 0 && IA[s[eIx].code and 0xff] < 0) {
            eIx--
        }

        // get the padding count (=) (0, 1 or 2)
        val pad = if (s[eIx] == '=') (if (s[eIx - 1] == '=') 2 else 1) else 0 // Count
        // '='
        // at
        // end.
        val cCnt = eIx - sIx + 1 // Content count including possible separators
        val sepCnt = if (sLen > 76) (if (s[76] == '\r') cCnt / 78 else 0) shl 1 else 0
        val len = ((cCnt - sepCnt) * 6 shr 3) - pad // The number of decoded
        // bytes
        val dArr = ByteArray(len) // Preallocate byte[] of exact length

        // Decode all but the last 0 - 2 bytes.
        var d = 0
        var cc = 0
        val eLen = len / 3 * 3
        while (d < eLen) {
            // Assemble three bytes into an int from four "valid" characters.
            val i = IA[s[sIx++].code] shl 18 or (
                    IA[s[sIx++].code] shl 12
                    ) or (IA[s[sIx++].code] shl 6) or IA[s[sIx++].code]

            // Add the bytes
            dArr[d++] = (i shr 16).toByte()
            dArr[d++] = (i shr 8).toByte()
            dArr[d++] = i.toByte()

            // If line separator, jump over it.
            if (sepCnt > 0 && ++cc == 19) {
                sIx += 2
                cc = 0
            }
        }
        if (d < len) {
            // Decode last 1-3 bytes (incl '=') into 1-3 bytes
            var i = 0
            var j = 0
            while (sIx <= eIx - pad) {
                i = i or (IA[s[sIx++].code] shl 18 - j * 6)
                j++
            }
            var r = 16
            while (d < len) {
                dArr[d++] = (i shr r).toByte()
                r -= 8
            }
        }
        return dArr
    }
}
