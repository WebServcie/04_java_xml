package com.bling.stax;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.EventFilter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFunction;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TestStax {
	/**
	 * 使用光标的方式
	 */
	@Test
	public void test01() {
		XMLInputFactory factory = XMLInputFactory.newFactory();
		InputStream is = null;
		is = TestStax.class.getClassLoader().getResourceAsStream("books.xml");
		try {
			XMLStreamReader reader = factory.createXMLStreamReader(is);
			while (reader.hasNext()) {
				int type = reader.next();
				if (type == XMLStreamConstants.START_ELEMENT) {
					System.out.println(reader.getName());
				} else if (type == XMLStreamConstants.CHARACTERS) {
					System.out.println(reader.getText().trim());
				} else if (type == XMLStreamConstants.END_ELEMENT) {
					System.out.println("/" + reader.getName());
				}
				// System.out.println(reader.next());
			}
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	@Test
	public void test02() {
		XMLInputFactory factory = XMLInputFactory.newFactory();
		InputStream is = null;
		is = TestStax.class.getClassLoader().getResourceAsStream("books.xml");
		try {
			XMLStreamReader reader = factory.createXMLStreamReader(is);
			while (reader.hasNext()) {
				int type = reader.next();
				if (type == XMLStreamConstants.START_ELEMENT) {
					String name = reader.getName().toString();
					if (name.equals("book")) {
						System.out.println(reader.getAttributeName(0) + "："
								+ reader.getAttributeValue(0));
					}
				}
				// System.out.println(reader.next());
			}
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	@Test
	public void test03() {
		XMLInputFactory factory = XMLInputFactory.newFactory();
		InputStream is = null;
		is = TestStax.class.getClassLoader().getResourceAsStream("books.xml");
		try {
			XMLStreamReader reader = factory.createXMLStreamReader(is);
			while (reader.hasNext()) {
				int type = reader.next();
				if (type == XMLStreamConstants.START_ELEMENT) {
					String name = reader.getName().toString();
					if (name.equals("title")) {
						System.out.print(reader.getElementText() + "：");
					}
					if (name.equals("price")) {
						System.out.println(reader.getElementText() + "：");
					}
				}
				// System.out.println(reader.next());
			}
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	@Test
	public void test04() {
		XMLInputFactory factory = XMLInputFactory.newFactory();
		InputStream is = null;
		is = TestStax.class.getClassLoader().getResourceAsStream("books.xml");
		// 基于迭代模型的操作凡事
		try {
			XMLEventReader reader = factory.createXMLEventReader(is);
			while (reader.hasNext()) {
				// 通过XMLEvent获取是否是某种节点类型
				XMLEvent event = reader.nextEvent();

				if (event.isStartElement()) {
					// 通过event.asStartElement()转换节点
					String name = event.asStartElement().getName().toString();
					if (name.equals("title")) {
						System.out.print(reader.getElementText() + "：");
					}
					if (name.equals("price")) {
						System.out.println(reader.getElementText() + "：");
					}
				}
				// System.out.println(reader.next());
			}
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	@Test
	public void test05() {
		XMLInputFactory factory = XMLInputFactory.newFactory();
		InputStream is = null;
		is = TestStax.class.getClassLoader().getResourceAsStream("books.xml");
		// 基于filter的过滤方式，可以有效地过滤不用操作的节点，效率会高点
		try {
			XMLEventReader reader = factory.createFilteredReader(
					factory.createXMLEventReader(is), new EventFilter() {
						@Override
						public boolean accept(XMLEvent event) {
							//返回true表示会显示，返回false表示不会显示
							if(event.isStartElement()){
								String name = event.asStartElement().getName().toString();
								if(name.equals("title")||name.equals("price")){
									return true;
								}
							}
							return false;
						}
					});
			int num=0;
			while (reader.hasNext()) {
				// 通过XMLEvent获取是否是某种节点类型
				XMLEvent event = reader.nextEvent();

				if (event.isStartElement()) {
					// 通过event.asStartElement()转换节点
					String name = event.asStartElement().getName().toString();
					if (name.equals("title")) {
						System.out.print(reader.getElementText() + "：");
					}
					if (name.equals("price")) {
						System.out.println(reader.getElementText() + "：");
					}
				}
				num++;
				// System.out.println(reader.next());
			}
			System.out.println(num);
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	@Test
	public void test06() {
		InputStream is = null;
		try {
			is = TestStax.class.getClassLoader().getResourceAsStream("books.xml");
			//创建文档处理对象
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			//通过DocumentBuilder创建doc的文档对象
			Document doc = db.parse(is);
			//创建Xpath
			XPath xpath = XPathFactory.newInstance().newXPath();
			//第一个参数为xpath,第二个参数就是文档
			NodeList list = (NodeList)xpath.evaluate("//book[@category='WEB']", doc, XPathConstants.NODESET);
			for(int i=0;i<list.getLength();i++){
				Element e = (Element)list.item(i);
				System.out.println(e.getElementsByTagName("title").item(0).getTextContent());
						
			}
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
