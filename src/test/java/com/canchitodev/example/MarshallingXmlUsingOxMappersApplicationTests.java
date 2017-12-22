package com.canchitodev.example;

import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MarshallingXmlUsingOxMappersApplicationTests {
	
	@Autowired 
	protected ApplicationContext applicationContext;

	@Test
	@Ignore("Status... Passed")
	public void basicExample() {
		System.out.println("********* BASIC EXAMPLE *********");
		
		System.out.println("Loading source data to read from classpath");
		ClassPathResource resource = new ClassPathResource("basic/basic-example.xml");
		
		// Get the marshaling converter
		System.out.println("Loading XML Marshal Utility");
		XMLMarshalUtil parser = (XMLMarshalUtil) applicationContext.getBean("xmlBasicHandler");
		
		try {
			System.out.println("Reading source XML file");
			com.canchitodev.example.domain.basic.Platform platform = (com.canchitodev.example.domain.basic.Platform) parser.doUnMarshaling(resource.getInputStream());
			System.out.println("Source read: " + platform.toString());
			
			System.out.println("Writting read data to file");
			parser.doMarshaling(new FileOutputStream("basic-example-exported.xml"), platform);

			System.out.println("********* BASIC EXAMPLE *********");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	@Ignore("Status... Passed")
	public void complexExample() {
		System.out.println("********* COMPLEX EXAMPLE *********");
		
		System.out.println("Loading source data to read from classpath");
		ClassPathResource resource = new ClassPathResource("complex/complex-example.xml");
		
		// Get the marshaling converter
		System.out.println("Loading XML Marshal Utility");
		XMLMarshalUtil parser = (XMLMarshalUtil) applicationContext.getBean("xmlComplexHandler");
		
		try {
			System.out.println("Reading source XML file");
			com.canchitodev.example.domain.complex.Platform platform = (com.canchitodev.example.domain.complex.Platform) parser.doUnMarshaling(resource.getInputStream());
			System.out.println("Source read: " + platform.toString());
			
			System.out.println("Writting read data to file");
			parser.doMarshaling(new FileOutputStream("complex-example-exported.xml"), platform);

			System.out.println("********* COMPLEX EXAMPLE *********");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	@Ignore("Status... Passed")
	public void fieldConvertionExample() {
		System.out.println("********* FIELD CONVERTION EXAMPLE *********");
		
		System.out.println("Loading source data to read from classpath");
		ClassPathResource resource = new ClassPathResource("field-convertion/field-convertion-example.xml");
		
		// Get the marshaling converter
		System.out.println("Loading XML Marshal Utility");
		XMLMarshalUtil parser = (XMLMarshalUtil) applicationContext.getBean("xmlFieldConvertionHandler");
		
		try {
			System.out.println("Reading source XML file");
			com.canchitodev.example.field.handlers.domain.Platform platform = (com.canchitodev.example.field.handlers.domain.Platform) parser.doUnMarshaling(resource.getInputStream());
			System.out.println("Source read: " + platform.toString());
			
			System.out.println("Writting read data to file");
			parser.doMarshaling(new FileOutputStream("field-convertion-example-exported.xml"), platform);

			System.out.println("********* FIELD CONVERTION EXAMPLE *********");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}