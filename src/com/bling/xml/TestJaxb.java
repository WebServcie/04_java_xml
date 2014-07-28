package com.bling.xml;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

public class TestJaxb {
	@Test
	public void test01(){
		try {
			JAXBContext ctx = JAXBContext.newInstance(Student.class);
			Marshaller marshaller = ctx.createMarshaller();
			Student stu = new Student(1,"杨明亮",21,new Classroom(1,"计算机",2014));
			marshaller.marshal(stu, System.out);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void test02(){
		String xml ="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><student><age>21</age><classroom><grade>2014</grade><id>1</id><name>计算机</name></classroom><id>1</id><name>杨明亮</name></student>";
		JAXBContext ctx;
		try {
			ctx = JAXBContext.newInstance(Student.class);
			Unmarshaller um = ctx.createUnmarshaller();
			Student stu = (Student)um.unmarshal(new StringReader(xml));
			System.out.println("student name:"+stu.getName());
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
