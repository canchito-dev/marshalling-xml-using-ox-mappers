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
package com.canchitodev.example;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.castor.CastorMarshaller;

@Configuration
public class XMLMarshalBean {
	
	@Bean(name = "xmlBasicHandler")
	public XMLMarshalUtil getXmlBasicHandler() throws IOException{
		XMLMarshalUtil handler = new XMLMarshalUtil();
		handler.setMarshaller(getXmlBasicMarshaller());
		handler.setUnmarshaller(getXmlBasicMarshaller());
		return handler;
	}
	
	@Bean(name = "xmlBasicMarshaller")
	public CastorMarshaller getXmlBasicMarshaller() throws IOException {
		CastorMarshaller castorMarshaller = new CastorMarshaller();
		castorMarshaller.setCastorProperties(this.castorProperties());
		castorMarshaller.setMappingLocation(
				new ClassPathResource("basic/basic-mapping.xml")
		);
		return castorMarshaller;
	}
	
	@Bean(name = "xmlComplexHandler")
	public XMLMarshalUtil getXmlComplexHandler() throws IOException{
		XMLMarshalUtil handler = new XMLMarshalUtil();
		handler.setMarshaller(getXmlComplexMarshaller());
		handler.setUnmarshaller(getXmlComplexMarshaller());
		return handler;
	}
	
	@Bean(name = "xmlComplexMarshaller")
	public CastorMarshaller getXmlComplexMarshaller() throws IOException {
		CastorMarshaller castorMarshaller = new CastorMarshaller();
		castorMarshaller.setCastorProperties(this.castorProperties());
		castorMarshaller.setMappingLocation(
				new ClassPathResource("complex/complex-mapping.xml")
		);
		return castorMarshaller;
	}
	
	@Bean(name = "xmlFieldConvertionHandler")
	public XMLMarshalUtil getXmlFieldConvertionHandler() throws IOException{
		XMLMarshalUtil handler = new XMLMarshalUtil();
		handler.setMarshaller(getXmlFieldConvertionMarshaller());
		handler.setUnmarshaller(getXmlFieldConvertionMarshaller());
		return handler;
	}
	
	@Bean(name = "xmlFieldConvertionMarshaller")
	public CastorMarshaller getXmlFieldConvertionMarshaller() throws IOException {
		CastorMarshaller castorMarshaller = new CastorMarshaller();
		castorMarshaller.setCastorProperties(this.castorProperties());
		castorMarshaller.setMappingLocation(
				new ClassPathResource("field-convertion/field-convertion-mapping.xml")
		);
		return castorMarshaller;
	}
	
	private HashMap<String, String> castorProperties() {
    	HashMap<String, String> properties = new HashMap<String, String>();
        properties.put("org.exolab.castor.indent", "true");
        properties.put("org.exolab.castor.debug", "true");
        return properties;
    }
}
