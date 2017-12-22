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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;

public class XMLMarshalUtil {
	
	private Marshaller marshaller;
    private Unmarshaller unmarshaller;
    
    public XMLMarshalUtil() {}
    
    public void setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
    }
    
    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    //Converts Object to XML file
    public void doMarshaling(String filename, Object graph) throws IOException {
    	OutputStream fos = null;
        try {
        	fos = new FileOutputStream(filename);
            marshaller.marshal(graph, new StreamResult(fos));
        } finally {
        	if(fos != null)
        		fos.close();
        }
    }
    
    //Converts Object to XML file
    public void doMarshaling(OutputStream fos, Object graph) throws IOException {
        try {
            marshaller.marshal(graph, new StreamResult(fos));
        } finally {
        	if(fos != null)
        		fos.close();
        }
    }
    
    //Converts XML to Java Object
    public Object doUnMarshaling(String filename) throws IOException {
        InputStream fis = null;
        try {
            fis = new FileInputStream(filename);
            return unmarshaller.unmarshal(new StreamSource(fis));
        } finally {
        	if(fis != null)
        		fis.close();
        }
    }
    
    //Converts XML to Java Object
    public Object doUnMarshaling(InputStream fis) throws IOException {
        try {
            return unmarshaller.unmarshal(new StreamSource(fis));
        } finally {
        	if(fis != null)
        		fis.close();
        }
    }
}