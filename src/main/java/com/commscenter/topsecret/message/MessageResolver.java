package com.commscenter.topsecret.message;

import java.util.List;

public interface MessageResolver {

	public String getMessage(List<List<String>> wordLists);

}