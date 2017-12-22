/**
 * This content is released under the MIT License (MIT)
 *
 * Copyright (c) 2017, canchito-dev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 * @author 		José Carlos Mendoza Prego
 * @copyright	Copyright (c) 2017, canchito-dev (http://www.canchito-dev.com)
 * @license		http://opensource.org/licenses/MIT	MIT License
 * @link		https://github.com/canchito-dev/marshalling-xml-using-ox-mappers
 **/
package com.canchitodev.example.field.handlers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.exolab.castor.mapping.GeneralizedFieldHandler;

/**
 * The GeneralizedFieldHandler for the Date class
 * A org.exolab.castor.mapping.GeneralizedFieldHandler is an extension of FieldHandler interface where we simply write the conversion 
 * methods and Castor will automatically handle the underlying get/set operations. This allows us to re-use the same FieldHandler for 
 * fields from different classes that require the same conversion.
 **/
public class DateHandler extends GeneralizedFieldHandler  {
	
	private static final String SOURCE_FORMAT = "dd/MM/yyyy";

	/**
     * Creates a new DateHandler instance
     **/
	public DateHandler() {
		super();
	}

	/**
     * This method is used to convert the value when the getValue method is called. The getValue method will
     * obtain the actual field value from given 'parent' object.
     * 
     * This convert method is then invoked with the field's value. The value returned from this method will be
     * the actual value returned by getValue method.
     *
     * @param value the object value to convert after  performing a get operation
     * @return the converted value.
     **/
	@Override
	public Object convertUponGet(Object value) {
        SimpleDateFormat formatter = new SimpleDateFormat(SOURCE_FORMAT);
		if (value == null) return formatter.format(new Date());
        Date date = (Date)value;
        return formatter.format(date);
	}

	/**
     * This method is used to convert the value when the setValue method is called. The setValue method will
     * call this method to obtain the converted value.
     * 
     * The converted value will then be used as the value to set for the field.
     *
     * @param value the object value to convert before performing a set operation
     * @return the converted value.
     **/
	@Override
	public Object convertUponSet(Object value) {
        SimpleDateFormat formatter = new SimpleDateFormat(SOURCE_FORMAT);
        Date date = null;
        try {
            date = formatter.parse((String)value);
        }
        catch(ParseException px) {
            throw new IllegalArgumentException(px.getMessage());
        }
        return date;
	}

	/**
     * Returns the class type for the field that this
     * GeneralizedFieldHandler converts to and from. This should be the type that is used in the object model.
     *
     * @return the class type of of the field
     **/
	@SuppressWarnings("rawtypes")
	@Override
	public Class getFieldType() {
		return Date.class;
	}
	
	/**
     * Creates a new instance of the object described by this field.
     *
     * @param parent The object for which the field is created
     * @return A new instance of the field's value
     * @throws IllegalStateException This field is a simple type and cannot be instantiated
     **/
    public Object newInstance(Object parent) throws IllegalStateException{
        //-- Since it's marked as a string...just return null,
        //-- it's not needed.
        return null;
    }
}