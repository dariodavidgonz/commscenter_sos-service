package com.commscenter.topsecret.sos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;

public class SimpleMessageResolver implements MessageResolver {

	public String getMessage(List<List<String>> wordLists) {
		int minMessageSize = getMinMessageSize(wordLists);
		normalizeSize(wordLists, minMessageSize);
		validateListsSize(wordLists, minMessageSize);
		List<String> mergedWordList = mergeWordLists(wordLists, minMessageSize);
		return convertToMessageString(mergedWordList);
	}

	private String convertToMessageString(List<String> list) {
		StringJoiner join = new StringJoiner(" ");
		for (String word : list) {
			if (!"".equals(word))
				join.add(word);
		}
		return join.toString();
	}

	private void normalizeSize(List<List<String>> wordLists, int messageSize) {
		for (List<String> list : wordLists) {
			removeExtraBlanksFromList(messageSize, list);
		}
	}

	private void removeExtraBlanksFromList(int messageSize, List<String> list) {

		Iterator<String> i = list.iterator();
		int listSize = list.size();
		for (int index = 0; index < listSize - messageSize && i.hasNext(); index++) {
			String e = i.next();
		    if ("".equals(e)) {
		        i.remove();
		    }
			
		}
	}

	private List<String> mergeWordLists(List<List<String>> wordLists, int messageSize) {
		List<String> outputList = createListOfBlanks(messageSize);
		for (int listIndex = 0; listIndex < wordLists.size(); listIndex++) {
			for (int wordIndex = 0; wordIndex < wordLists.get(listIndex).size(); wordIndex++) {
				replaceWord(wordLists.get(listIndex), outputList, wordIndex);
			}
		}
		return outputList;
	}

	private void replaceWord(List<String> wordList, List<String> aux, int i) {
		if (wordList.get(i) != null && !wordList.get(i).equals(aux.get(i))) {
			if ("".equals(aux.get(i))) {
				aux.set(i, wordList.get(i));
			} else {
				if (!"".equals(wordList.get(i))) {
					throw new MessageNotResolvedException();
				}
			}

		}

	}

	private List<String> createListOfBlanks(int messageSize) {
		List<String> aux = new ArrayList<>();
		for (int i = 0; i < messageSize; i++) {
			aux.add("");
		}
		return aux;
	}

	private void validateListsSize(List<List<String>> wordLists, int msgSize) {
		for (List<String> list : wordLists) {
			if (list.size() > msgSize)
				throw new MessageNotResolvedException();
		}

	}

	private int getMinMessageSize(List<List<String>> wordLists) {
		if (wordLists == null)
			throw new MessageNotResolvedException();
		List<String> minList = wordLists.stream().min(Comparator.comparing(List<String>::size))
				.orElseThrow(MessageNotResolvedException::new);
		return minList.size();
	}

}
