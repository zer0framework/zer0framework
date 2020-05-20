package br.com.zer0framework.utils;

/*
2    * Copyright (c) 2009, Oracle and/or its affiliates. All rights reserved.
3    * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
4    *
5    * This code is free software; you can redistribute it and/or modify it
6    * under the terms of the GNU General Public License version 2 only, as
7    * published by the Free Software Foundation.  Oracle designates this
8    * particular file as subject to the "Classpath" exception as provided
9    * by Oracle in the LICENSE file that accompanied this code.
10    *
11    * This code is distributed in the hope that it will be useful, but WITHOUT
12    * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
13    * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
14    * version 2 for more details (a copy is included in the LICENSE file that
15    * accompanied this code).
16    *
17    * You should have received a copy of the GNU General Public License version
18    * 2 along with this work; if not, write to the Free Software Foundation,
19    * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
20    *
21    * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
22    * or visit www.oracle.com if you need additional information or have any
23    * questions.
24    */

/**
27    * IOUtils: A collection of IO-related public static methods.
28    */

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class IOUtils {

	/**
	 * 40 * Read up to <code>length</code> of bytes from <code>in</code> 41 * until
	 * EOF is detected. 42 * @param in input stream, must not be null 43 * @param
	 * length number of bytes to read, -1 or Integer.MAX_VALUE means 44 * read as
	 * much as possible 45 * @param readAll if true, an EOFException will be thrown
	 * if not enough 46 * bytes are read. Ignored when length is -1 or
	 * Integer.MAX_VALUE 47 * @return bytes read 48 * @throws IOException Any IO
	 * error or a premature EOF is detected 49
	 */
	public static byte[] readFully(InputStream is, int length, boolean readAll) throws IOException {
		byte[] output = {};
		if (length == -1)
			length = Integer.MAX_VALUE;
		int pos = 0;
		while (pos < length) {
			int bytesToRead;
			if (pos >= output.length) { // Only expand when there's no room
				bytesToRead = Math.min(length - pos, output.length + 1024);
				if (output.length < pos + bytesToRead) {
					output = Arrays.copyOf(output, pos + bytesToRead);
				}
			} else {
				bytesToRead = output.length - pos;
			}
			int cc = is.read(output, pos, bytesToRead);
			if (cc < 0) {
				if (readAll && length != Integer.MAX_VALUE) {
					throw new EOFException("Detect premature EOF");
				} else {
					if (output.length != pos) {
						output = Arrays.copyOf(output, pos);
					}
					break;
				}
			}
			pos += cc;
		}
		return output;
	}
}
