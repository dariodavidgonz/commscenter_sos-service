package com.commscenter.topsecret.sos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.commscenter.topsecret.message.MessageNotResolvedException;
import com.commscenter.topsecret.message.SimpleMessageResolver;

class SimpleMessageResolverTest {

	private SimpleMessageResolver simpleMessageResolver;

	@BeforeEach
	public void init() {
		simpleMessageResolver = new SimpleMessageResolver();
	}

	@ParameterizedTest
	@MethodSource("singleListTestData")
	void testSingleListParameterized(List<String> list, String expectedMessage) {
		List<List<String>> aux = new ArrayList<>();
		aux.add(list);
		String message = simpleMessageResolver.getMessage(aux);
		assertEquals(expectedMessage, message);
	}

	static Stream<Arguments> singleListTestData() {
		return Stream.of(Arguments.of(Arrays.asList("solicito", "", "ayuda"), "solicito ayuda"),
				Arguments.of(Arrays.asList("solicito", ""), "solicito"),
				Arguments.of(Arrays.asList("", "ayuda"), "ayuda"));
	}

	@ParameterizedTest
	@MethodSource("twoListTestData")
	void testListTwoItemsParameterized(List<String> listA, List<String> listB, String expectedMessage) {
		List<List<String>> aux = Lists.newArrayList(listA, listB);
		String message = simpleMessageResolver.getMessage(aux);
		assertEquals(expectedMessage, message);
	}

	static Stream<Arguments> twoListTestData() {
		Arguments twoListCompatibleData = Arguments.of(Arrays.asList("este", "", "", "mensaje", ""),
				Arrays.asList("", "es", "un", "", ""), "este es un mensaje");
		Arguments twoListCompatibleWithNull = Arguments.of(Arrays.asList("solicito", null, ""),
				Arrays.asList("", null, "ayuda"), "solicito ayuda");
		return Stream.of(twoListCompatibleData, twoListCompatibleWithNull);
	}

	@ParameterizedTest
	@MethodSource("threeListTestData")
	void testListThreeItemsParameterized(List<String> listA, List<String> listB, List<String> listC,
			String expectedMessage) {
		List<List<String>> aux = Lists.newArrayList(listA, listB, listC);
		String message = simpleMessageResolver.getMessage(aux);
		assertEquals(expectedMessage, message);
	}

	static Stream<Arguments> threeListTestData() {
		Arguments compatibleListsData = Arguments.of(Arrays.asList("este", "", "", "mensaje", ""),
				Arrays.asList("", "es", "", "", "secreto"), Arrays.asList("este", "", "un", "", ""),
				"este es un mensaje secreto");

		Arguments compatibleListWithSecondListExtraOffsetData = Arguments.of(
				Arrays.asList("", "es", "", "mensaje"), Arrays.asList("", "este", "", "un", "mensaje"),
				Arrays.asList("este", "", "un", "mensaje"), "este es un mensaje");

		Arguments compatibleListWithFirstAndSecondExtraOffsetdata = Arguments.of(
				Arrays.asList("", "ayuda", "", ""), Arrays.asList("", "", "", "", "", "favor"),
				Arrays.asList("", "por", ""), "ayuda por favor");

		return Stream.of(compatibleListsData, compatibleListWithSecondListExtraOffsetData,
				compatibleListWithFirstAndSecondExtraOffsetdata);
	}
	
	@ParameterizedTest
	@MethodSource("fourListTestData")
	void testListFourItemsParameterized(List<String> listA, List<String> listB, List<String> listC, List<String> listD,
			String expectedMessage) {
		List<List<String>> aux = Lists.newArrayList(listA, listB, listC, listD);
		String message = simpleMessageResolver.getMessage(aux);
		assertEquals(expectedMessage, message);
	}
	
	static Stream<Arguments> fourListTestData() {
		Arguments compatibleListWithOneBlank = Arguments.of(Arrays.asList("este", "", "", ""),
				Arrays.asList("este", "es", "", ""), Arrays.asList("este", "", "un", "mensaje"),
				Arrays.asList("", "", "", ""),
				"este es un mensaje");
		
		Arguments compatibleList = Arguments.of(Arrays.asList("este", "", "", ""),
				Arrays.asList("este", "es", "", ""), Arrays.asList("", "", "un", ""),
				Arrays.asList("este", "", "", "mensaje"),
				"este es un mensaje");

		return Stream.of(compatibleListWithOneBlank, compatibleList);
	}

	@Test
	void testMerge2IncompatibleLists() {
		List<List<String>> aux = Lists.newArrayList(Arrays.asList("este", "", "", "mensaje", ""),
				Arrays.asList("es", "un", "mensaje", "", ""));
		assertThrows(MessageNotResolvedException.class, () -> {
			simpleMessageResolver.getMessage(aux);
		});
	}

	@Test
	void testMerge3IncompatibleLists() {
		List<List<String>> aux = Lists.newArrayList(Arrays.asList("", "x", "", "este", "es", "un", "mensaje"),
				Arrays.asList("este", "", "un", "mensaje"), Arrays.asList("", "", "es", "", "mensaje"));
		assertThrows(MessageNotResolvedException.class, () -> {
			simpleMessageResolver.getMessage(aux);
		});
	}

	@Test
	void testMerge3ListIncompatibleSecondWord() {
		List<List<String>> aux = Lists.newArrayList(Arrays.asList("", "ayuda", "", ""),
				Arrays.asList("", "", "", "", "", "favor", ""), Arrays.asList("", "por", ""));
		assertThrows(MessageNotResolvedException.class, () -> {
			simpleMessageResolver.getMessage(aux);
		});
	}

	@Test
	void testMerge3ListIncompatibleFirstWord() {
		List<List<String>> aux = Lists.newArrayList(Arrays.asList("ayuda", "", ""), Arrays.asList("favor", ""),
				Arrays.asList("por", ""));
		assertThrows(MessageNotResolvedException.class, () -> {
			simpleMessageResolver.getMessage(aux);
		});
	}

	@Test
	void testMerge3IncompatibleListWithNullEqualsNoElementInPlace() {
		List<List<String>> aux = Lists.newArrayList(Arrays.asList("ayuda", null, ""), Arrays.asList("", "por", ""),
				Arrays.asList("ayuda", "favor"));
		assertThrows(MessageNotResolvedException.class, () -> {
			simpleMessageResolver.getMessage(aux);
		});
	}



	@Test
	void testIncorrectListSizes() {
		List<List<String>> aux = Lists.newArrayList(Arrays.asList("este", "es"), Arrays.asList("", "este", "", "uno"));
		assertThrows(MessageNotResolvedException.class, () -> {
			simpleMessageResolver.getMessage(aux);
		});
	}

	@Test
	void test3MessagesWithOffset() {
		List<List<String>> aux = Lists.newArrayList(Arrays.asList("", "", "este", "", "", ""),
				Arrays.asList("", "", "", "", "un", ""), Arrays.asList("", "", "es", "", "mensaje"));
		String message = simpleMessageResolver.getMessage(aux);
		assertEquals("este es un mensaje", message);
	}

	@Test
	void testNullList() {
		assertThrows(MessageNotResolvedException.class, () -> {
			simpleMessageResolver.getMessage(null);
		});
	}

	@Test
	void testNoElementsList() {
		List<List<String>> aux = new ArrayList<>();
		assertThrows(MessageNotResolvedException.class, () -> {
			simpleMessageResolver.getMessage(aux);
		});
	}

	@Test
	void testSingleList() {
		List<List<String>> aux = new ArrayList<>();
		aux.add(new ArrayList<>(Arrays.asList("solicito", "ayuda")));
		String message = simpleMessageResolver.getMessage(aux);
		assertEquals("solicito ayuda", message);
	}
}
