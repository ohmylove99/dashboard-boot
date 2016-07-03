package org.octopus.dashboard.shared.mapper;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

import com.google.common.collect.Lists;

public class JaxbMapperTest {
	@Test
	public void objectToXml() {
		User user = new User();
		user.setId(1L);
		user.setName("Jason");

		user.getInterests().add("movie");
		user.getInterests().add("sports");

		String xml = JaxbMapper.toXml(user, "UTF-8");
		System.out.println("Jaxb Object to Xml result:\n" + xml);
		assertXmlByDom4j(xml);
	}

	@Test
	public void objectToXmlWithList() {
		User user1 = new User();
		user1.setId(1L);
		user1.setName("Jason");

		User user2 = new User();
		user2.setId(2L);
		user2.setName("kate");

		List<User> userList = Lists.newArrayList(user1, user2);

		String xml = JaxbMapper.toXml(userList, "userList", User.class, "UTF-8");
		System.out.println("Jaxb Object List to Xml result:\n" + xml);
	}

	@Test
	public void xmlToObject() {
		String xml = generateXmlByDom4j();
		User user = JaxbMapper.fromXml(xml, User.class);

		System.out.println("Jaxb Xml to Object result:\n" + user);

		assertThat(user.getId()).isEqualTo(1L);
		assertThat(user.getInterests()).containsOnly("movie", "sports");
	}

	private static String generateXmlByDom4j() {
		Document document = DocumentHelper.createDocument();

		Element root = document.addElement("user").addAttribute("id", "1");

		root.addElement("name").setText("Jason");

		// List<String>
		Element interests = root.addElement("interests");
		interests.addElement("interest").addText("movie");
		interests.addElement("interest").addText("sports");

		return document.asXML();
	}

	@SuppressWarnings("unchecked")
	private static void assertXmlByDom4j(String xml) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			fail(e.getMessage());
		}
		Element user = doc.getRootElement();
		assertThat(user.attribute("id").getValue()).isEqualTo("1");

		Element interests = (Element) doc.selectSingleNode("//interests");
		assertThat(interests.elements()).hasSize(2);
		assertThat(((Element) interests.elements().get(0)).getText()).isEqualTo("movie");
	}

	@XmlRootElement
	@XmlType(propOrder = { "name", "interests" })
	private static class User {

		private Long id;
		private String name;
		private String password;

		private List<String> interests = Lists.newArrayList();

		//
		@XmlAttribute
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		@SuppressWarnings("unused")
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		// ignore
		@XmlTransient
		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		// <interests><interest>movie</interest></interests>
		@XmlElementWrapper(name = "interests")
		@XmlElement(name = "interest")
		public List<String> getInterests() {
			return interests;
		}

		public void setInterests(List<String> interests) {
			this.interests = interests;
		}

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this);
		}
	}
}
