package com.commscenter.topsecret.message;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class SimpleMessageResolver implements MessageResolver {

	public String getMessage(List<List<String>> wordLists) {
		validate(wordLists);
		int minMessageSize = getMinMessageSize(wordLists);
		wordLists.stream().forEach(list -> removeExtraBlanksFromList(minMessageSize, list));
		validateListsSize(wordLists, minMessageSize);
		List<String> mergedWordList = resolveMessages(wordLists, minMessageSize);
		return convertToMessageString(mergedWordList);
	}

	private void removeExtraBlanksFromList(int messageSize, List<String> list) {
		Iterator<String> i = list.iterator();
		int listSize = list.size();
		for (int index = 0; index < listSize - messageSize && i.hasNext(); index++) {
			String e = i.next();
			if (e.isEmpty()) {
				i.remove();
			}
		}
	}

	private List<String> resolveMessages(List<List<String>> wordLists, int messageSize) {
		List<String> outputList = createListOfEmpties(messageSize);
		wordLists.stream().forEach(list -> mergeLists(outputList, list));
		return outputList;
	}

	private void mergeLists(List<String> outputList, List<String> list) {
		for (int wordIndex = 0; wordIndex < list.size(); wordIndex++) {
			mergeWord(list, outputList, wordIndex);
		}
	}

	private void mergeWord(List<String> sourceWordList, List<String> targetWordList, int wordIndex) {
		if (canReplace(sourceWordList, targetWordList, wordIndex)) {
			if (isEmptyAtIndex(targetWordList, wordIndex)) {
				targetWordList.set(wordIndex, sourceWordList.get(wordIndex));
			} else if (!isEmptyAtIndex(sourceWordList, wordIndex)) {
				throw new MessageNotResolvedException();
			}
		}

	}

	private boolean isEmptyAtIndex(List<String> targetWordList, int wordIndex) {
		return targetWordList.get(wordIndex).isEmpty();
	}

	private boolean canReplace(List<String> wordList, List<String> targetWordList, int wordIndex) {
		return wordList.get(wordIndex) != null && !wordList.get(wordIndex).equals(targetWordList.get(wordIndex));
	}

	private List<String> createListOfEmpties(int messageSize) {
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
		List<String> minList = wordLists.stream().min(Comparator.comparing(List<String>::size))
				.orElseThrow(MessageNotResolvedException::new);
		return minList.size();
	}

	private void validate(List<List<String>> wordLists) {
		if (wordLists == null) {
			throw new MessageNotResolvedException();
		}
	}
	
	private String convertToMessageString(List<String> list) {
		return list.stream().
			       map(Object::toString).
			       collect(Collectors.joining(" "));
	}

}
