/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.core.tests.internal.databinding.conversion;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.eclipse.core.internal.databinding.conversion.StringToNumberParser;
import org.junit.Test;

/**
 * @since 1.1
 */
public abstract class StringToNumberParserTestHarness {

	protected abstract Number getValidMax();

	protected abstract Number getValidMin();

	protected abstract boolean assertValid(Number number);

	@Test
	public void testRanges() throws Exception {
		Number min = getValidMin();
		Number max = getValidMax();

		double minDouble = min.doubleValue();
		double maxDouble = max.doubleValue();

		//test bytes
		assertTrue("valid byte", assertValid(Byte.valueOf((byte) 1)));
		assertTrue("valid byte min", assertValid(Byte.MIN_VALUE));
		assertTrue("valid byte max", assertValid(Byte.MAX_VALUE));

		// test shorts
		assertTrue("valid short", assertValid(Short.valueOf((short) 1)));
		boolean result = assertValid(Short.MIN_VALUE);
		if (minDouble > Short.MIN_VALUE) {
			assertFalse("invalid short min", result);
		} else {
			assertTrue("valid short min", result);
		}

		result = assertValid(Short.MAX_VALUE);
		if (maxDouble < Short.MAX_VALUE) {
			assertFalse("invalid short max", result);
		} else {
			assertTrue("valid short max", result);
		}

		// test integers
		assertTrue("valid Integer", assertValid(Integer.valueOf(1)));

		result = assertValid(Integer.valueOf(Integer.MIN_VALUE));
		if (minDouble > Integer.MIN_VALUE) {
			assertFalse("invalid Integer min", result);
		} else {
			assertTrue("valid integer min", result);
		}

		result = assertValid(Integer.valueOf(Integer.MAX_VALUE));
		if (maxDouble < Integer.MAX_VALUE) {
			assertFalse("valid Integer max", result);
		} else {
			assertTrue("valid integer max", result);
		}

		// test longs
		assertTrue("valid long", assertValid(Long.valueOf(1)));
		result = assertValid(Long.MIN_VALUE);
		if (minDouble > Long.MIN_VALUE) {
			assertFalse("invalid long min", result);
		} else {
			assertTrue("valid long min", result);
		}

		result = assertValid(Long.MAX_VALUE);
		if (maxDouble < Long.MAX_VALUE) {
			assertFalse("invalid long max", result);
		} else {
			assertTrue("valid long max", result);
		}

		// test floats
		assertTrue("valid float", assertValid(Float.valueOf(1)));
		result = assertValid(Float.valueOf(-Float.MAX_VALUE));
		if (minDouble > -Float.MAX_VALUE) {
			assertFalse("invalid float min", result);
		} else {
			assertTrue("valid float min", result);
		}

		result = assertValid(Float.valueOf(Float.MAX_VALUE));
		if (maxDouble < Float.MAX_VALUE) {
			assertFalse("invalid float max", result);
		} else {
			assertTrue("valid float max", result);
		}

		assertFalse("invalid negative float infinity", assertValid(Float.valueOf(Float.NEGATIVE_INFINITY)));
		assertFalse("invalid positive float infinity", assertValid(Float.valueOf(Float.POSITIVE_INFINITY)));
		assertFalse("invalid float NaN", assertValid(Float.valueOf(Float.NaN)));

		// test doubles
		assertTrue("valid double", assertValid(Double.valueOf(1)));
		result = assertValid(Double.valueOf(-Double.MAX_VALUE));
		if (minDouble > -Double.MAX_VALUE) {
			assertFalse("invalid double min", result);
		} else {
			assertTrue("valid double min", result);
		}

		result = assertValid(Double.valueOf(Double.MAX_VALUE));
		if (maxDouble < Double.MAX_VALUE) {
			assertFalse("invalid float max", result);
		} else {
			assertTrue("valid float max", result);
		}

		assertFalse("invalid negative double infinity", assertValid(Double.valueOf(Double.NEGATIVE_INFINITY)));
		assertFalse("invalid positive double infinity", assertValid(Double.valueOf(Double.POSITIVE_INFINITY)));
		assertFalse("invalid double NaN", assertValid(Double.valueOf(Double.NaN)));

		// test BigIntegers
		assertTrue("valid BigInteger", assertValid(BigInteger.valueOf(1)));
		BigDecimal bigDecimalMin = BigDecimal.valueOf(min.doubleValue());
		bigDecimalMin = bigDecimalMin.subtract(BigDecimal.valueOf(1));
		assertFalse("invalid BigInteger min", assertValid(bigDecimalMin.toBigInteger()));

		BigDecimal bigDecimalMax = BigDecimal.valueOf(max.doubleValue());
		bigDecimalMax = bigDecimalMax.add(BigDecimal.valueOf(1));
		assertFalse("invalid BigInteger max", assertValid(bigDecimalMax.toBigInteger()));

		// test BigDecimals
		assertTrue("valid BigDecimal", assertValid(BigDecimal.valueOf(1)));
		assertFalse("invalid BigDecimal min", assertValid(bigDecimalMin));
		assertFalse("invalid BigDecimal max", assertValid(bigDecimalMax));

		Number number = BigDecimal.valueOf((double) 1);
		assertTrue(StringToNumberParser.inIntegerRange(number));
	}
}
