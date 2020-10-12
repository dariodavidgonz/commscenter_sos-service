package com.commscenter.topsecret.message;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

/**
 * Message Resolver implementating an algorithm for merging information from
 * different sources messages in order to resolve its meaning
 * 
 * @author Dario Gonzalez
 * 
 */
@Component
public class SimpleMessageResolver implements MessageResolver {

	/**
	 * {@inheritDoc}
	 *
	 * This method used the input messages and merge its information. Messages can
	 * have an offset filed with empty String elements. The function removes the
	 * offset of the input lists.
	 * 
	 */
	public String getMessage(List<List<String>> wordLists) {
		validate(wordLists);
		int realMessageSize = getMinMessageSize(wordLists);
		List<List<String>> cleanLists = wordLists.stream()
				.map(list -> removeExtraEmptyElementsFromList(realMessageSize, list)).collect(Collectors.toList());
		validateListsSize(cleanLists, realMessageSize);
		List<String> mergedWordList = resolveMessages(cleanLists, realMessageSize);
		return convertToMessageString(mergedWordList);
	}

	/**
	 * Removes extra empty elements from a list to make it match a required size.
	 *
	 * @param requiredSize exact size that the list is required to have
	 * @param list
	 * 
	 * @return new list without extra empty elements
	 * 
	 */
	private List<String> removeExtraEmptyElementsFromList(int requiredSize, List<String> list) {
		boolean elementsToDeleteAllEmpty = list.stream().limit((long) list.size() - requiredSize)
				.allMatch(element -> element.isEmpty());
		if (!elementsToDeleteAllEmpty) {
			throw new MessageNotResolvedException(
					"Message size (ignoring offsets) should be the same for all satellites.");
		}
		return list.stream().skip((long) list.size() - requiredSize).collect(Collectors.toList());

	}

	/**
	 * This method merges all lists in wordLists to a new List with a required size
	 * 
	 * @param wordLists         lists that will be merged
	 * @param outputMessageSize required output size
	 * 
	 */
	private List<String> resolveMessages(List<List<String>> wordLists, int outputMessageSize) {
		List<String> outputList = createListOfEmpties(outputMessageSize);
		wordLists.stream().forEach(list -> mergeLists(outputList, list));
		return outputList;
	}

	/**
	 * This method merges elements from two lists with invalid elements Invalid
	 * elements are the ones which are an empty String "". This method modifies the
	 * first parameter (outputList).
	 * 
	 * @param outputList list to be modified
	 * @param list       list with possible values
	 * 
	 */
	private void mergeLists(List<String> outputList, List<String> list) {
		for (int wordIndex = 0; wordIndex < list.size(); wordIndex++) {
			mergeWord(list, outputList, wordIndex);
		}
	}

	/**
	 * This method replaces the content present at an specific index in the output
	 * list with the information present in the source list at the same index
	 * 
	 * @param sourceWordList list with the information to be merged
	 * @param targetWordList output list in which the information will be merged
	 * @param wordIndex      position in both lists of the element being merged
	 * 
	 */
	private void mergeWord(List<String> sourceWordList, List<String> targetWordList, int wordIndex) {
		if (canReplace(sourceWordList, targetWordList, wordIndex)) {
			if (isEmptyAtIndex(targetWordList, wordIndex)) {
				targetWordList.set(wordIndex, sourceWordList.get(wordIndex));
			} else if (!isEmptyAtIndex(sourceWordList, wordIndex)) {
				throw new MessageNotResolvedException("Incompatible messages.");
			}
		}
	}

	/**
	 * Validates that the list of words can be processed
	 * 
	 * @param wordLists list to validate
	 * 
	 * @throws MessageNotResolvedException in case the lists are not valid to
	 *                                     process
	 * 
	 */
	private void validate(List<List<String>> wordLists) {
		if (wordLists == null || wordLists.stream().anyMatch(list -> (list == null) || list.isEmpty())) {
			throw new MessageNotResolvedException("All messages should have information.");
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

	private void validateListsSize(List<List<String>> wordLists, int expectedSize) {
		for (List<String> list : wordLists) {
			if (list.size() != expectedSize)
				throw new MessageNotResolvedException(
						"Message size (ignoring offsets) should be the same for all satellites.\"");
		}
	}

	private int getMinMessageSize(List<List<String>> wordLists) {
		List<String> minList = wordLists.stream().min(Comparator.comparing(List<String>::size))
				.orElseThrow(() -> new MessageNotResolvedException("Message could not be resolved"));
		return minList.size();
	}

	private String convertToMessageString(List<String> list) {
		return list.stream().map(Object::toString).filter(s -> !s.isEmpty()).collect(Collectors.joining(" "));
	}

}
