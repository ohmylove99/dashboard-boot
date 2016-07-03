package org.octopus.dashboard.shared.mapper;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class JsonMapperTest {
	private static JsonMapper binder = JsonMapper.nonDefaultMapper();

	@Test
	public void toJson() throws Exception {
		// Bean
		TestBean bean = new TestBean("A");
		String beanString = binder.toJson(bean);
		System.out.println("Bean:" + beanString);
		assertThat(beanString).isEqualTo("{\"name\":\"A\"}");

		// Map
		Map<String, Object> map = Maps.newLinkedHashMap();
		map.put("name", "A");
		map.put("age", 2);
		String mapString = binder.toJson(map);
		System.out.println("Map:" + mapString);
		assertThat(mapString).isEqualTo("{\"name\":\"A\",\"age\":2}");

		// List<String>
		List<String> stringList = Lists.newArrayList("A", "B", "C");
		String listString = binder.toJson(stringList);
		System.out.println("String List:" + listString);
		assertThat(listString).isEqualTo("[\"A\",\"B\",\"C\"]");

		// List<Bean>
		List<TestBean> beanList = Lists.newArrayList(new TestBean("A"), new TestBean("B"));
		String beanListString = binder.toJson(beanList);
		System.out.println("Bean List:" + beanListString);
		assertThat(beanListString).isEqualTo("[{\"name\":\"A\"},{\"name\":\"B\"}]");

		// Bean[]
		TestBean[] beanArray = new TestBean[] { new TestBean("A"), new TestBean("B") };
		String beanArrayString = binder.toJson(beanArray);
		System.out.println("Array List:" + beanArrayString);
		assertThat(beanArrayString).isEqualTo("[{\"name\":\"A\"},{\"name\":\"B\"}]");
	}

	@Test
	public void fromJson() throws Exception {
		// Bean
		String beanString = "{\"name\":\"A\"}";
		TestBean bean = binder.fromJson(beanString, TestBean.class);
		System.out.println("Bean:" + bean);

		// Map
		String mapString = "{\"name\":\"A\",\"age\":2}";
		Map<String, Object> map = binder.fromJson(mapString, HashMap.class);
		System.out.println("Map:");
		for (Entry<String, Object> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}

		// List<String>
		String listString = "[\"A\",\"B\",\"C\"]";
		List<String> stringList = binder.getMapper().readValue(listString, List.class);
		System.out.println("String List:");
		for (String element : stringList) {
			System.out.println(element);
		}

		// List<Bean>
		String beanListString = "[{\"name\":\"A\"},{\"name\":\"B\"}]";
		List<TestBean> beanList = binder.getMapper().readValue(beanListString, new TypeReference<List<TestBean>>() {
		});
		System.out.println("Bean List:");
		for (TestBean element : beanList) {
			System.out.println(element);
		}
	}

	@Test
	public void nullAndEmpty() {

		// Null Bean
		TestBean nullBean = null;
		String nullBeanString = binder.toJson(nullBean);
		assertThat(nullBeanString).isEqualTo("null");

		// Empty List
		List<String> emptyList = Lists.newArrayList();
		String emptyListString = binder.toJson(emptyList);
		assertThat(emptyListString).isEqualTo("[]");

		// Null String for Bean
		TestBean nullBeanResult = binder.fromJson(null, TestBean.class);
		assertThat(nullBeanResult).isNull();

		nullBeanResult = binder.fromJson("null", TestBean.class);
		assertThat(nullBeanResult).isNull();

		// Null/Empty String for List
		List nullListResult = binder.fromJson(null, List.class);
		assertThat(nullListResult).isNull();

		nullListResult = binder.fromJson("null", List.class);
		assertThat(nullListResult).isNull();

		nullListResult = binder.fromJson("[]", List.class);
		assertThat(nullListResult).isEmpty();
	}

	// Test different type mapper
	@Test
	public void threeTypeBinders() {
		JsonMapper normalBinder = new JsonMapper();
		TestBean bean = new TestBean("A");
		assertThat(normalBinder.toJson(bean))
				.isEqualTo("{\"name\":\"A\",\"defaultValue\":\"hello\",\"nullValue\":null}");

		// no nullvalue
		JsonMapper nonNullBinder = JsonMapper.nonEmptyMapper();
		assertThat(nonNullBinder.toJson(bean)).isEqualTo("{\"name\":\"A\",\"defaultValue\":\"hello\"}");

		// no unchanged value
		JsonMapper nonDefaultBinder = JsonMapper.nonDefaultMapper();
		assertThat(nonDefaultBinder.toJson(bean)).isEqualTo("{\"name\":\"A\"}");
	}

	public static class TestBean {

		private String name;
		private String defaultValue = "hello";
		private String nullValue = null;

		public TestBean() {
		}

		public TestBean(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDefaultValue() {
			return defaultValue;
		}

		public void setDefaultValue(String defaultValue) {
			this.defaultValue = defaultValue;
		}

		public String getNullValue() {
			return nullValue;
		}

		public void setNullValue(String nullValue) {
			this.nullValue = nullValue;
		}

		@Override
		public String toString() {
			return "TestBean [defaultValue=" + defaultValue + ", name=" + name + ", nullValue=" + nullValue + "]";
		}
	}
}
