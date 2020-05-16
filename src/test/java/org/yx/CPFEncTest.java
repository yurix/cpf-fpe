package org.yx;

import static org.junit.Assert.*;

import org.junit.Test;
import org.yx.CPFEnc;

public class CPFEncTest {
	private byte[] unsecureFixedKey = new byte[]{
            (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01
    };
	
	  @Test public void test_enc() {
		  CPFEnc sut = new CPFEnc(unsecureFixedKey);
	        assertEquals("08282397202", sut.encrypt("06501307236"));
	    }

}
