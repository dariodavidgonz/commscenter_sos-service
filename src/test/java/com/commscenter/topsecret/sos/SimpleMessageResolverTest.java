package com.commscenter.topsecret.sos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.commscenter.topsecret.message.MessageNotResolvedException;
import com.commscenter.topsecret.message.SimpleMessageResolver;

class SimpleMessageResolverTest {

	@Test
	void testNullList() {
		SimpleMessageResolver simpleMessageResolver = new SimpleMessageResolver();
		assertThrows(MessageNotResolvedException.class, () -> {			
			simpleMessageResolver.getMessage(null);
		});
	}

	@Test
	void testNoElementsList() {
		SimpleMessageResolver simpleMessageResolver = new SimpleMessageResolver();
		List<List<String>> aux = new ArrayList<>();
		assertThrows(MessageNotResolvedException.class, () -> {
			simpleMessageResolver.getMessage(aux);
		});
	}

	@Test
	void testSingleList() {
		List<List<String>> aux = new ArrayList<>();
		aux.add(new ArrayList<>(Arrays.asList("solicito", "ayuda")));
		String message = new SimpleMessageResolver().getMessage(aux);
		assertEquals("solicito ayuda", message);
	}

	@Test
	void testSingleListWithEmptyWord() {
		List<List<String>> aux = new ArrayList<>();
		aux.add(new ArrayList<>(Arrays.asList("solicito", "", "ayuda")));
		String message = new SimpleMessageResolver().getMessage(aux);
		assertEquals("solicito ayuda", message);
	}

	@Test
	void testSingleListWithTwoSpaces() {
		List<List<String>> aux = new ArrayList<>();
		aux.add(new ArrayList<>(Arrays.asList("solicito", "  ", "ayuda")));
		String message = new SimpleMessageResolver().getMessage(aux);
		assertEquals("solicito    ayuda", message);
	}

	@Test
	void testSingleListWithExtraBlank() {
		List<List<String>> aux = new ArrayList<>();
		aux.add(new ArrayList<>(Arrays.asList("", "ayuda")));
		String message = new SimpleMessageResolver().getMessage(aux);
		assertEquals("ayuda", message);
	}

	@Test
	void testMerge2CompatibleLists() {
		List<List<String>> aux = new ArrayList<>();
		aux.add(new ArrayList<>(Arrays.asList("este", "", "", "mensaje", "")));
		aux.add(new ArrayList<>(Arrays.asList("", "es", "un", "", "")));
		String message = new SimpleMessageResolver().getMessage(aux);
		assertEquals("este es un mensaje", message);
	}

	@Test
	void testMerge2IncompatibleLists() {
		List<List<String>> aux = new ArrayList<>();
		aux.add(new ArrayList<>(Arrays.asList("este", "", "", "mensaje", "")));
		aux.add(new ArrayList<>(Arrays.asList("es", "un", "mensaje", "", "")));
		SimpleMessageResolver simpleMessageResolver = new SimpleMessageResolver();
		assertThrows(MessageNotResolvedException.class, () -> {
			simpleMessageResolver.getMessage(aux);
		});
	}

	@Test
	void testMerge2CompatibleListWithNullIgnoredAsEmpty() {
		List<List<String>> aux = new ArrayList<>();
		aux.add(new ArrayList<>(Arrays.asList("solicito", null, "")));
		aux.add(new ArrayList<>(Arrays.asList("", null, "ayuda")));
		String message = new SimpleMessageResolver().getMessage(aux);
		assertEquals("solicito ayuda", message);
	}

	@Test
	void testMerge3CompatibleLists() {
		List<List<String>> aux = new ArrayList<>();
		aux.add(new ArrayList<>(Arrays.asList("este", "", "", "mensaje", "")));
		aux.add(new ArrayList<>(Arrays.asList("", "es", "", "", "secreto")));
		aux.add(new ArrayList<>(Arrays.asList("este", "", "un", "", "")));
		String message = new SimpleMessageResolver().getMessage(aux);
		assertEquals("este es un mensaje secreto", message);
	}

	@Test
	void testMerge3CompatibleListWithFirstListExtraBlank() {
		List<List<String>> aux = new ArrayList<>();
		aux.add(new ArrayList<>(Arrays.asList("", "este", "es", "un", "mensaje")));
		aux.add(new ArrayList<>(Arrays.asList("este", "", "un", "mensaje")));
		aux.add(new ArrayList<>(Arrays.asList("", "", "es", "", "mensaje")));
		String message = new SimpleMessageResolver().getMessage(aux);
		assertEquals("este es un mensaje", message);
	}

	@Test
	void testMerge3CompatibleListWithSecondListExtraBlank() {
		List<List<String>> aux = new ArrayList<>();
		aux.add(new ArrayList<>(Arrays.asList("", "es", "", "mensaje")));
		aux.add(new ArrayList<>(Arrays.asList("", "este", "", "un", "mensaje")));
		aux.add(new ArrayList<>(Arrays.asList("este", "", "un", "mensaje")));
		String message = new SimpleMessageResolver().getMessage(aux);
		assertEquals("este es un mensaje", message);
	}

	@Test
	void testMerge3CompatibleListWithFirstAndSecondExtraBlank() {
		List<List<String>> aux = new ArrayList<>();
		aux.add(new ArrayList<>(Arrays.asList("", "ayuda", "", "")));
		aux.add(new ArrayList<>(Arrays.asList("", "", "", "", "", "favor")));
		aux.add(new ArrayList<>(Arrays.asList("", "por", "")));
		String message = new SimpleMessageResolver().getMessage(aux);
		assertEquals("ayuda por favor", message);
	}

	@Test
	void testMerge3ListIncompatibleSecondWord() {
		List<List<String>> aux = new ArrayList<>();
		aux.add(new ArrayList<>(Arrays.asList("", "ayuda", "", "")));
		aux.add(new ArrayList<>(Arrays.asList("", "", "", "", "", "favor", "")));
		aux.add(new ArrayList<>(Arrays.asList("", "por", "")));
		SimpleMessageResolver simpleMessageResolver = new SimpleMessageResolver();
		assertThrows(MessageNotResolvedException.class, () -> {
			simpleMessageResolver.getMessage(aux);
		});
	}

	@Test
	void testMerge3ListIncompatibleFirstWord() {
		List<List<String>> aux = new ArrayList<>();
		aux.add(new ArrayList<>(Arrays.asList("ayuda", "", "")));
		aux.add(new ArrayList<>(Arrays.asList("favor", "")));
		aux.add(new ArrayList<>(Arrays.asList("por", "")));
		SimpleMessageResolver simpleMessageResolver = new SimpleMessageResolver();
		assertThrows(MessageNotResolvedException.class, () -> {
			simpleMessageResolver.getMessage(aux);
		});
	}

	@Test
	void testMerge3IncompatibleListWithNullEqualsNoElementInPlace() {
		List<List<String>> aux = new ArrayList<>();
		aux.add(new ArrayList<>(Arrays.asList("ayuda", null, "")));
		aux.add(new ArrayList<>(Arrays.asList("", "por", "")));
		aux.add(new ArrayList<>(Arrays.asList("ayuda", "favor")));
		SimpleMessageResolver simpleMessageResolver = new SimpleMessageResolver();
		assertThrows(MessageNotResolvedException.class, () -> {
			simpleMessageResolver.getMessage(aux);
		});
	}

	@Test
	void testMerge4CompatibleList() {
		List<List<String>> aux = new ArrayList<>();
		aux.add(new ArrayList<>(Arrays.asList("este", "", "", "")));
		aux.add(new ArrayList<>(Arrays.asList("este", "es", "", "")));
		aux.add(new ArrayList<>(Arrays.asList("", "", "un", "")));
		aux.add(new ArrayList<>(Arrays.asList("este", "", "", "mensaje")));
		String message = new SimpleMessageResolver().getMessage(aux);
		assertEquals("este es un mensaje", message);
	}

	@Test
	void testMerge4CompatibleListWithOneBlank() {
		List<List<String>> aux = new ArrayList<>();
		aux.add(new ArrayList<>(Arrays.asList("este", "", "", "")));
		aux.add(new ArrayList<>(Arrays.asList("este", "es", "", "")));
		aux.add(new ArrayList<>(Arrays.asList("", "", "", "")));
		aux.add(new ArrayList<>(Arrays.asList("este", "", "un", "mensaje")));
		String message = new SimpleMessageResolver().getMessage(aux);
		assertEquals("este es un mensaje", message);
	}

	@Test
	void testIncorrectListSizes() {
		List<List<String>> aux = new ArrayList<>();
		aux.add(new ArrayList<>(Arrays.asList("este", "es")));
		aux.add(new ArrayList<>(Arrays.asList("", "este", "", "uno")));
		SimpleMessageResolver simpleMessageResolver = new SimpleMessageResolver();
		assertThrows(MessageNotResolvedException.class, () -> {
			simpleMessageResolver.getMessage(aux);
		});
	}

}
