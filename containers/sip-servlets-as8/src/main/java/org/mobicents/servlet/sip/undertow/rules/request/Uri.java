/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.mobicents.servlet.sip.undertow.rules.request;

import javax.servlet.sip.Address;
import javax.servlet.sip.SipServletRequest;

/**
 * @author Thomas Leseney
 * @author alerant.appngin@gmail.com
 */
public class Uri implements Extractor {
	
	private static final int REQUEST = 1;
	private static final int ADDRESS = 2;
	
    private int inputType;
    
	public Uri(String token) 
    {
		if (token.equals("request")) {
			inputType = REQUEST;
		} else if (token.equals("from")) {
			inputType = ADDRESS;
    	} else if (token.equals("to")) {
			inputType = ADDRESS;
		} else {
			throw new IllegalArgumentException("Invalid expression: uri after " + token);
		}
    }
    
	public Object extract(Object input) {
		if (inputType == REQUEST) {
			return ((SipServletRequest) input).getRequestURI();
		} else { 
			return ((Address) input).getURI();
		}
	}
}
